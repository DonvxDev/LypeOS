# Lype OS Performance-Optimierungen
PRODUCT_PROPERTY_OVERRIDES += \
    dalvik.vm.heapstartsize=8m \
    dalvik.vm.heapgrowthlimit=256m \
    dalvik.vm.heapsize=512m \
    dalvik.vm.heaptargetutilization=0.75 \
    dalvik.vm.heapminfree=512k \
    dalvik.vm.heapmaxfree=8m \
    ro.lype.performance.profile=balanced

# Gaming-Modus (über Lype Settings aktivierbar)
PRODUCT_PRODUCT_PROPERTIES += \
    persist.sys.lype.gaming_mode=0 \
    persist.sys.lype.touch_boost=1

PRODUCT_PACKAGES += \
    LypePerformanceLib
