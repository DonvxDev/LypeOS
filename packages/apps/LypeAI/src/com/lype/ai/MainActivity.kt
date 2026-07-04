package com.lype.ai

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

/**
 * Lype AI – integrierter Assistent mit Offline- und Online-Modus.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LypeAIScreen() }
    }
}

data class ChatMessage(val role: String, val text: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LypeAIScreen() {
    var input by remember { mutableStateOf("") }
    var offlineMode by remember { mutableStateOf(true) }
    val messages = remember {
        mutableStateListOf(
            ChatMessage("assistant", "Hallo! Ich bin Lype AI. Wie kann ich helfen?")
        )
    }

    MaterialTheme(colorScheme = lightColorScheme(primary = Color(0xFF6C5CE7))) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Lype AI", fontWeight = FontWeight.Bold) },
                    actions = {
                        Switch(checked = offlineMode, onCheckedChange = { offlineMode = it })
                        Text("Offline", modifier = Modifier.padding(end = 16.dp))
                    }
                )
            }
        ) { padding ->
            Column(Modifier.fillMaxSize().padding(padding)) {
                LazyColumn(Modifier.weight(1f).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(messages) { msg ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (msg.role == "assistant") Color(0xFFE8E4FF) else Color(0xFFE0F7F6)
                            )
                        ) {
                            Text(msg.text, Modifier.padding(12.dp))
                        }
                    }
                }
                Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Frage stellen…") }
                    )
                    Button(onClick = {
                        if (input.isNotBlank()) {
                            messages.add(ChatMessage("user", input))
                            val reply = if (offlineMode) {
                                "Offline-Antwort: Ich habe \"$input\" verstanden."
                            } else {
                                "Online-Antwort zu: $input"
                            }
                            messages.add(ChatMessage("assistant", reply))
                            input = ""
                        }
                    }) { Text("Senden") }
                }
            }
        }
    }
}
