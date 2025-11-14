@file:OptIn(ExperimentalMaterial3Api::class) // ← TopAppBar 실험 API 옵트인

package com.example.androidlab.ui.feature.address

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidlab.data.remote.juso.SimpleAddr

@Composable
fun AddressSearchScreen(
    onBack: () -> Unit,
    onSelect: (SimpleAddr) -> Unit,
    vm: AddressSearchViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("주소 찾기") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack, // ← 아이콘 수정
                            contentDescription = "뒤로가기"
                        )
                    }
                }
            )
        }
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = state.query,
                onValueChange = vm::onQueryChange,
                label = { Text("도로명/지번/건물명") },
                singleLine = true,
                keyboardOptions = KeyboardOptions( // ← 깔끔하게 사용
                    imeAction = ImeAction.Search
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = vm::search,
                enabled = !state.loading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.loading) "검색 중..." else "검색")
            }

            state.error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(state.items) { item ->
                    AddressRow(item) { onSelect(item) }
                }
            }
        }
    }
}

@Composable
private fun AddressRow(
    item: SimpleAddr,
    onClick: () -> Unit
) {
    Surface(
        tonalElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(item.roadAddr, style = MaterialTheme.typography.bodyLarge)

            if (item.jibunAddr.isNotBlank()) {
                Spacer(Modifier.height(2.dp))
                Text("지번: ${item.jibunAddr}", style = MaterialTheme.typography.bodyMedium)
            }
            if (item.zipNo.isNotBlank()) {
                Spacer(Modifier.height(2.dp))
                Text("우편번호: ${item.zipNo}", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

