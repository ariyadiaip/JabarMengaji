package edu.ariyadi.jabarmengaji.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ariyadi.jabarmengaji.data.model.Place
import edu.ariyadi.jabarmengaji.data.repository.MosqueRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MosqueViewModel @Inject constructor(
    private val repository: MosqueRepository
) : ViewModel() {

    private val _nearbyMosques = MutableStateFlow<List<Place>>(emptyList())
    val nearbyMosques: StateFlow<List<Place>> = _nearbyMosques

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchNearbyMosques(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getNearbyMosques(latitude, longitude)
                _nearbyMosques.value = response.results
            } catch (e: Exception) {
                Log.e("MosqueViewModel", "Error fetching nearby mosques: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

}