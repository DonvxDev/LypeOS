# Lype OS SDK – gemeinsame Konfiguration
PRODUCT_PACKAGES += \
    org.lypeos.platform

PRODUCT_BOOT_JARS += \
    lype-sdk

PRODUCT_SYSTEM_EXT_PROPERTIES += \
    persist.sys.lype.sdk=1

# Lineage SDK bleibt für Gerätekompatibilität aktiv
ifneq ($(TARGET_DISABLE_LINEAGE_SDK), true)
$(call inherit-product-if-exists, vendor/lineage/config/lineage_sdk_common.mk)
endif
