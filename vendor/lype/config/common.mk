# Lype OS – gemeinsame Produktkonfiguration
$(call inherit-product-if-exists, vendor/extra/product.mk)

PRODUCT_SOURCE_ROOT_DIRS += -kernel/platform
-include $(sort $(wildcard vendor/*/*/exclude-bp.mk))

PRODUCT_BRAND := LypeOS
PRODUCT_MANUFACTURER := Lype

ifeq ($(PRODUCT_GMS_CLIENTID_BASE),)
PRODUCT_PRODUCT_PROPERTIES += \
    ro.com.google.clientidbase=android-google
else
PRODUCT_PRODUCT_PROPERTIES += \
    ro.com.google.clientidbase=$(PRODUCT_GMS_CLIENTID_BASE)
endif

ifeq ($(TARGET_BUILD_VARIANT),eng)
PRODUCT_SYSTEM_EXT_PROPERTIES += ro.adb.secure=0
else
ifdef WITH_ADB_INSECURE
PRODUCT_SYSTEM_EXT_PROPERTIES += ro.adb.secure=0
else
PRODUCT_SYSTEM_EXT_PROPERTIES += ro.adb.secure=1
PRODUCT_NOT_DEBUGGABLE_IN_USERDEBUG := true
endif
PRODUCT_PRODUCT_PROPERTIES += persist.sys.strictmode.disable=true
endif

# Lype OS Design-Framework
PRODUCT_PACKAGES += \
    LypeDesignLib \
    LypeGlassThemeOverlay

PRODUCT_PACKAGE_OVERLAYS += \
    vendor/lype/overlay/common \
    frameworks/base/lype/design/overlay

PRODUCT_ENFORCE_RRO_EXCLUDED_OVERLAYS += vendor/lype/overlay/no-rro

# Lype System-Apps
PRODUCT_PACKAGES += \
    LypeLauncher \
    LypeSettings \
    LypeUpdater \
    LypeSecurity \
    LypeFiles \
    LypeBackup \
    LypeBrowser \
    LypeNotes \
    LypeGallery \
    LypeRecorder \
    LypeMusic \
    LypeCalendar \
    LypeClock \
    LypeCalculator \
    LypeWallpapers \
    LypeWeather \
    LypeThemes \
    LypeShare \
    LypeAI

# Berechtigungen & Sysconfig
PRODUCT_COPY_FILES += \
    vendor/lype/config/permissions/lype-sysconfig.xml:$(TARGET_COPY_OUT_PRODUCT)/etc/sysconfig/lype-sysconfig.xml \
    vendor/lype/config/permissions/org.lypeos.android.xml:$(TARGET_COPY_OUT_PRODUCT)/etc/permissions/org.lypeos.android.xml

PRODUCT_COPY_FILES += \
    vendor/lype/prebuilt/common/etc/init/init.lype-system_ext.rc:$(TARGET_COPY_OUT_SYSTEM_EXT)/etc/init/init.lype-system_ext.rc \
    vendor/lype/prebuilt/common/etc/init/init.lype-updater.rc:$(TARGET_COPY_OUT_SYSTEM_EXT)/etc/init/init.lype-updater.rc

# Boot & Ladeanimation
TARGET_SCREEN_WIDTH ?= 1080
TARGET_SCREEN_HEIGHT ?= 2400
PRODUCT_PACKAGES += \
    bootanimation.zip \
    bootanimation-dark.zip

PRODUCT_COPY_FILES += \
    vendor/lype/bootanimation/bootanimation.zip:system/media/bootanimation.zip \
    vendor/lype/bootanimation/bootanimation-dark.zip:system/media/bootanimation-dark.zip

# Performance-Optimierungen
$(call inherit-product, frameworks/base/lype/performance/lype_performance.mk)

# SDK
ifneq ($(TARGET_DISABLE_LYPE_SDK), true)
include vendor/lype/config/lype_sdk_common.mk
endif

PRODUCT_MINIMIZE_JAVA_DEBUG_INFO := true
SYSTEM_OPTIMIZE_JAVA ?= true
SYSTEMUI_OPTIMIZE_JAVA ?= true
PRODUCT_RESTRICT_VENDOR_FILES := false

PRODUCT_DEXPREOPT_SPEED_APPS += \
    CarSystemUI \
    SystemUI \
    LypeLauncher \
    LypeSettings

PRODUCT_PRODUCT_PROPERTIES += \
    dalvik.vm.systemuicompilerfilter=speed \
    ro.storage_manager.enabled=true \
    setupwizard.theme=glif_v4 \
    setupwizard.feature.day_night_mode_enabled=true \
    persist.sys.lype.glass_blur=1 \
    persist.sys.lype.refresh_rate=auto

include vendor/lype/config/version.mk
