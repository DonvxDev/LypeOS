package com.lype.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Lype Settings – vollständige System-Einstellungen mit Glass-Design.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LypeSettingsTheme {
                SettingsScreen(onNavigate = ::openSystemSetting)
            }
        }
    }

    private fun openSystemSetting(action: String) {
        try {
            startActivity(Intent(action))
        } catch (_: Exception) {
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }
    }
}

@Composable
fun LypeSettingsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6C5CE7),
            secondary = Color(0xFF00CEC9),
            surface = Color(0xF0FFFFFF)
        ),
        content = content
    )
}

data class SettingsSection(val title: String, val items: List<SettingsItem>)
data class SettingsItem(val label: String, val action: String)

private val settingsSections = listOf(
    SettingsSection("Netzwerk", listOf(
        SettingsItem("WLAN", Settings.ACTION_WIFI_SETTINGS),
        SettingsItem("Bluetooth", Settings.ACTION_BLUETOOTH_SETTINGS),
        SettingsItem("Mobile Daten", Settings.ACTION_DATA_ROAMING_SETTINGS),
        SettingsItem("Hotspot", Settings.ACTION_WIRELESS_SETTINGS),
        SettingsItem("VPN", Settings.ACTION_VPN_SETTINGS),
        SettingsItem("NFC", Settings.ACTION_NFC_SETTINGS),
        SettingsItem("SIM", Settings.ACTION_NETWORK_OPERATOR_SETTINGS),
    )),
    SettingsSection("Display", listOf(
        SettingsItem("Helligkeit", Settings.ACTION_DISPLAY_SETTINGS),
        SettingsItem("Farben & Theme", Settings.ACTION_DISPLAY_SETTINGS),
        SettingsItem("Bildwiederholrate", Settings.ACTION_DISPLAY_SETTINGS),
        SettingsItem("Always-on-Display", Settings.ACTION_DISPLAY_SETTINGS),
        SettingsItem("Sperrbildschirm", Settings.ACTION_SECURITY_SETTINGS),
    )),
    SettingsSection("Akku", listOf(
        SettingsItem("Akkuverbrauch", Settings.ACTION_BATTERY_SAVER_SETTINGS),
        SettingsItem("Ladeoptimierung", Settings.ACTION_BATTERY_SAVER_SETTINGS),
        SettingsItem("Energiesparmodus", Settings.ACTION_BATTERY_SAVER_SETTINGS),
    )),
    SettingsSection("Speicher", listOf(
        SettingsItem("Speicheranalyse", Settings.ACTION_INTERNAL_STORAGE_SETTINGS),
        SettingsItem("Downloads", Settings.ACTION_DOWNLOAD_SETTINGS),
    )),
    SettingsSection("Datenschutz", listOf(
        SettingsItem("Berechtigungen", Settings.ACTION_PRIVACY_SETTINGS),
        SettingsItem("Datenschutz-Dashboard", Settings.ACTION_PRIVACY_SETTINGS),
        SettingsItem("Standort", Settings.ACTION_LOCATION_SOURCE_SETTINGS),
    )),
    SettingsSection("Sicherheit", listOf(
        SettingsItem("Fingerabdruck", Settings.ACTION_SECURITY_SETTINGS),
        SettingsItem("PIN & Passwort", Settings.ACTION_SECURITY_SETTINGS),
        SettingsItem("Verschlüsselung", Settings.ACTION_SECURITY_SETTINGS),
    )),
    SettingsSection("Personalisierung", listOf(
        SettingsItem("Themes", Settings.ACTION_DISPLAY_SETTINGS),
        SettingsItem("Icons", Settings.ACTION_DISPLAY_SETTINGS),
        SettingsItem("Launcher", Settings.ACTION_HOME_SETTINGS),
        SettingsItem("Kontrollzentrum", Settings.ACTION_DISPLAY_SETTINGS),
    )),
    SettingsSection("Apps", listOf(
        SettingsItem("Installierte Apps", Settings.ACTION_APPLICATION_SETTINGS),
        SettingsItem("Standard-Apps", Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS),
    )),
    SettingsSection("Entwickleroptionen", listOf(
        SettingsItem("USB-Debugging", Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS),
        SettingsItem("Performance", Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS),
    )),
    SettingsSection("Über das Telefon", listOf(
        SettingsItem("Lype OS Version", Settings.ACTION_DEVICE_INFO_SETTINGS),
        SettingsItem("Android Version", Settings.ACTION_DEVICE_INFO_SETTINGS),
        SettingsItem("Kernel Version", Settings.ACTION_DEVICE_INFO_SETTINGS),
        SettingsItem("Sicherheitsupdate", Settings.ACTION_DEVICE_INFO_SETTINGS),
    )),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onNavigate: (String) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Lype Einstellungen", fontWeight = FontWeight.Bold)
                        Text("Premium Systemsteuerung", fontSize = 12.sp, color = Color.Gray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            settingsSections.forEach { section ->
                item {
                    Text(
                        section.title,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF6C5CE7),
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                }
                items(section.items) { item ->
                    GlassSettingsCard(item.label) { onNavigate(item.action) }
                }
            }
        }
    }
}

@Composable
fun GlassSettingsCard(label: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xCCFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp
        )
    }
}
