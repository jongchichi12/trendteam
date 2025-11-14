package com.example.androidlab.ui.feature.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlab.data.remote.juso.SimpleAddr
import com.example.androidlab.data.repository.JusoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddressUiState(
    val query: String = "",
    val loading: Boolean = false,
    val items: List<SimpleAddr> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class AddressSearchViewModel @Inject constructor(
    private val repo: JusoRepository
): ViewModel() {
    private val _state = MutableStateFlow(AddressUiState())
    val state: StateFlow<AddressUiState> = _state

    private var typingJob: Job? = null

    fun onQueryChange(q: String) {
        _state.value = _state.value.copy(query = q, error = null)
        // 디바운스: 350ms 동안 추가 입력 없으면 검색
        typingJob?.cancel()
        typingJob = viewModelScope.launch {
            delay(350)
            search()
        }
    }

    fun search() {
        val q = _state.value.query.trim()
        if (q.isBlank()) {
            _state.value = _state.value.copy(items = emptyList(), error = null)
            return
        }

        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(loading = true, error = null)
                val list = repo.search(q, page = 1, size = 20)
                _state.value = _state.value.copy(loading = false, items = list)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "검색 실패"
                )
            }
        }
    }
}
