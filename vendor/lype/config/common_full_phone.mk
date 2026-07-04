# Telefon-Produkt – Einstiegspunkt für Device-Trees
$(call inherit-product, vendor/lype/config/common_mobile_full.mk)

PRODUCT_PRODUCT_PROPERTIES += \
    ro.support_one_handed_mode?=true

$(call inherit-product-if-exists, vendor/lype/config/telephony.mk)
