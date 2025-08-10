package edu.ariyadi.jabarmengaji.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ariyadi.jabarmengaji.data.model.DuaEntity
import edu.ariyadi.jabarmengaji.data.repository.DuaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailDuaViewModel @Inject constructor(
    private val repository: DuaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _duaDetail = MutableStateFlow<DuaEntity?>(null)
    val duaDetail: StateFlow<DuaEntity?> = _duaDetail.asStateFlow()

    private val duaId: Int = savedStateHandle.get<Int>("duaId") ?: -1

    init {
        if (duaId != -1) {
            fetchDuaDetail(duaId)
        }
    }

    private fun fetchDuaDetail(id: Int) {
        viewModelScope.launch {
            _duaDetail.value = repository.getDuaById(id)
        }
    }

}