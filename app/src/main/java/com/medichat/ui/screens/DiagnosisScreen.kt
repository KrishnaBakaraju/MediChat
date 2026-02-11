package com.medichat.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.medichat.ui.MediChatUiState
import com.medichat.ui.components.ResultCard

@Composable
fun DiagnosisScreen(
    state: MediChatUiState,
    onSymptomsChange: (String) -> Unit,
    onDiagnose: () -> Unit,
    onViewHistory: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "MediChat AI Triage",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(text = "Describe symptoms, duration, severity, and known conditions.")

        OutlinedTextField(
            value = state.symptomsInput,
            onValueChange = onSymptomsChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Symptoms") },
            minLines = 6
        )

        Button(
            onClick = onDiagnose,
            enabled = !state.loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.loading) {
                CircularProgressIndicator(strokeWidth = 2.dp)
            } else {
                Text("Get diagnosis")
            }
        }

        Button(onClick = onViewHistory, modifier = Modifier.fillMaxWidth()) {
            Text("View medical history")
        }

        state.error?.let { error ->
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }

        state.result?.let { result ->
            ResultCard(result = result)
        }

        Text(
            text = "Not medical advice. Seek emergency help for severe symptoms.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
