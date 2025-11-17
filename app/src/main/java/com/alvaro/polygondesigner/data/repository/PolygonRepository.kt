package com.alvaro.polygondesigner.data.repository

import com.alvaro.polygondesigner.data.remote.ApiService

class PolygonRepository(
    private val api: ApiService
) {
    suspend fun getPolygons() = api.getPolygons()
}