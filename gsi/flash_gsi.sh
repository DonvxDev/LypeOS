#!/usr/bin/env bash
set -euo pipefail

if [[ $# -lt 1 ]]; then
  echo "Usage: $0 <gsi-dir>"
  echo "Example: $0 gsi/out"
  exit 1
fi

GSI_DIR="$1"
if ! command -v fastboot >/dev/null 2>&1; then
  echo "fastboot not found. Install Android platform-tools first."
  exit 2
fi

if [[ ! -d "$GSI_DIR" ]]; then
  echo "Directory not found: $GSI_DIR"
  exit 3
fi

if [[ ! -f "$GSI_DIR/system.img" ]]; then
  echo "Missing system.img in $GSI_DIR"
  exit 4
fi

echo "Booting device into fastboot mode..."
fastboot reboot bootloader
read -r -p "Press Enter once the device is in fastboot mode..."

fastboot flash system "$GSI_DIR/system.img"
if [[ -f "$GSI_DIR/vendor.img" ]]; then
  fastboot flash vendor "$GSI_DIR/vendor.img"
fi
if [[ -f "$GSI_DIR/boot.img" ]]; then
  fastboot flash boot "$GSI_DIR/boot.img"
fi
if [[ -f "$GSI_DIR/recovery.img" ]]; then
  fastboot flash recovery "$GSI_DIR/recovery.img"
fi

fastboot reboot

echo "Done."
