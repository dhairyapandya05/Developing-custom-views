package com.example.myfirstlibraryproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.mylibrary.CustomViewScaffold

class drawingstar: CustomViewScaffold {
    // Secondary constructors (don't use @JvmOverloads as it can lead to bugs).
    interface OnTouchInsideListener {
        fun onDragInside(x: Float, y: Float)
        fun onClickInside(x: Float, y: Float)
    }

    var onTouchInsideListener: OnTouchInsideListener? = null

    // Other view properties...
    var value = 0f
    private var isDragged = false
    private var lastMotionEventX = 0f
    private var path = Path()
    private var path2 = Path()
    private var path3 = Path()
    private var path4 = Path()
    private var path5 = Path()
    private val topLeft = Point(0, 0)
    private val topRight = Point(0, 0)
    private val bottomLeft = Point(0, 0)
    private val bottomRight = Point(0, 0)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context, attrs, defStyleAttr, defStyleRes
    )

    private val starPaint = Paint().apply {
        color = Color.argb(64, 255, 184, 28)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val starPaint1 = Paint().apply {
        color = Color.argb(255, 255, 184, 28)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val paths = listOf(path, path2, path3, path4, path5)
    private val starRadiusOuter = 80f
    private val starRadiusInner = 40f
    private val spacingBetweenStars = 20f
    private var startX = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        startX = (width - (5 * (2 * starRadiusOuter + spacingBetweenStars) - spacingBetweenStars)) / 2f
        updateStarPaths()
    }

    private fun updateStarPaths() {
        paths.forEachIndexed { index, path ->
            val cx = startX + index * (2 * starRadiusOuter + spacingBetweenStars)
            val cy = height / 2f
            createStarPath(path, cx, cy, starRadiusOuter, starRadiusInner, 5)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paths.forEach { path ->
            canvas.drawPath(path, starPaint1)
        }
    }

    private fun createStarPath(path: Path, cx: Float, cy: Float, outerRadius: Float, innerRadius: Float, points: Int) {
        val angle = Math.PI / points
        val rotationOffset = -Math.PI / 2
        path.reset()

        for (i in 0 until 2 * points) {
            val r = if (i % 2 == 0) outerRadius else innerRadius
            val x = (cx + r * Math.cos(i * angle + rotationOffset)).toFloat()
            val y = (cy + r * Math.sin(i * angle + rotationOffset)).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return super.onTouchEvent(event)
        val view = this

        return when {
            event.action == MotionEvent.ACTION_DOWN && isTouchInsideView(view, event) -> {
                isDragged = true
                lastMotionEventX = event.x
                onTouchInsideListener?.onClickInside(event.x, event.y)
                true
            }
            isDragged && event.action == MotionEvent.ACTION_MOVE -> {
                Log.d("Dhairyass", "${event.x} and ${event.y}")
                onTouchInsideListener?.onDragInside(event.x, event.y)
                true
            }
            else -> {
                isDragged = false
                false
            }
        }
    }

    fun isTouchInsideView(view: View, event: MotionEvent): Boolean {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val viewLeft = location[0]
        val viewTop = location[1]
        val viewRight = viewLeft + view.width
        val viewBottom = viewTop + view.height

        val touchX = event.rawX.toInt()
        val touchY = event.rawY.toInt()

        return touchX in viewLeft..viewRight && touchY in viewTop..viewBottom
    }
}