# Lype OS Common Device Configuration
$(call inherit-product, vendor/lype/config/common_full_phone.mk)

PRODUCT_NAME := lype_common
PRODUCT_DEVICE := lype
PRODUCT_BRAND := LypeOS
PRODUCT_MODEL := Lype OS
PRODUCT_MANUFACTURER := Lype

PRODUCT_SYSTEM_PROPERTIES += \
    ro.lype.device=common
