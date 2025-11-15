package com.alvaro.polygondesigner.utils

import com.alvaro.polygondesigner.model.Point
import com.alvaro.polygondesigner.model.Polygon
import kotlin.math.cos
import kotlin.math.sin

object PolygonUtils {
    /**
     * Genera un polígono regular centrado en el (0,0)
     * @param sides número de lados
     * @param radius tamaño del polígono
     */
    fun generateRegularPolygon(sides: Int, radius: Float): Polygon {
        require(sides >= 3) { "Un polígono debe tener mínimo 3 lados" }

        val angleStep = (2 * Math.PI / sides).toFloat()
        val points = mutableListOf<Point>()

        for (i in 0 until sides) {
            val angle = angleStep * i
            val x = (cos(angle.toDouble()) * radius).toFloat()
            val y = (sin(angle.toDouble()) * radius).toFloat()
            points.add(Point(x, y))
        }

        return Polygon(
            sides = sides,
            radius = radius,
            points = points
        )
    }
}