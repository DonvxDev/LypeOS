package com.lype.design

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat

/**
 * Lype OS Glass Design System – zentrale UI-Hilfsfunktionen für Frosted-Glass-Oberflächen.
 */
object LypeGlassTheme {
    fun applyGlassSurface(view: View, cornerRadiusDp: Float = 20f, alpha: Int = 180) {
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = cornerRadiusDp * view.resources.displayMetrics.density
            setColor((alpha shl 24) or 0x00FFFFFF)
        }
        view.background = drawable
        view.clipToOutline = true
    }

    fun primaryColor(context: Context): Int =
        ContextCompat.getColor(context, com.lype.design.R.color.lype_primary)

    fun secondaryColor(context: Context): Int =
        ContextCompat.getColor(context, com.lype.design.R.color.lype_secondary)
}
