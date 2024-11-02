package com.example.myfirstlibraryproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.example.mylibrary.CustomViewScaffold
import kotlin.random.Random

class waveanimation : CustomViewScaffold {
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

    private val wavePaint1 = Paint().apply {
        color = Color.argb(255, 144, 213, 255); // Light Blue with 25% transparency
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val wavePaint2 = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private var wavePhase1 = 0f // Controls the animation phase
    private var waveAmplitude1 = 50f // Height of the wave
    private var waveFrequency1 = 0.02f // Frequency of the wave

    private var wavePhase2 = 30f // Controls the animation phase
    private var waveAmplitude2 = 40f // Height of the wave
    private var waveFrequency2 = 0.01f // Frequency of the wave

    init {
        // Redraw view at a regular interval to create an animation
        postInvalidateOnAnimation()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawWave(canvas)
        animateWave()
    }

    private fun drawWave(canvas: Canvas) {
        val width = width.toFloat()
        val height = height.toFloat()

        // Draw a sine wave pattern across the width of the view
        val path1 = android.graphics.Path()
        val path2 = android.graphics.Path()

        path1.moveTo(0f, height / 2)
        path2.moveTo(0f, height / 2)
        for (x in 0 until width.toInt()) {
            val y1 = (height / 2 + waveAmplitude1 * Math.sin((x * waveFrequency1 + wavePhase1).toDouble())).toFloat()
            val y2 = (height / 2 + waveAmplitude2 * Math.cos((x * waveFrequency2 + wavePhase2).toDouble())).toFloat()

            path1.lineTo(x.toFloat(), y1)
            path2.lineTo(x.toFloat(), y2)

        }

        path1.lineTo(width, height) // Connect to the bottom right corner
        path2.lineTo(width, height) // Connect to the bottom right corner

        path1.lineTo(0f, height) // Connect to the bottom left corner
        path2.lineTo(0f, height) // Connect to the bottom left corner

        path1.close()
        path2.close()


        canvas.drawPath(path1, wavePaint1)
        canvas.drawPath(path2, wavePaint2)

    }

    private fun animateWave() {
        wavePhase1 += 0.1f // Increase phase to move the wave
        wavePhase2 += 0.1f // Increase phase to move the wave
        waveAmplitude1 = (waveAmplitude1 - 5) + Random.nextFloat() * ((waveAmplitude2 + 5) - (waveAmplitude1 - 5))
        if (wavePhase1 > 360) wavePhase1 = 0f // Reset phase periodically
        if (wavePhase2 > 360) wavePhase2 = 0f // Reset phase periodically

        // Request a redraw to create an animation loop
        postInvalidateOnAnimation()
    }
}