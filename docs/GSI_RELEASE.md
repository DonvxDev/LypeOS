# Lype OS GSI Release Workflow

This repository now includes a simple GSI release workflow for packaging release artifacts into a distributable archive.

## What is included

- A packaging script at [tools/package_gsi_release.py](../tools/package_gsi_release.py)
- A GitHub Actions workflow at [.github/workflows/gsi-release.yml](../.github/workflows/gsi-release.yml)
- A sample flashing helper at [gsi/flash_gsi.sh](../gsi/flash_gsi.sh)

## Release layout

Place the files you want to ship in a folder such as:

- [gsi/out](../gsi/out)

Expected files:

- system.img
- vendor.img (optional)
- boot.img (optional)
- recovery.img (optional)
- readme.txt (optional)

## Packaging locally

```bash
python3 tools/package_gsi_release.py \
  --input-dir gsi/out \
  --output dist/LypeOS-gsi-0.1.0.7z \
  --name "Lype OS GSI" \
  --version 0.1.0
```

If `7z` is not available, the script will create a `.zip` fallback.

## GitHub release flow

The workflow can be triggered manually from the Actions tab and will create a release archive from the chosen input directory.
