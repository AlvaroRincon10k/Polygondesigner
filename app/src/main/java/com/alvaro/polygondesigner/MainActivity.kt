package com.alvaro.polygondesigner

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alvaro.polygondesigner.ui.custom.PolygonView
import com.alvaro.polygondesigner.utils.PolygonUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val polygonView = findViewById<PolygonView>(R.id.polygonView)

        val polygon = PolygonUtils.generateRegularPolygon(5, 200f)
        polygonView.setPoints(polygon.points)
    }
}