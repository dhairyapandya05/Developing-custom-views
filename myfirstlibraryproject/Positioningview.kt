package com.example.myfirstlibraryproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View

open class Positioningview: View {


    private val paint = Paint()
//    var lineXleft=0f
//    var lineXright=0f
//    var lineYPOS=0f
//    var lineheight=0f

    // values for circle
    var circleX=0f
    var circleY=0f
    var circleRadius=0f
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
//        lineXleft=dpToPx(LINE_MARGIN_HORIZONTAL_DP)
//        lineXright=w-lineXleft
//        lineYPOS=h/2f
//        lineheight= dpToPx(LINE_HEIGHT)

//        values for circle
        circleX=dpToPx(w/2f)
        circleY=dpToPx(h/2f)
        circleRadius=dpToPx(CIRCLE_RADIUS)
    }

    // Will be called with clean Canvas when this View needs to redraw itself on the screen.
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

//        paint.color=Color.LTGRAY
//        paint.style=Paint.Style.STROKE
//        paint.strokeWidth=lineheight
//        Log.d("CustomView", "lineXleft: $lineXleft, lineXright: $lineXright, lineYPOS: $lineYPOS, lineheight: $lineheight")
//        canvas.drawLine(lineXleft,lineYPOS,lineXright,lineYPOS,paint)
        paint.color=Color.RED
        paint.style=Paint.Style.FILL
        canvas.drawCircle(width/2f,height/2f,circleRadius,paint)
    }

    fun dpToPx(dp:Float):Float{
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics)
    }

    companion object{
        const val LINE_HEIGHT=8f
        const val LINE_Y_POS_FRACTION=0.3f
        const val LINE_MARGIN_HORIZONTAL_DP=20f
        const val CIRCLE_RADIUS=15f
    }
}