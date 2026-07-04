package com.lype.os;

import android.content.Context;

/**
 * Lype OS Platform Manager – zentrale API für System-Apps.
 */
public class LypeManager {
    public static final String FEATURE_LYPE_OS = "com.lypeos.android";

    private final Context mContext;

    public LypeManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public boolean isLypeOS() {
        return mContext.getPackageManager().hasSystemFeature(FEATURE_LYPE_OS);
    }

    public String getVersion() {
        return Build.VERSION;
    }
}
