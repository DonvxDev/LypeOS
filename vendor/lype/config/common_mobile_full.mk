$(call inherit-product, vendor/lype/config/common_mobile.mk)

PRODUCT_SIZE := full

$(call inherit-product-if-exists, external/google-fonts/google-sans-flex/fonts.mk)

PRODUCT_PACKAGES += \
    FontGoogleSansFlexOverlay

PRODUCT_PACKAGE_OVERLAYS += vendor/lype/overlay/dictionaries
PRODUCT_ENFORCE_RRO_EXCLUDED_OVERLAYS += vendor/lype/overlay/dictionaries
