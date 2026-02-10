package com.medichat.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.medichat.domain.DiagnosisResult

@Composable
fun ResultCard(result: DiagnosisResult) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = "Likely diagnosis",
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = result.diagnosis)

            Text(
                text = "Suggested treatment",
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = result.treatment)

            Text(
                text = "Recommendation",
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = result.recommendation)
        }
    }
}
