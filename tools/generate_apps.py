#!/usr/bin/env python3
"""Generiert Lype OS System-Apps."""
from pathlib import Path

BASE = Path(__file__).resolve().parent.parent / "packages" / "apps"
APPS = [
    ("LypeSecurity", "com.lype.security", "Lype Security", "Datenschutzcenter"),
    ("LypeFiles", "com.lype.files", "Lype Files", "Dateimanager"),
    ("LypeBackup", "com.lype.backup", "Lype Backup", "Backup und Wiederherstellung"),
    ("LypeBrowser", "com.lype.browser", "Lype Browser", "Datenschutzfreundlicher Browser"),
    ("LypeNotes", "com.lype.notes", "Lype Notes", "Notizen"),
    ("LypeGallery", "com.lype.gallery", "Lype Gallery", "Fotos und Videos"),
    ("LypeRecorder", "com.lype.recorder", "Lype Recorder", "Aufnahmen"),
    ("LypeMusic", "com.lype.music", "Lype Music", "Musikplayer"),
    ("LypeCalendar", "com.lype.calendar", "Lype Calendar", "Kalender"),
    ("LypeClock", "com.lype.clock", "Lype Clock", "Uhr und Wecker"),
    ("LypeCalculator", "com.lype.calculator", "Lype Calculator", "Taschenrechner"),
    ("LypeWallpapers", "com.lype.wallpapers", "Lype Wallpapers", "Hintergruende"),
    ("LypeWeather", "com.lype.weather", "Lype Weather", "Wetter"),
    ("LypeThemes", "com.lype.themes", "Lype Themes", "Themes und Icons"),
    ("LypeShare", "com.lype.share", "Lype Share", "Teilen"),
]

BP = '''android_app {{
    name: "{name}",
    srcs: ["src/**/*.kt"],
    resource_dirs: ["res"],
    manifest: "AndroidManifest.xml",
    platform_apis: true,
    certificate: "platform",
    privileged: true,
    system_ext_specific: true,
    static_libs: [
        "LypeDesignLib",
        "androidx.activity_activity-compose",
        "androidx.compose.material3_material3",
    ],
}}
'''

MANIFEST = '''<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android" package="{pkg}">
    <application android:label="@string/app_name" android:supportsRtl="true">
        <activity android:name=".MainActivity" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
'''

STRINGS = '''<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">{title}</string>
    <string name="app_description">{desc}</string>
</resources>
'''

KT = '''package {pkg}

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

class MainActivity : ComponentActivity() {{
    override fun onCreate(savedInstanceState: Bundle?) {{
        super.onCreate(savedInstanceState)
        setContent {{ AppScreen("{title}", "{desc}") }}
    }}
}}

@Composable
fun AppScreen(title: String, description: String) {{
    MaterialTheme(colorScheme = lightColorScheme(primary = Color(0xFF6C5CE7))) {{
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {{
            Column(horizontalAlignment = Alignment.CenterHorizontally) {{
                Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(description, color = Color.Gray)
            }}
        }}
    }}
}}
'''

for name, pkg, title, desc in APPS:
    root = BASE / name
    src = root / "src" / Path(*pkg.split("."))
    (root / "res" / "values").mkdir(parents=True, exist_ok=True)
    src.mkdir(parents=True, exist_ok=True)
    (root / "Android.bp").write_text(BP.format(name=name), encoding="utf-8")
    (root / "AndroidManifest.xml").write_text(MANIFEST.format(pkg=pkg), encoding="utf-8")
    (root / "res" / "values" / "strings.xml").write_text(STRINGS.format(title=title, desc=desc), encoding="utf-8")
    (src / "MainActivity.kt").write_text(KT.format(pkg=pkg, title=title, desc=desc), encoding="utf-8")
    print(f"Created {name}")
