package edu.ariyadi.jabarmengaji.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ariyadi.jabarmengaji.data.model.DuaEntity
import edu.ariyadi.jabarmengaji.data.repository.DuaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DuaViewModel @Inject constructor(
    private val repository: DuaRepository
) : ViewModel() {

    private val _duaList = MutableStateFlow<List<DuaEntity>>(emptyList())
    val duaList: StateFlow<List<DuaEntity>> = _duaList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchDuaList()
    }

    private fun fetchDuaList() {
        viewModelScope.launch {
            _isLoading.value = true

            val duaCount = repository.getDuaCount()
            if (duaCount == 0) {
                repository.refreshDuaList()
            }

            repository.allDua.collectLatest { duaEntities ->
                _duaList.value = duaEntities
                _isLoading.value = false
            }
        }
    }

    fun searchDua(query: String) {
        viewModelScope.launch {
            repository.searchDua(query).collectLatest { duaEntities ->
                _duaList.value = duaEntities
            }
        }
    }

}