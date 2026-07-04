# Lype OS Versionierung (Android 16 / Lineage 23.2 Basis)
PRODUCT_VERSION_MAJOR := 1
PRODUCT_VERSION_MINOR := 0

ifeq ($(LYPE_VERSION_APPEND_TIME_OF_DAY),true)
    LYPE_BUILD_DATE := $(shell date -u +%Y%m%d_%H%M%S)
else
    LYPE_BUILD_DATE := $(shell date -u +%Y%m%d)
endif

ifndef LYPE_BUILDTYPE
    ifdef RELEASE_TYPE
        RELEASE_TYPE := $(shell echo $(RELEASE_TYPE) | sed -e 's|^LYPE_||g')
        LYPE_BUILDTYPE := $(RELEASE_TYPE)
    endif
endif

# Stable, Beta, Alpha, Nightly, UNOFFICIAL
ifeq ($(filter STABLE BETA ALPHA NIGHTLY RELEASE SNAPSHOT EXPERIMENTAL,$(LYPE_BUILDTYPE)),)
    LYPE_BUILDTYPE := UNOFFICIAL
    LYPE_EXTRAVERSION :=
endif

ifeq ($(LYPE_BUILDTYPE), UNOFFICIAL)
    ifneq ($(TARGET_UNOFFICIAL_BUILD_ID),)
        LYPE_EXTRAVERSION := -$(TARGET_UNOFFICIAL_BUILD_ID)
    endif
endif

LYPE_VERSION_SUFFIX := $(LYPE_BUILD_DATE)-$(LYPE_BUILDTYPE)$(LYPE_EXTRAVERSION)-$(LYPE_BUILD)

LYPE_VERSION := $(PRODUCT_VERSION_MAJOR).$(PRODUCT_VERSION_MINOR)-$(LYPE_VERSION_SUFFIX)
LYPE_DISPLAY_VERSION := Lype OS $(PRODUCT_VERSION_MAJOR).$(PRODUCT_VERSION_MINOR)-$(LYPE_VERSION_SUFFIX)

# System-Properties
PRODUCT_PRODUCT_PROPERTIES += \
    ro.lype.version=$(LYPE_VERSION) \
    ro.lype.display.version=$(LYPE_DISPLAY_VERSION) \
    ro.lype.build.version=$(PRODUCT_VERSION_MAJOR).$(PRODUCT_VERSION_MINOR) \
    ro.lype.releasetype=$(LYPE_BUILDTYPE) \
    ro.lype.android_version=16

# LineageOS-Kompatibilität für bestehende Device-Trees
LINEAGE_VERSION := $(LYPE_VERSION)
LINEAGE_DISPLAY_VERSION := $(LYPE_DISPLAY_VERSION)
LINEAGE_BUILDTYPE := $(LYPE_BUILDTYPE)

PRODUCT_PRODUCT_PROPERTIES += \
    ro.lineage.version=$(LYPE_VERSION) \
    ro.lineage.display.version=$(LYPE_DISPLAY_VERSION) \
    ro.lineage.build.version=$(PRODUCT_VERSION_MAJOR).$(PRODUCT_VERSION_MINOR) \
    ro.lineage.releasetype=$(LYPE_BUILDTYPE)
