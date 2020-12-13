package com.vllenin.tutorialview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.layout_high_light_tutorial.view.*
import kotlinx.android.synthetic.main.layout_text_content.view.*

/**
 * Created by Vllenin on 11/12/20.
 */
class HighLightTutorialView(
    context: Context,
    attrs: AttributeSet?
) : RelativeLayout(context, attrs) {

    private var animTranslate: TranslateAnimation? = null
    private var callback: () -> Unit = {}
    private val listViewAdded = ArrayList<View>()

    init {
        visibility = View.INVISIBLE
        View.inflate(context, R.layout.layout_high_light_tutorial, this)
        highLightView.setOnClickListener {
            listViewAdded.forEach {
                // If you want remove view is success, then you must clearAnimation
                it.clearAnimation()
                viewContainer.removeView(it)
            }
            animate().alpha(0f).setDuration(100)
                .withEndAction {
                    visibility = View.INVISIBLE
                    callback.invoke()
                }
                .start()
        }

        animTranslate = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, -0.25f)
        animTranslate?.duration = 1000L
        animTranslate?.repeatMode = Animation.REVERSE
        animTranslate?.repeatCount = Animation.INFINITE
    }

    fun focusToView(mapViewNeedTutorial: Map<String, View>) = apply {
        listViewAdded.clear()
        highLightView.focusToViews(mapViewNeedTutorial) { marginTop, contentIsBottomView, centerOfView, content ->
            val textContentView = TextContentView(context, null)
            val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            lp.topMargin = marginTop
            textContentView.layoutParams = lp

            textContentView.startAnimation(animTranslate)
            textContentView.setContent(content)
            val coordinateRightTextContent = context.resources.displayMetrics.widthPixels - resources.getDimensionPixelSize(R.dimen.default_margin_xlarge)
            val coordinateLeftTextContent = resources.getDimensionPixelSize(R.dimen.default_margin_xlarge)

            var marginStartOfArrow = centerOfView - resources.getDimensionPixelSize(R.dimen.default_margin_xlarge) / 2
            if (marginStartOfArrow > coordinateRightTextContent - resources.getDimensionPixelSize(R.dimen.default_margin_xlarge)) {
                marginStartOfArrow = coordinateRightTextContent - resources.getDimensionPixelSize(R.dimen.default_margin_xlarge)
            }
            if (marginStartOfArrow < coordinateLeftTextContent) {
                marginStartOfArrow = coordinateLeftTextContent
            }
            if (contentIsBottomView) {
                textContentView.viewArrowOnTop.visibility = View.VISIBLE

                val lpArrow = textContentView.viewArrowOnTop.layoutParams as LayoutParams
                lpArrow.marginStart = marginStartOfArrow
                textContentView.viewArrowOnTop.layoutParams = lpArrow
            } else {
                textContentView.viewArrowOnBottom.visibility = View.VISIBLE

                val lpArrow = textContentView.viewArrowOnBottom.layoutParams as LayoutParams
                lpArrow.marginStart = marginStartOfArrow
                textContentView.viewArrowOnBottom.layoutParams = lpArrow
            }

            viewContainer.addView(textContentView, lp)
            listViewAdded.add(textContentView)
        }

        animate().alpha(1f).setDuration(100)
            .withEndAction {
                visibility = View.VISIBLE
            }
            .start()
    }

    fun setCancelListener(callback: () -> Unit) = apply {
        this.callback = callback
    }

}