package com.lype.launcher

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Lype Launcher – Homescreen, App Drawer, Gesten und Widget-Unterstützung.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LypeLauncherApp(packageManager) }
    }
}

data class AppInfo(val label: String, val packageName: String)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LypeLauncherApp(packageManager: PackageManager) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val apps = remember {
        packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { packageManager.getLaunchIntentForPackage(it.packageName) != null }
            .map { AppInfo(packageManager.getApplicationLabel(it).toString(), it.packageName) }
            .sortedBy { it.label }
    }
    var showDrawer by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { 2 })

    MaterialTheme(colorScheme = lightColorScheme(primary = Color(0xFF6C5CE7))) {
        Box(Modifier.fillMaxSize()) {
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                when (page) {
                    0 -> HomeScreen(onOpenDrawer = { showDrawer = true })
                    1 -> WidgetPlaceholder()
                }
            }
            AnimatedVisibility(
                visible = showDrawer,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                AppDrawer(apps = apps, onLaunch = { pkg ->
                    packageManager.getLaunchIntentForPackage(pkg)?.let { intent ->
                        context.startActivity(intent)
                    }
                    showDrawer = false
                }, onDismiss = { showDrawer = false })
            }
        }
    }
}

@Composable
fun HomeScreen(onOpenDrawer: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Lype OS", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6C5CE7))
        SearchBar(placeholder = "Apps, Kontakte, Einstellungen suchen…", onClick = onOpenDrawer)
        Spacer(Modifier.weight(1f))
        Text("Nach oben wischen für App Drawer", color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun SearchBar(placeholder: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xCCFFFFFF))
    ) {
        Text(placeholder, Modifier.padding(16.dp), color = Color.Gray)
    }
}

@Composable
fun AppDrawer(apps: List<AppInfo>, onLaunch: (String) -> Unit, onDismiss: () -> Unit) {
    Surface(Modifier.fillMaxSize(), color = Color(0xF5F0F4FF)) {
        Column {
            Text("Apps", Modifier.padding(24.dp), fontWeight = FontWeight.Bold, fontSize = 22.sp)
            LazyVerticalGrid(columns = GridCells.Adaptive(80.dp), contentPadding = PaddingValues(16.dp)) {
                items(apps) { app ->
                    Column(
                        Modifier.clickable { onLaunch(app.packageName) }.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(Modifier.size(56.dp), shape = RoundedCornerShape(16.dp)) {}
                        Text(app.label, fontSize = 11.sp, maxLines = 1)
                    }
                }
            }
            TextButton(onClick = onDismiss, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("Schließen")
            }
        }
    }
}

@Composable
fun WidgetPlaceholder() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Widget-Bereich", color = Color.Gray)
    }
}
