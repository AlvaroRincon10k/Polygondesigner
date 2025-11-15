package com.alvaro.polygondesigner.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.alvaro.polygondesigner.model.Point

class PolygonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : View(context, attrs, defStyle) {
    private var points: List<Point> = emptyList()

    private val linePaint = Paint().apply {
        color = 0xFF000000.toInt()
        strokeWidth = 6f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val pointPaint = Paint().apply {
        color = 0xFF2196F3.toInt()
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    fun setPoints(p: List<Point>) {
        points = p
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (points.isEmpty()) return

        val centerX = width / 2f
        val centerY = height / 2f

        val scaled = points.map {
            Point(it.x + centerX, it.y + centerY)
        }

        // Dibuja líneas del polígono
        val path = Path()
        path.moveTo(scaled[0].x, scaled[0].y)

        for (i in 1 until scaled.size) {
            path.lineTo(scaled[i].x, scaled[i].y)
        }
        path.close()

        canvas.drawPath(path, linePaint)

        // Dibuja los puntos
        scaled.forEach {
            canvas.drawCircle(it.x, it.y, 18f, pointPaint)
        }
    }
}