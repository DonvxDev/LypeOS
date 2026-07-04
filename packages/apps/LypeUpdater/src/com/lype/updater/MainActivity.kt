package com.lype.updater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Lype Updater – OTA-System mit Stable, Beta, Alpha und Nightly Kanälen.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { UpdaterScreen() }
    }
}

enum class UpdateChannel(val label: String) {
    STABLE("Stable"),
    BETA("Beta"),
    ALPHA("Alpha"),
    NIGHTLY("Nightly")
}

data class UpdateInfo(
    val version: String,
    val channel: UpdateChannel,
    val changelog: List<String>,
    val sizeMb: Int,
    val deltaAvailable: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdaterScreen() {
    var selectedChannel by remember { mutableStateOf(UpdateChannel.STABLE) }
    var checking by remember { mutableStateOf(false) }
    var update by remember { mutableStateOf<UpdateInfo?>(null) }

    val sampleUpdate = UpdateInfo(
        version = "Lype OS 1.0.1",
        channel = selectedChannel,
        changelog = listOf(
            "Neues Glass-Design für Kontrollzentrum",
            "Performance-Optimierungen für 120 Hz",
            "Lype AI Offline-Modus verbessert",
            "Sicherheitsupdate Android 16"
        ),
        sizeMb = 842,
        deltaAvailable = true
    )

    MaterialTheme(colorScheme = lightColorScheme(primary = Color(0xFF6C5CE7))) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Lype Updater", fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { padding ->
            LazyColumn(
                Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text("Update-Kanal", fontWeight = FontWeight.SemiBold)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        UpdateChannel.entries.forEach { channel ->
                            FilterChip(
                                selected = selectedChannel == channel,
                                onClick = { selectedChannel = channel },
                                label = { Text(channel.label) }
                            )
                        }
                    }
                }
                item {
                    Button(
                        onClick = {
                            checking = true
                            update = sampleUpdate.copy(channel = selectedChannel)
                            checking = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !checking
                    ) {
                        Text(if (checking) "Suche läuft…" else "Nach Updates suchen")
                    }
                }
                update?.let { info ->
                    item {
                        Card(shape = RoundedCornerShape(16.dp)) {
                            Column(Modifier.padding(16.dp)) {
                                Text(info.version, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text("Kanal: ${info.channel.label}", color = Color.Gray)
                                Text("Größe: ${info.sizeMb} MB" +
                                    if (info.deltaAvailable) " (Delta verfügbar)" else "")
                                Spacer(Modifier.height(8.dp))
                                Text("Changelog", fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                    items(info.changelog) { line ->
                        Text("• $line", modifier = Modifier.padding(start = 8.dp))
                    }
                    item {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { /* Download */ }) { Text("Herunterladen") }
                            OutlinedButton(onClick = { /* Rollback */ }) { Text("Rollback") }
                        }
                    }
                }
            }
        }
    }
}
