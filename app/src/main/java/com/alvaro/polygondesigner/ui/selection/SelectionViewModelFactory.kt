package com.alvaro.polygondesigner.ui.selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alvaro.polygondesigner.data.repository.PolygonRepository

class SelectionViewModelFactory(
    private val repository: PolygonRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectionViewModel::class.java)) {
            return SelectionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}