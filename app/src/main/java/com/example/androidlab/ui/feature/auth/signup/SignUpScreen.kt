// app/src/main/java/com/example/androidlab/ui/feature/auth/signup/SignUpScreen.kt
package com.example.androidlab.ui.feature.auth.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.androidlab.data.remote.juso.SimpleAddr
import com.example.androidlab.ui.components.PasswordField

/**
 * onClickAddressSearch:
 *  - 실제 주소검색(웹뷰/다음우편번호 등) 열 때 쓰는 선택형 콜백.
 *  - 전달하지 않으면(=null) 버튼은 동작하지 않음. 나중에 붙이면 됨.
 */
@Composable
fun SignUpScreen(
    onBack: () -> Unit,
    onSignUpSuccess: () -> Unit,
    vm: SignUpViewModel = hiltViewModel(),
    onClickAddressSearch: (() -> Unit)? = null,
    navController: NavController? = null
) {
    val state by vm.state.collectAsState()
    
    // 주소 검색 결과 수신
    val picked by navController?.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<com.example.androidlab.data.remote.juso.SimpleAddr?>("picked_addr", null)
        ?.collectAsState() ?: remember { mutableStateOf(null) }
    
    LaunchedEffect(picked) {
        picked?.let { addr ->
            // 주소 입력 필드 갱신
            vm.setAddress(addr.roadAddr)
            // 선택 완료 후 핸들 비우기
            navController?.currentBackStackEntry?.savedStateHandle?.set("picked_addr", null)
        }
    }

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("회원가입", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(20.dp))

            // 이름(닉네임)
            OutlinedTextField(
                value = state.displayName,
                onValueChange = vm::updateDisplayName,
                label = { Text("이름(닉네임)") },
                singleLine = true,
                isError = state.nameError != null,
                supportingText = { state.nameError?.let { Text(it) } },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            // 아이디
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = state.username,
                onValueChange = vm::updateUsername,
                label = { Text("아이디") },
                singleLine = true,
                isError = state.usernameError != null,
                supportingText = { state.usernameError?.let { Text(it) } },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            // 비밀번호 (아이디 바로 아래)
            Spacer(Modifier.height(12.dp))
            PasswordField(
                value = state.password,
                onValueChange = vm::updatePassword,
                modifier = Modifier.fillMaxWidth()
            )

            // 전화번호
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = state.phone,
                onValueChange = vm::updatePhone,
                label = { Text("전화번호") },
                singleLine = true,
                isError = state.phoneError != null,
                supportingText = { state.phoneError?.let { Text(it) } },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // 🔎 주소찾기 + 상세주소 한 줄
            Spacer(Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        // 실제 주소검색 화면을 띄우는 곳.
                        // 나중에 WebView 붙이면 여기서 onClickAddressSearch()만 호출하면 됨.
                        onClickAddressSearch?.invoke()
                    },
                    enabled = !state.loading,
                    modifier = Modifier
                        .width(112.dp)
                        .height(52.dp),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Text("주소찾기")
                }

                Spacer(Modifier.width(8.dp))

                OutlinedTextField(
                    value = state.addressDetail,
                    onValueChange = vm::updateAddressDetail,
                    label = { Text("상세주소") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    modifier = Modifier.weight(1f)
                )
            }

            // 주소: 전체 너비, 읽기전용 (버튼/상세주소 아래)
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = state.address,
                onValueChange = {}, // 주소찾기는 버튼으로만 변경
                readOnly = true,
                label = { Text("주소") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // 이메일
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = state.email,
                onValueChange = vm::updateEmail,
                label = { Text("이메일") },
                singleLine = true,
                isError = state.emailError != null,
                supportingText = { state.emailError?.let { Text(it) } },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // 가입 버튼
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = { vm.signUp(onSuccess = onSignUpSuccess) },
                enabled = !state.loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(if (state.loading) "가입 중..." else "가입하기")
            }

            // 뒤로가기
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onBack, enabled = !state.loading) {
                Text("뒤로가기")
            }

            // 공통 에러
            if (state.error != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}
