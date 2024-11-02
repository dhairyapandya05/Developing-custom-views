package com.example.myfirstlibraryproject

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.example.myfirstlibraryproject.BasicShapesView.Companion
import com.example.mylibrary.CustomViewScaffold
import kotlin.math.sqrt

@SuppressLint("ClickableViewAccessibility")
class MySliderView: CustomViewScaffold {

    //    Text view value
    var middle=0f
    var sliderChangeListener: SliderChangeListener? = null
    var value = 0f

    private val paint = Paint()
    var lineXleft=0f
    var lineXright=0f
    var lineYPOS=0f
    var lineheight=0f



//    dragging variables
    private var lastMotionEventX = 0f
    private var lastMotionEventY = 0f
    private var isDragged = false

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

    //    Function on circle is touched
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if(event==null)
//        return super.onTouchEvent(event)
//        val distanceFromCircleCenter = pointsDistance(event.x, event.y,circleX, circleY)
//        return if (distanceFromCircleCenter <= circleRadius && event.action == MotionEvent.ACTION_DOWN) {
//            isDragged = true
//            lastMotionEventX = event.x
//            lastMotionEventY = event.y // Important this is the initial touch event
//            true
//        } else if (isDragged && event.action == MotionEvent.ACTION_MOVE) {
//            val dx = event.x - lastMotionEventX   //Important: This is the drag action
//            circleX += dx
//            if(circleX< lineXleft){
//                circleX=lineXleft
//            }
//            else if(circleX>lineXright){
//                circleX=lineXright
//            }
//            else if(dx<0){
//                circleX=maxOf(circleX,lineXleft)
//            }
//            else{
//                circleX=minOf(circleX,lineXright)
//            }
//            invalidate() //onDraw will be called at some point in the future
//            lastMotionEventX = event.x// remembering the current position of the circle
//            updateValue()
//            true
//        } else {
//            isDragged = false
//            false
//        }
//    }

    private fun pointsDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val dx = x1 - x2
        val dy = y1 - y2
        return sqrt(dx*dx + dy*dy)
    }

    // Will be called when this View changes its size.
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        lineXleft=dpToPx(LINE_MARGIN_HORIZONTAL_DP)
        lineXright=w-lineXleft
        lineYPOS=h/2f
        lineheight= dpToPx(LINE_HEIGHT)

//        values for circle
        circleX=circleX+value*(lineXright-lineXleft)
        circleY=lineYPOS
        middle=w/2f
        circleRadius=dpToPx(CIRCLE_RADIUS)

    }

    // Will be called with clean Canvas when this View needs to redraw itself on the screen.
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color=Color.LTGRAY
        paint.style=Paint.Style.STROKE
        paint.strokeWidth=lineheight
        Log.d("CustomView", "lineXleft: $lineXleft, lineXright: $lineXright, lineYPOS: $lineYPOS, lineheight: $lineheight")
        canvas.drawLine(lineXleft,lineYPOS,lineXright,lineYPOS,paint)
        paint.color=Color.RED
        paint.style=Paint.Style.FILL
        Log.d("Dhairya","Value X:$circleX and Y as $circleY")
        canvas.drawCircle(width/2f,height/2f,circleRadius,paint)
    }

    private fun updateValue() {
        val lineWidth = lineXright - lineXleft
        val relativeThumbPosition = circleX - lineXleft
        val oldValue = value
        value = 1.0f * relativeThumbPosition / lineWidth
        if (value != oldValue) {
            sliderChangeListener?.onValueChanged(value)
        }
    }
    override fun onSaveInstanceState(): Parcelable {
        val superSavedState = super.onSaveInstanceState()
        return MySavedState(superSavedState, value)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is MySavedState) {
            super.onRestoreInstanceState(state.superSavedState)
            value = state.sliderValue
        } else {
            super.onRestoreInstanceState(state)
        }

    }
    companion object{
        const val LINE_HEIGHT=8f
        const val LINE_Y_POS_FRACTION=0.3f
        const val LINE_MARGIN_HORIZONTAL_DP=40f
        const val CIRCLE_RADIUS=15f
    }

    private class MySavedState: BaseSavedState {

        val superSavedState: Parcelable?
        val sliderValue: Float

        constructor(superSavedState: Parcelable?, sliderValue: Float): super(superSavedState) {
            this.superSavedState = superSavedState
            this.sliderValue = sliderValue
        }

        constructor(parcel: Parcel) : super(parcel) {
            this.superSavedState = parcel.readParcelable(null)
            this.sliderValue = parcel.readFloat()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeParcelable(superSavedState, flags)
            out.writeFloat(sliderValue)
        }

        companion object CREATOR : Parcelable.Creator<MySavedState> {
            override fun createFromParcel(parcel: Parcel): MySavedState {
                return MySavedState(parcel)
            }

            override fun newArray(size: Int): Array<MySavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}