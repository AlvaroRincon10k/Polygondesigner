package com.alvaro.polygondesigner.ui.selection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvaro.polygondesigner.databinding.ActivitySelectionBinding
import com.alvaro.polygondesigner.data.remote.RetrofitClient
import com.alvaro.polygondesigner.data.repository.PolygonRepository

class SelectionActivity : ComponentActivity() {

    private lateinit var binding: ActivitySelectionBinding

    private val viewModel: SelectionViewModel by viewModels {
        SelectionViewModelFactory(PolygonRepository(RetrofitClient.api))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.polygonList.layoutManager = LinearLayoutManager(this)

        viewModel.polygons.observe(this) { list ->
            val adapter = PolygonAdapter(list) { polygon ->
                // TODO: navegar a la pantalla de diseño
            }
            binding.polygonList.adapter = adapter
        }
        viewModel.loadPolygons()
    }
}