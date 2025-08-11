package edu.ariyadi.jabarmengaji.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ariyadi.jabarmengaji.data.model.Surah
import edu.ariyadi.jabarmengaji.data.repository.QuranRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuranViewModel @Inject constructor(
    private val repository: QuranRepository
) : ViewModel() {

    private val _surahList = MutableStateFlow<List<Surah>>(emptyList())
    val surahList: StateFlow<List<Surah>> = _surahList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchSurahList()
    }

    private fun fetchSurahList() {
        viewModelScope.launch {
            _isLoading.value = true

            val surahCount = repository.getSurahCount()
            if (surahCount == 0) {
                repository.refreshSurahList()
            }

            repository.getAllSurah().collectLatest { list ->
                _surahList.value = list
                _isLoading.value = false
            }
        }
    }

    fun searchSurah(query: String) {
        viewModelScope.launch {
            repository.searchSurah(query).collectLatest { list ->
                _surahList.value = list
            }
        }
    }

}