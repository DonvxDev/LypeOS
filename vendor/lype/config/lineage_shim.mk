# Rebrandet ein LineageOS-Build zu Lype OS ohne Device-Tree-Änderung.
# In device.mk einfügen: $(call inherit-product, vendor/lype/config/lineage_shim.mk)

PRODUCT_BRAND := LypeOS
PRODUCT_MANUFACTURER := Lype

$(call inherit-product-if-exists, vendor/lype/config/version.mk)

PRODUCT_PACKAGE_OVERLAYS += \
    vendor/lype/overlay/common \
    frameworks/base/lype/design/overlay

PRODUCT_PACKAGES += \
    LypeLauncher \
    LypeSettings \
    LypeUpdater \
    LypeSecurity \
    LypeFiles \
    LypeBackup \
    LypeAI

PRODUCT_PRODUCT_PROPERTIES += \
    ro.product.brand=LypeOS \
    ro.product.manufacturer=Lype
