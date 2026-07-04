# So veröffentlichst du Lype OS auf GitHub

## 1. Repository vorbereiten

- Öffne GitHub und erstelle ein neues Repository.
- Lade diesen Ordner dort hoch.

## 2. Release-Dateien vorbereiten

Lege deine GSI-Dateien in den Ordner gsi oder gsi/out ab:

- system.img
- vendor.img (optional)
- boot.img (optional)
- recovery.img (optional)

## 3. Workflow starten

1. Öffne die Registerkarte Actions.
2. Wähle den Workflow "Build and Publish Lype OS Release".
3. Klicke auf Run workflow.
4. Gib eine Version ein, z. B. 0.1.0.
5. Starte den Lauf.

## 4. Ergebnis

Nach dem Lauf entsteht ein GitHub Release mit einer Archivdatei wie:

- LypeOS-gsi-0.1.0.7z

## 5. Hinweise

- Für echte .7z-Dateien braucht die GitHub-Actions-Umgebung p7zip.
- Wenn 7z nicht verfügbar ist, erzeugt das Skript automatisch einen ZIP-Fallback.
