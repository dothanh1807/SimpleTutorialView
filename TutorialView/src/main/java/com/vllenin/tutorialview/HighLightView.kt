package com.vllenin.tutorialview

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.min

/**
 * Created by Vllenin on 11/11/20.
 */
class HighLightView(
    context: Context,
    attrs: AttributeSet?
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val listPath = ArrayList<Path>()

    override fun onDraw(canvas: Canvas?) {
        listPath.forEach { path ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                canvas?.clipOutPath(path)
            } else {
                canvas?.clipPath(path, Region.Op.DIFFERENCE)
            }
        }
        paint.color = ContextCompat.getColor(context, R.color.app_transparent_black_40)
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }

    fun focusToViews(mapViewNeedTutorial: Map<String, View>, callbackMarginTop: (valueMargin: Int, contentIsBottomView: Boolean, centerOfView: Int, content: String) -> Unit) {
        listPath.clear()
        for ((string, view) in mapViewNeedTutorial) {
            val location = IntArray(2)
            this.getLocationOnScreen(location)
            val marginWithTopOfScreen = location[1]
            view.getLocationOnScreen(location)
            val rectClipOfView = RectF(
                location[0].toFloat(),
                location[1].toFloat() - marginWithTopOfScreen,
                location[0] + view.width.toFloat(),
                location[1] + view.height.toFloat() - marginWithTopOfScreen
            )
            val pathBorderView = Path()
            pathBorderView.addRoundRect(rectClipOfView,
                min(view.width.toFloat() / 10f, resources.getDimension(R.dimen.default_margin_normal)),
                min(view.height.toFloat() / 10f, resources.getDimension(R.dimen.default_margin_normal)),
                Path.Direction.CW)

            val sizeTutorialView = resources.getDimensionPixelSize(R.dimen.size_view_tutorial_container)
            val marginBetweenPathWithContent = resources.getDimensionPixelSize(R.dimen.default_margin_xxlarge)
            val centerOfBorderView = rectClipOfView.centerX().toInt()
            val tutorialViewIsBottomView: Boolean
            val marginTop: Int = if ((height - rectClipOfView.bottom) > sizeTutorialView + marginBetweenPathWithContent) {
                tutorialViewIsBottomView = true
                rectClipOfView.bottom.toInt() + marginBetweenPathWithContent
            } else {
                tutorialViewIsBottomView = false
                rectClipOfView.top.toInt() - marginBetweenPathWithContent - sizeTutorialView
            }
            callbackMarginTop.invoke(marginTop, tutorialViewIsBottomView, centerOfBorderView, string)

            listPath.add(pathBorderView)
        }

        invalidate()
    }

}