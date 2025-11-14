package com.example.androidlab.domain.auth.entity

import com.example.androidlab.core.constants.Region

data class User(
    val id: String,
    val email: String,
    val displayName: String,
    // ⬇️ 추가 필드 (기본값으로 기존 코드 영향 최소화)
    val username: String = "",
    val phone: String = "",
    val region: Region = Region.SEOUL
)