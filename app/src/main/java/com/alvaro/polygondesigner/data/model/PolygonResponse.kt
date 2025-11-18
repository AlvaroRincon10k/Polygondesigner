package com.alvaro.polygondesigner.data.model

data class PolygonResponse(
    val name: String,
    val points: List<PointDto>
) : java.io.Serializable
