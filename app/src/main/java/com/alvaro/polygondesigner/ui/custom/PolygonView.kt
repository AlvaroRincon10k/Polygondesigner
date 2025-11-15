package com.alvaro.polygondesigner.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.alvaro.polygondesigner.model.Point
import kotlin.math.hypot

class PolygonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var points: MutableList<Point> = mutableListOf()
    private var draggingIndex: Int? = null

    private val pointRadius = 40f   // para seleccionar más fácil

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
        points = p.map { Point(it.x, it.y) }.toMutableList()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (points.isEmpty()) return

        val centerX = width / 2f
        val centerY = height / 2f

        val scaled = points.map { Point(it.x + centerX, it.y + centerY) }

        drawPolygon(canvas, scaled)
        drawPoints(canvas, scaled)
    }

    private fun drawPolygon(canvas: Canvas, scaled: List<Point>) {
        val path = Path()
        path.moveTo(scaled[0].x, scaled[0].y)

        for (i in 1 until scaled.size) {
            path.lineTo(scaled[i].x, scaled[i].y)
        }

        path.close()
        canvas.drawPath(path, linePaint)
    }

    private fun drawPoints(canvas: Canvas, scaled: List<Point>) {
        scaled.forEach {
            canvas.drawCircle(it.x, it.y, 20f, pointPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val centerX = width / 2f
        val centerY = height / 2f

        val touchX = event.x - centerX
        val touchY = event.y - centerY

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                draggingIndex = findClosestPoint(touchX, touchY)
            }

            MotionEvent.ACTION_MOVE -> {
                draggingIndex?.let { index ->
                    points[index].x = touchX
                    points[index].y = touchY
                    invalidate()
                }
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                draggingIndex = null
            }
        }

        return true
    }

    private fun findClosestPoint(tx: Float, ty: Float): Int? {
        points.forEachIndexed { index, point ->
            val dist = hypot(point.x - tx, point.y - ty)
            if (dist <= pointRadius) {
                return index
            }
        }
        return null
    }
}