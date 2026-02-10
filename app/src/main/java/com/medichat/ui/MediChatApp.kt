package com.medichat.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.medichat.data.AppContainer
import com.medichat.ui.screens.DiagnosisScreen
import com.medichat.ui.screens.HistoryScreen

@Composable
fun MediChatApp(appContainer: AppContainer) {
    val navController = rememberNavController()
    val vm: MediChatViewModel = viewModel(factory = MediChatViewModelFactory(appContainer.medicalAssistantRepository))
    val state by vm.uiState.collectAsState()

    NavHost(navController = navController, startDestination = "diagnose") {
        composable("diagnose") {
            DiagnosisScreen(
                state = state,
                onSymptomsChange = vm::updateSymptoms,
                onDiagnose = vm::diagnose,
                onViewHistory = { navController.navigate("history") }
            )
        }
        composable("history") {
            HistoryScreen(
                records = state.history,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
