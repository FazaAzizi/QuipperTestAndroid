package com.faza.quippertest.utils

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

class CustomTypefaceSpan : TypefaceSpan {
    private val mNewType: Typeface

    constructor(type: Typeface) : super("") {
        mNewType = type
    }

    constructor(family: String, type: Typeface) : super(family) {
        mNewType = type
    }

    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeFace(ds, mNewType)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, mNewType)
    }

    private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
        val oldStyle: Int
        val old = paint.typeface
        if (old == null) {
            oldStyle = 0
        } else {
            oldStyle = old.style
        }

        val fake = oldStyle and tf.style.inv()
        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }

        paint.typeface = tf
    }
}