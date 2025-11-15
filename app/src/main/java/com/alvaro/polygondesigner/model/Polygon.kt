package com.alvaro.polygondesigner.model

data class Polygon(
    val sides: Int,
    val radius: Float,
    val points: List<Point>
)
