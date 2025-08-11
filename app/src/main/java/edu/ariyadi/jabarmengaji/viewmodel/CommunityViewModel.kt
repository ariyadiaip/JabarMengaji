package edu.ariyadi.jabarmengaji.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ariyadi.jabarmengaji.data.model.Community
import edu.ariyadi.jabarmengaji.data.repository.CommunityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: CommunityRepository
) : ViewModel() {

    private val _communityList = MutableStateFlow<List<Community>>(emptyList())
    val communityList: StateFlow<List<Community>> = _communityList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchCommunities()
    }

    private fun fetchCommunities() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllCommunities().collectLatest { list ->
                _communityList.value = list
                _isLoading.value = false
            }
        }
    }

    fun searchCommunity(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                fetchCommunities()
            } else {
                repository.searchCommunities(query).collectLatest { list ->
                    _communityList.value = list
                }
            }
        }
    }

}