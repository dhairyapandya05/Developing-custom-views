package com.example.myfirstlibraryproject

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

open class BasicShapesView: View {



    private val paint = Paint()
    private var lineXleft=0f
    private var lineXright=0f
    private var lineYPOS=0f
    private var lineheight=0f
    private var circleX=0f
    private var circleY=0f
    private var circleRadius=0f



    private val rectangleRect=RectF()

    // Secondary constructors (don't use @JvmOverloads as it can lead to bugs).
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    // Will be called when this View changes its size.
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
//        initialising the values
        lineXleft=dpToPx(LINE_MARGIN_HORIZONTAL_DP)
        lineXright=width.toFloat()-dpToPx(LINE_MARGIN_HORIZONTAL_DP)
        lineYPOS=h*LINE_Y_POS_FRACTION
        lineheight=dpToPx(LINE_HEIGHT)

//        rectangle values
        var rectangleWidth= w-2*dpToPx(LINE_MARGIN_HORIZONTAL_DP)
        var rectheight=rectangleWidth/2
        var rectTop=(h-rectheight)/2
        var rectBottom=(h+rectheight)/2
        rectangleRect.set(dpToPx(RECT_MARGIN_HORIZONTAL_DP),rectTop,rectangleWidth+dpToPx(RECT_MARGIN_HORIZONTAL_DP),rectBottom)

        //circle configuaration
        circleX=w/2f;
        circleY=h*CIRCLE_POS_FRACTION
        circleRadius=h*CIRCLE_RADIUS
    }

    // Will be called with clean Canvas when this View needs to redraw itself on the screen.
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color=Color.RED
        paint.style=Paint.Style.STROKE
        paint.strokeWidth=dpToPx(LINE_HEIGHT)
        canvas.drawLine(lineXleft,lineYPOS,lineXright,lineYPOS,paint)

        // drawing a rectangle
        paint.color=Color.GREEN
        paint.style=Paint.Style.FILL
        canvas.drawRect(rectangleRect,paint)

        //drawing a circle
        paint.color=Color.BLUE
        paint.style=Paint.Style.FILL
        canvas.drawCircle(circleX,circleY,circleRadius,paint)
    }

    fun dpToPx(dp:Float):Float{
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics)
    }
    companion object{
        const val LINE_HEIGHT=10f
        const val LINE_Y_POS_FRACTION=0.3f
        const val LINE_MARGIN_HORIZONTAL_DP=20f
        const val RECT_MARGIN_HORIZONTAL_DP=20f
        const val CIRCLE_POS_FRACTION=0.8f
        const val CIRCLE_RADIUS=0.1f
    }
}