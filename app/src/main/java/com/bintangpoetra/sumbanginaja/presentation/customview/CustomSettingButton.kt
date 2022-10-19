package com.bintangpoetra.sumbanginaja.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bintangpoetra.sumbanginaja.R

class CustomSettingButton(context: Context, attrs: AttributeSet?): ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.layout_custom_setting_button, this)

        val customAttributeStyle = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomSettingButton,
            0, 0
        )

        val imgMenuIcon = findViewById<ImageView>(R.id.imgIcon)
        val tvMenuTitle = findViewById<TextView>(R.id.tvMenuTitle)

        try {
            imgMenuIcon.setImageDrawable(customAttributeStyle.getDrawable(R.styleable.CustomSettingButton_menuIcon))
            tvMenuTitle.text = customAttributeStyle.getString(R.styleable.CustomSettingButton_menuTitle)
        } finally {
            invalidate()
            customAttributeStyle.recycle()
        }
    }

}