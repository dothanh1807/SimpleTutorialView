package com.vllenin.tutorialview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.layout_text_content.view.*

/**
 * Created by Vllenin on 11/12/20.
 */
class TextContentView(
    context: Context,
    attrs: AttributeSet?
) : FrameLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.layout_text_content, this)
    }

    fun setContent(content: String) = apply {
        textContentDescription.text = content
    }
}