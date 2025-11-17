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
import kotlin.math.min

class PolygonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var points: MutableList<Point> = mutableListOf()
    private var draggingIndex: Int? = null

    private val pointRadius = 40f

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

    private var initialized = false

    fun setPoints(input: List<Point>) {
        points = input.map { Point(it.x, it.y) }.toMutableList()
        initialized = false
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (points.isEmpty()) return

        if (!initialized) {
            scalePolygonToFit()
            initialized = true
        }

        drawPolygon(canvas)
    }

    private fun scalePolygonToFit() {
        val minX = points.minOf { it.x }
        val maxX = points.maxOf { it.x }
        val minY = points.minOf { it.y }
        val maxY = points.maxOf { it.y }

        val widthPoly = maxX - minX
        val heightPoly = maxY - minY

        val scale = 0.7f * min(width / widthPoly, height / heightPoly)

        val offsetX = width / 2f
        val offsetY = height / 2f

        val centerX = minX + widthPoly / 2f
        val centerY = minY + heightPoly / 2f

        points.forEach { p ->
            p.x = (p.x - centerX) * scale + offsetX
            p.y = (p.y - centerY) * scale + offsetY
        }
    }

    private fun drawPolygon(canvas: Canvas) {
        val path = Path()
        path.moveTo(points[0].x, points[0].y)

        for (i in 1 until points.size) {
            path.lineTo(points[i].x, points[i].y)
        }

        path.close()
        canvas.drawPath(path, linePaint)

        for (p in points) {
            canvas.drawCircle(p.x, p.y, 16f, pointPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                draggingIndex = findClosestPoint(event.x, event.y)
            }

            MotionEvent.ACTION_MOVE -> {
                draggingIndex?.let {
                    points[it].x = event.x
                    points[it].y = event.y
                    invalidate()
                }
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> draggingIndex = null
        }
        return true
    }

    private fun findClosestPoint(x: Float, y: Float): Int? {
        points.forEachIndexed { index, p ->
            if (hypot(p.x - x, p.y - y) <= pointRadius) {
                return index
            }
        }
        return null
    }

    fun getPoints(): List<Point> = points
}