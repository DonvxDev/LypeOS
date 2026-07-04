#!/bin/bash
# Lype OS Build Environment Setup
# Source from build/envsetup.sh after LineageOS setup

function lype_lunch() {
    local device=$1
    if [ -z "$device" ]; then
        echo "Usage: lype_lunch <device>"
        return 1
    fi
    lunch "lype_${device}-userdebug"
}

function lype_build() {
    echo "Building Lype OS..."
    mka bacon -j$(nproc --all)
}

export LYPE_BUILD=true
export LYPE_VERSION="1.0"

echo "Lype OS build environment loaded."
