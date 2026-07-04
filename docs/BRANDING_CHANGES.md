# Lype OS – Branding- und Migrationsliste

Diese Datei listet alle Änderungen für die Umstellung von LineageOS auf **Lype OS**.

## Phase 1: Manifest & Repository (ERLEDIGT)

| Datei | Aktion | Beschreibung |
|-------|--------|--------------|
| `android/default.xml` | Geändert | `snippets/lype.xml` eingebunden |
| `android/snippets/lype.xml` | Neu | Lype OS Repo-Projekte |
| `android/README.mkdn` | Geändert | Lype OS Build-Anleitung |
| `manifest/local_manifest.xml` | Neu | Alternative für `.repo/local_manifests/` |
| `manifest/snippets/lype.xml` | Neu | Vollständiges Lype-Manifest-Snippet |

## Phase 2: Vendor / Build (ERLEDIGT)

| Datei | Aktion |
|-------|--------|
| `vendor/lype/config/version.mk` | Neu – Lype Version, Lineage-Kompatibilität |
| `vendor/lype/config/common.mk` | Neu – Haupt-Produktkonfiguration |
| `vendor/lype/config/common_mobile.mk` | Neu |
| `vendor/lype/config/common_mobile_full.mk` | Neu |
| `vendor/lype/config/common_full_phone.mk` | Neu – Device-Tree Einstiegspunkt |
| `vendor/lype/config/lype_sdk_common.mk` | Neu |
| `vendor/lype/config/lineage_shim.mk` | Neu – Migration ohne Device-Tree-Änderung |
| `vendor/lype/config/BoardConfigLype.mk` | Neu |
| `vendor/lype/config/permissions/lype-sysconfig.xml` | Neu |
| `vendor/lype/config/permissions/org.lypeos.android.xml` | Neu |
| `vendor/lype/overlay/common/.../config.xml` | Neu – System-Branding |
| `vendor/lype/bootanimation/desc.txt` | Neu |
| `vendor/lype/prebuilt/common/etc/init/*.rc` | Neu |
| `vendor/lype/Android.mk` | Neu |
| `build/lype/core/lype_config.mk` | Neu |
| `build/lype/tools/lype_envsetup.sh` | Neu |

## Phase 3: Framework & Design (ERLEDIGT)

| Datei | Aktion |
|-------|--------|
| `frameworks/base/lype/design/Android.bp` | Neu – LypeDesignLib |
| `frameworks/base/lype/design/res/values/lype_colors.xml` | Neu |
| `frameworks/base/lype/design/res/values/lype_themes.xml` | Neu |
| `frameworks/base/lype/design/src/.../LypeGlassTheme.kt` | Neu |
| `frameworks/base/lype/performance/lype_performance.mk` | Neu |
| `packages/overlays/Lype/Android.bp` | Neu |
| `packages/overlays/Lype/AndroidManifest.xml` | Neu |

## Phase 4: SDK (ERLEDIGT)

| Datei | Aktion |
|-------|--------|
| `lype-sdk/Android.mk` | Neu |
| `lype-sdk/sdk/src/com/lype/os/Build.java` | Neu |
| `lype-sdk/api/system/current/com/lype/os/LypeManager.java` | Neu |

## Phase 5: System-Apps (ERLEDIGT)

| App | Pfad |
|-----|------|
| Lype Launcher | `packages/apps/LypeLauncher/` |
| Lype Settings | `packages/apps/LypeSettings/` |
| Lype Updater | `packages/apps/LypeUpdater/` |
| Lype Security | `packages/apps/LypeSecurity/` |
| Lype Files | `packages/apps/LypeFiles/` |
| Lype Backup | `packages/apps/LypeBackup/` |
| Lype Browser | `packages/apps/LypeBrowser/` |
| Lype Notes | `packages/apps/LypeNotes/` |
| Lype Gallery | `packages/apps/LypeGallery/` |
| Lype Recorder | `packages/apps/LypeRecorder/` |
| Lype Music | `packages/apps/LypeMusic/` |
| Lype Calendar | `packages/apps/LypeCalendar/` |
| Lype Clock | `packages/apps/LypeClock/` |
| Lype Calculator | `packages/apps/LypeCalculator/` |
| Lype Wallpapers | `packages/apps/LypeWallpapers/` |
| Lype Weather | `packages/apps/LypeWeather/` |
| Lype Themes | `packages/apps/LypeThemes/` |
| Lype Share | `packages/apps/LypeShare/` |
| Lype AI | `packages/apps/LypeAI/` |

## Phase 6: Device Tree (PRO GERÄT)

Nach `repo sync` in jedem Device-Tree ändern:

| Datei | Änderung |
|-------|----------|
| `device/*/*/lineage_*.mk` | `vendor/lineage` → `vendor/lype` ODER `lineage_shim.mk` hinzufügen |
| `device/*/*/vendorsetup.sh` | Optional: `lype_<device>` Lunch-Combo |
| `device/*/*/BoardConfig.mk` | `-include vendor/lype/config/BoardConfigLype.mk` |
| `device/*/*/overlay/` | Lype Bootlogo/Animation falls gerätespezifisch |

## Phase 7: LineageOS-Quellen (NACH SYNC)

Diese Dateien existieren erst nach `repo sync` und müssen dort angepasst werden:

| Bereich | Dateien | Ersetzung |
|---------|---------|-----------|
| Branding Strings | `vendor/lineage/overlay/**/strings.xml` | LineageOS → Lype OS |
| Build Props | `vendor/lineage/config/version.mk` | Via `vendor/lype` überschrieben |
| Updater | `packages/apps/Updater/res/values/strings.xml` | Lype Updater als Standard |
| SetupWizard | `packages/apps/SetupWizard/` | Lype Branding |
| SystemUI | `frameworks/base/packages/SystemUI/` | Glass-Theme Overlay |
| Settings | `packages/apps/Settings/` | About-Phone: Lype OS Version |
| Bootanimation | `vendor/lineage/bootanimation/` | `vendor/lype/bootanimation/` |
| Recovery | `bootable/recovery/` | Lype Logo |
| sepolicy | `device/lineage/sepolicy/` | `lype` Domain-Labels optional |

## Phase 8: Properties & Identifiers

| Property | Wert |
|----------|------|
| `ro.lype.version` | 1.0-YYYYMMDD-CHANNEL |
| `ro.lype.display.version` | Lype OS 1.0-... |
| `ro.lype.releasetype` | STABLE/BETA/ALPHA/NIGHTLY |
| `PRODUCT_BRAND` | LypeOS |
| `PRODUCT_MANUFACTURER` | Lype |

Lineage-Kompatibilität bleibt über `ro.lineage.*` Properties erhalten.

## Nächste Schritte

1. `repo sync` ausführen (Linux/WSL empfohlen)
2. Lype OS Quellen in Sync-Baum kopieren oder Git-Remotes konfigurieren
3. Device-Tree auf `vendor/lype/config/common_full_phone.mk` umstellen
4. Bootanimation-ZIP aus PNG-Frames generieren (`vendor/lype/tools/gen_bootanimation.sh`)
5. Ersten Build: `brunch lype_<device>` oder `brunch lineage_<device>` + shim
