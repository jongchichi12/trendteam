package com.example.androidlab.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlab.domain.post.Post
import com.example.androidlab.domain.post.usecase.ObservePosts
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

data class HomeUiState(
    val posts: List<Post> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    observePosts: ObservePosts
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState(loading = true))
    val state: StateFlow<HomeUiState> = _state

    init {
        viewModelScope.launch {
            observePosts()
                .onStart { _state.value = _state.value.copy(loading = true, error = null) }
                .catch { e -> _state.value = _state.value.copy(loading = false, error = e.message) }
                .collect { posts ->
                    _state.value = HomeUiState(
                        posts = posts,
                        loading = false,
                        error = null
                    )
                }
        }
    }
}
