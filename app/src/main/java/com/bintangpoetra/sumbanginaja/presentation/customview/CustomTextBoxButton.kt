package com.bintangpoetra.sumbanginaja.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bintangpoetra.sumbanginaja.R

class CustomTextBoxButton(context: Context, attrs: AttributeSet?): ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.layout_custom_text_box_button, this)

        val customAttributeStyle = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextBoxButton,
            0, 0
        )

        val tvMenuTitle = findViewById<TextView>(R.id.tvMenuTitle)

        try {
            tvMenuTitle.text = customAttributeStyle.getString(R.styleable.CustomTextBoxButton_buttonTitle)
        } finally {
            invalidate()
            customAttributeStyle.recycle()
        }
    }

}