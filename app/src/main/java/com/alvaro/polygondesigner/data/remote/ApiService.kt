package com.alvaro.polygondesigner.data.remote

import com.alvaro.polygondesigner.data.model.PolygonResponse
import retrofit2.http.GET

interface ApiService {
    @GET("polygons")
    suspend fun getPolygons(): List<PolygonResponse>
}