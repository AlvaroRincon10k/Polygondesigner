package com.alvaro.polygondesigner.ui.designer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.alvaro.polygondesigner.data.model.PolygonResponse
import com.alvaro.polygondesigner.databinding.ActivityPolygonDesignerBinding
import com.alvaro.polygondesigner.model.Point

class PolygonDesignerActivity : ComponentActivity() {
    private lateinit var binding: ActivityPolygonDesignerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPolygonDesignerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val polygon = intent.getSerializableExtra("polygon") as PolygonResponse
        val name = intent.getStringExtra("polygonName")
        binding.txtTitle.text = name ?: "Polygon"

        // Convertir DTO → modelo local
        val converted = polygon.points.map {
            Point(it.x.toFloat(), it.y.toFloat())
        }

        binding.polygonView.setPoints(converted)

        binding.btnSave.setOnClickListener {
            // Obtener los puntos ya editados desde el CustomView
            val updatedPoints = binding.polygonView.getPoints()

            // Convertirlos al formato que usa el backend (PointDto o similar)
            val dtoList = updatedPoints.map {
                com.alvaro.polygondesigner.data.model.PointDto(
                    x = it.x.toDouble(),
                    y = it.y.toDouble()
                )
            }

            // Actualizar el polígono
            val updatedPolygon = polygon.copy(
                points = dtoList
            )

            // TODO enviar updatedPolygon al backend
            // Aquí después haces tu Retrofit.post() o lo que uses

            // Mientras, mostramos un mensaje
            Toast.makeText(this, "Polígono guardado", Toast.LENGTH_SHORT).show()
        }
    }
}