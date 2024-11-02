package com.example.myfirstlibraryproject

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import com.example.mylibrary.CustomViewScaffold
import kotlin.math.pow
import kotlin.math.sqrt

class tickmark : CustomViewScaffold {

    private val checkmarkPaint = Paint()
    private var checkmarkLineSize = 0f
    private var checkmarkShortSideLength = 0f
    private val referenceCheckmarkPath = Path()
    private val checkmarkPath = Path()
    private val pivotPoint = PointF()
    private val viewBorderPaint = Paint()
    private val viewBorderRect = RectF()

    private var pathValueAnimator: ValueAnimator? = null
    private var scaleValueAnimator: ValueAnimator? = null
    private var animatorSet: AnimatorSet? = null
    private var scale = 1f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    fun startAnimation(durationMs: Long) {
        pathValueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            interpolator = LinearInterpolator()
            duration = durationMs
            addUpdateListener {
                updateCheckmarkPath(it.animatedValue as Float)
            }
        }
        scaleValueAnimator = ValueAnimator.ofFloat(1f, 1.1f, 1f).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = durationMs / 2
            addUpdateListener {
                scale = it.animatedValue as Float
                invalidate()
            }
        }
        animatorSet = AnimatorSet().apply {
            play(pathValueAnimator)
            play(scaleValueAnimator).after(durationMs * 3/4)
            start()
        }
    }

    fun stopAnimation() {
        animatorSet?.cancel()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val viewBorderLineSize = dpToPx(VIEW_BORDER_LINE_SIZE_DP)
        val viewBorderPadding = viewBorderLineSize / 2
        viewBorderRect.set(
            viewBorderPadding,
            viewBorderPadding,
            width.toFloat() - viewBorderPadding,
            height.toFloat() - viewBorderPadding
        )

        viewBorderPaint.color = Color.RED
        viewBorderPaint.style = Paint.Style.STROKE
        viewBorderPaint.strokeWidth = viewBorderLineSize
        viewBorderPaint.pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
        checkmarkLineSize = dpToPx(LINE_SIZE_DP)

        checkmarkPaint.color = ContextCompat.getColor(context, R.color.green)
        checkmarkPaint.style = Paint.Style.STROKE
        checkmarkPaint.strokeWidth = checkmarkLineSize
        checkmarkPaint.isAntiAlias = true

        updateReferenceCheckmarkPath(w, h, checkmarkLineSize)
    }

    private fun updateReferenceCheckmarkPath(viewWidth: Int, viewHeight: Int, minPadding: Float) {
        checkmarkShortSideLength = calculateCheckmarkShortSideLength(viewWidth, viewHeight, minPadding)
        val checkmarkWidth = sqrt(5f) * checkmarkShortSideLength
        val checkmarkHeight = 2 * checkmarkShortSideLength / sqrt(5f)
        val checkmarkTop = (viewHeight - checkmarkHeight) / 2
        val checkmarkLeft = (viewWidth - checkmarkWidth) / 2
        val pivotPointX = checkmarkLeft + sqrt(checkmarkShortSideLength.pow(2) - checkmarkHeight.pow(2))

        val startPoint = PointF(checkmarkLeft, checkmarkTop)
        pivotPoint.set(pivotPointX, checkmarkTop + checkmarkHeight)
        val endPoint = PointF(checkmarkLeft + checkmarkWidth, checkmarkTop)

        referenceCheckmarkPath.reset()
        referenceCheckmarkPath.moveTo(startPoint.x, startPoint.y)
        referenceCheckmarkPath.lineTo(pivotPoint.x, pivotPoint.y)
        referenceCheckmarkPath.lineTo(endPoint.x, endPoint.y)
    }

    private fun updateCheckmarkPath(fraction: Float) {
        val pathMeasure = PathMeasure(referenceCheckmarkPath, false)
        val totalLength = 3 * checkmarkShortSideLength
        checkmarkPath.reset()
        pathMeasure.getSegment(0f, fraction * totalLength, checkmarkPath, true)
        invalidate()
    }

    private fun calculateCheckmarkShortSideLength(viewWidth: Int, viewHeight: Int, minPadding: Float): Float {
        val checkmarkShortSideLengthCandidate = sqrt((viewWidth - 2 * minPadding).pow(2) / 5)
        val checkMarkHeightCandidate = 2 * checkmarkShortSideLengthCandidate / sqrt(5f)
        return if (checkMarkHeightCandidate <= viewHeight - 2 * minPadding) {
            checkmarkShortSideLengthCandidate
        } else {
            (viewHeight - 2 * minPadding) * sqrt(5f) / 2
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.scale(scale, scale, pivotPoint.x, pivotPoint.y)
        canvas.drawPath(checkmarkPath, checkmarkPaint)
        canvas.drawRect(viewBorderRect, viewBorderPaint)
    }
    companion object {
        const val VIEW_BORDER_LINE_SIZE_DP = 1f
        const val LINE_SIZE_DP = 15f
    }
}