package com.alvaro.polygondesigner.ui.selection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaro.polygondesigner.data.repository.PolygonRepository
import com.alvaro.polygondesigner.data.model.PolygonResponse
import kotlinx.coroutines.launch

class SelectionViewModel(
    private val repository: PolygonRepository
) : ViewModel() {
    val polygons = MutableLiveData<List<PolygonResponse>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    fun loadPolygons() {
        loading.value = true

        viewModelScope.launch {
            try {
                val result = repository.getPolygons()
                polygons.value = result
            } catch (e: Exception) {
                error.value = e.message
            } finally {
                loading.value = false
            }
        }
    }
}