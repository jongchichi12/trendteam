// app/src/main/java/com/example/androidlab/ui/feature/auth/signup/SignUpViewModel.kt
package com.example.androidlab.ui.feature.auth.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlab.core.AppResult
import com.example.androidlab.domain.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val displayName: String = "",

    // 기존: region 제거, 대신 주소 필드 2개 추가
    val username: String = "",
    val phone: String = "",
    val address: String = "",        // 주소찾기 결과 (도로명 등)
    val addressDetail: String = "",  // 상세주소(동/호/호수)

    val loading: Boolean = false,
    val error: String? = null,

    // 개별 에러
    val nameError: String? = null,
    val emailError: String? = null,
    val usernameError: String? = null,
    val phoneError: String? = null
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpUiState())
    val state: StateFlow<SignUpUiState> = _state

    // --- 업데이트 메서드 ---

    fun updateEmail(v: String) {
        _state.value = _state.value.copy(
            email = v,
            emailError = validateEmail(v),
            error = null
        )
    }

    fun updatePassword(v: String) {
        _state.value = _state.value.copy(password = v, error = null)
    }

    fun updateDisplayName(v: String) {
        _state.value = _state.value.copy(
            displayName = v,
            nameError = validateName(v),
            error = null
        )
    }

    fun updateUsername(v: String) {
        _state.value = _state.value.copy(
            username = v,
            usernameError = validateUsername(v),
            error = null
        )
    }

    fun updatePhone(v: String) {
        _state.value = _state.value.copy(
            phone = v,
            phoneError = validatePhone(v),
            error = null
        )
    }

    /** 주소찾기 WebView 등에서 선택된 주소를 넣어줄 때 호출 */
    fun setAddress(addr: String) {
        _state.value = _state.value.copy(address = addr, error = null)
    }

    fun updateAddressDetail(v: String) {
        _state.value = _state.value.copy(addressDetail = v, error = null)
    }

    // --- 가입 실행 ---

    fun signUp(onSuccess: () -> Unit) {
        val s = _state.value

        val nameErr = validateName(s.displayName)
        val emailErr = validateEmail(s.email)
        val userErr = validateUsername(s.username)
        val phoneErr = validatePhone(s.phone)
        val pwErr = if (s.password.isBlank()) "비밀번호를 입력하세요." else null

        val firstError = nameErr ?: emailErr ?: userErr ?: phoneErr ?: pwErr
        if (firstError != null) {
            _state.value = s.copy(
                nameError = nameErr,
                emailError = emailErr,
                usernameError = userErr,
                phoneError = phoneErr,
                error = firstError
            )
            return
        }

        viewModelScope.launch {
            _state.value = s.copy(loading = true, error = null)

            // 현재는 주소 저장을 강제하지 않음. (Room/Firebase 확장 시 반영)
            when (
                val res = repo.signUp(
                    email = s.email,
                    password = s.password,
                    displayName = s.displayName,
                    username = s.username,
                    phone = s.phone
                    // region 파라미터는 인터페이스 디폴트값이 있으므로 생략
                )
            ) {
                is AppResult.Success -> onSuccess()
                is AppResult.Error ->
                    _state.value = _state.value.copy(loading = false, error = res.message)
            }
        }
    }

    // --- 검증 ---

    // 한글/영문/숫자/공백/.-_ 2~20자
    private fun validateName(name: String): String? {
        if (name.isBlank()) return "이름(닉네임)을 입력하세요."
        val ok = Regex("^[가-힣a-zA-Z0-9 ._\\-]{2,20}$").matches(name)
        return if (ok) null else "한글/영문/숫자 2~20자 (특수문자 .-_ 만 허용)"
    }

    private fun validateEmail(email: String): String? {
        if (email.isBlank()) return "이메일을 입력하세요."
        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) null
        else "이메일 형식이 올바르지 않습니다."
    }

    // 영문/숫자/밑줄 4~20자
    private fun validateUsername(username: String): String? {
        if (username.isBlank()) return "아이디를 입력하세요."
        val ok = Regex("^[A-Za-z0-9_]{4,20}$").matches(username)
        return if (ok) null else "영문/숫자/밑줄 4~20자"
    }

    // 숫자 10~11자리
    private fun validatePhone(phone: String): String? {
        if (phone.isBlank()) return "전화번호를 입력하세요."
        val ok = Regex("^\\d{10,11}$").matches(phone)
        return if (ok) null else "숫자 10~11자리"
    }
}