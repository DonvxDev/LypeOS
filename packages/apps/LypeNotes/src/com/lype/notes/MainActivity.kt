package com.lype.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppScreen("Lype Notes", "Notizen") }
    }
}

@Composable
fun AppScreen(title: String, description: String) {
    MaterialTheme(colorScheme = lightColorScheme(primary = Color(0xFF6C5CE7))) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(description, color = Color.Gray)
            }
        }
    }
}
