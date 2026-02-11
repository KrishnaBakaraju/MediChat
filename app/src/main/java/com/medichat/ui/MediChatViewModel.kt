package com.medichat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.medichat.domain.DiagnosisResult
import com.medichat.domain.MedicalAssistantRepository
import com.medichat.domain.MedicalRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MediChatUiState(
    val symptomsInput: String = "",
    val loading: Boolean = false,
    val result: DiagnosisResult? = null,
    val error: String? = null,
    val history: List<MedicalRecord> = emptyList()
)

class MediChatViewModel(
    private val repository: MedicalAssistantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MediChatUiState())
    val uiState: StateFlow<MediChatUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeHistory().collect { records ->
                _uiState.update { it.copy(history = records) }
            }
        }
    }

    fun updateSymptoms(newSymptoms: String) {
        _uiState.update { it.copy(symptomsInput = newSymptoms) }
    }

    fun diagnose() {
        val symptoms = uiState.value.symptomsInput.trim()
        if (symptoms.isBlank()) {
            _uiState.update { it.copy(error = "Please describe symptoms first.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }
            runCatching {
                repository.diagnose(symptoms)
            }.onSuccess { result ->
                _uiState.update { it.copy(loading = false, result = result) }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        loading = false,
                        error = throwable.message ?: "Unknown error while diagnosing."
                    )
                }
            }
        }
    }
}

class MediChatViewModelFactory(
    private val repository: MedicalAssistantRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MediChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MediChatViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
