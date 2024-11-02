package com.example.myfirstlibraryproject

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myfirstlibraryproject.MySliderView

class MainActivity : AppCompatActivity() {
    //    private lateinit var viewAnimation1: tickmark
//    private lateinit var viewAnimation2: tickmark
    private lateinit var starView: drawingstar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        viewAnimation1 = findViewById(R.id.tickmarkView1)
//        viewAnimation2 = findViewById(R.id.tickmarkView2)
        starView = findViewById(R.id.starview)
//        starView.starChangeListener = object : drawingstar.RateChangeListener {
//            override fun onRateTouchDown(x: Float, y: Float) {
////                Log.d("TouchDown", "Touch down at ($x, $y)")
//            }
//
//            override fun onRateDrag(x: Float, y: Float) {
////                Log.d("Drag", "Dragging at ($x, $y)")
//            }
//        }


//    companion object{
//        const val ANIMATION_DURATION_MS=2000L
//    }
//    fun dpToPx(dp:Float):Float{
//        return TypedValue.applyDimension(
//            TypedValue.COMPLEX_UNIT_DIP,
//            dp,
//            context.resources.displayMetrics)
//    }
    }
}