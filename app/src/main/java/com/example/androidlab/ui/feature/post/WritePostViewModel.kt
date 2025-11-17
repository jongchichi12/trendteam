package com.example.androidlab.ui.feature.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlab.domain.post.usecase.CreatePost
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class WritePostUiState(
    val content: String = "",
    val loading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class WritePostViewModel @Inject constructor(
    private val createPost: CreatePost
) : ViewModel() {

    private val _state = MutableStateFlow(WritePostUiState())
    val state: StateFlow<WritePostUiState> = _state

    fun onContentChange(v: String) {
        _state.value = _state.value.copy(content = v, error = null)
    }

    fun submit(onSuccess: () -> Unit) {
        val text = state.value.content.trim()
        if (text.isEmpty()) {
            _state.value = _state.value.copy(error = "내용을 입력하세요.")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                // TODO: 로그인 유저 정보와 연동. 없을 경우 게스트 기본값 사용.
                createPost(authorId = "guest", authorName = "게스트", content = text)
                _state.value = WritePostUiState() // 입력 초기화
                onSuccess()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "게시 실패"
                )
            }
        }
    }
}
