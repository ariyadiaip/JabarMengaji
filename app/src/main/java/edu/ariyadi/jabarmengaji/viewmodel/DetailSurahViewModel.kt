package edu.ariyadi.jabarmengaji.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ariyadi.jabarmengaji.data.model.DetailSurah
import edu.ariyadi.jabarmengaji.data.repository.QuranRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailSurahViewModel @Inject constructor(
    private val repository: QuranRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _surahDetail = MutableStateFlow<DetailSurah?>(null)
    val surahDetail: StateFlow<DetailSurah?> = _surahDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val surahNumber: Int = savedStateHandle.get<Int>("surahNumber") ?: -1

    init {
        if (surahNumber != -1) {
            fetchSurahDetail(surahNumber)
        }
    }

    private fun fetchSurahDetail(number: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val detail = repository.getDetailSurah(number)
            _surahDetail.value = detail
            _isLoading.value = false
        }
    }

}