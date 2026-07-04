# Lype OS SDK
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := lype-sdk
LOCAL_MODULE_TAGS := optional
LOCAL_MODULE_CLASS := JAVA_LIBRARIES
LOCAL_SRC_FILES := sdk/src/com/lype/os/Build.java
include $(BUILD_STATIC_JAVA_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := org.lypeos.platform
LOCAL_MODULE_TAGS := optional
LOCAL_MODULE_CLASS := JAVA_LIBRARIES
LOCAL_SRC_FILES := api/system/current/com/lype/os/LypeManager.java
include $(BUILD_STATIC_JAVA_LIBRARY)
