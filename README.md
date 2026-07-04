Lype OS
=======

Premium Android Custom ROM basierend auf LineageOS 23.2 (Android 16).

## Projektstruktur

```
Lype OS/
├── manifest/          # Repo-Manifest (repo init -u)
├── vendor/lype/       # ROM-Kern: Branding, Build, Overlays
├── packages/apps/     # Lype System-Apps
├── frameworks/base/lype/  # Design-System & Performance
├── build/lype/        # Build-Erweiterungen
├── lype-sdk/          # Platform SDK
├── device/lype/       # Geräte-Konfiguration
└── docs/              # Dokumentation
```

## Build-Voraussetzungen

- Ubuntu 22.04+ (empfohlen) oder WSL2
- 16 GB RAM minimum, 250+ GB Speicher
- LineageOS Build-Abhängigkeiten

## Quellcode synchronisieren

```bash
mkdir -p ~/lypeos && cd ~/lypeos
repo init -u <pfad-zu-manifest> -b lype-1.0 --git-lfs
repo sync -c -j$(nproc --all)
```

Alternativ mit LineageOS-Manifest + lokalem Overlay:

```bash
repo init -u https://github.com/LineageOS/android.git -b lineage-23.2 --git-lfs
# .repo/local_manifests/lype.xml aus manifest/snippets/lype.xml kopieren
repo sync
```

## Bauen

```bash
source build/envsetup.sh
source vendor/lype/build/envsetup.sh  # falls vorhanden
source build/lype/tools/lype_envsetup.sh
breakfast <device>
brunch <device>
```

## Device-Tree Migration

Ersetze in `device/<vendor>/<device>/lineage_*.mk`:

```makefile
# Alt:
$(call inherit-product, vendor/lineage/config/common_full_phone.mk)

# Neu:
$(call inherit-product, vendor/lype/config/common_full_phone.mk)
```

Oder für schrittweise Migration:

```makefile
$(call inherit-product, vendor/lineage/config/common_full_phone.mk)
$(call inherit-product, vendor/lype/config/lineage_shim.mk)
```

## Lype OS Apps

| App | Paket | Funktion |
|-----|-------|----------|
| Lype Launcher | com.lype.launcher | Standard-Homescreen |
| Lype Settings | com.lype.settings | Systemeinstellungen |
| Lype Updater | com.lype.updater | OTA-Updates |
| Lype Security | com.lype.security | Sicherheit & Datenschutz |
| Lype Files | com.lype.files | Dateimanager |
| Lype Backup | com.lype.backup | Backup & Restore |
| Lype AI | com.lype.ai | KI-Assistent |
| + 11 weitere System-Apps | | |

## Lizenz

Apache 2.0 – siehe vendor/lype/LICENSE
