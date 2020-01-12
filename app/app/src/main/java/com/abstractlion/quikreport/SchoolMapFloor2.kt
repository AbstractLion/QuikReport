package com.abstractlion.quikreport

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

public class SchoolMapFloor2 @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
View(context, attrs, defStyleAttr) {

    private val mCustomImage : Drawable? = context.getResources().getDrawable(R.drawable.floor_2)

    private val testPaint = Paint().apply {
        isAntiAlias = true
        color = Color.RED
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val imageBounds : Rect? = canvas?.getClipBounds()
        mCustomImage!!.setBounds(imageBounds!!)
        mCustomImage.draw(canvas!!)

        canvas.drawRect(Rect(0, 0, 10, 10), testPaint)
    }

    override fun onCapturedPointerEvent(motionEvent: MotionEvent): Boolean {
        println("got here")
        // Get the coordinates required by your app
        val verticalOffset: Float = motionEvent.y
        val horizontalOffset: Float = motionEvent.x
        println("x: $x, y: $y")
        // Use the coordinates to update your view and return true if the event was
        // successfully processed
        return true
    }
}
