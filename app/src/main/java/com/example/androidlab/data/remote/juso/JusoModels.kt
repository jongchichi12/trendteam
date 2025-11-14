package com.example.androidlab.data.remote.juso

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// API 응답 JSON 스키마(필요 필드만)
data class JusoResponseDto(
    val results: ResultsDto?
)

data class ResultsDto(
    val common: CommonDto?,
    val juso: List<JusoItemDto>?
)

data class CommonDto(
    val errorCode: String?,
    val errorMessage: String?,
    val totalCount: String?
)

data class JusoItemDto(
    val roadAddr: String?,   // 도로명 주소
    val jibunAddr: String?,  // 지번 주소
    val zipNo: String?,      // 우편번호
    val siNm: String?,       // 시/도
    val sggNm: String?,      // 시/군/구
    val emdNm: String?       // 읍/면/동
)

// 화면에서 쓰기 쉬운 도메인 모델
@Parcelize
data class SimpleAddr(
    val roadAddr: String,
    val jibunAddr: String,
    val zipNo: String
) : Parcelable

fun JusoItemDto.toDomain(): SimpleAddr = SimpleAddr(
    roadAddr = roadAddr.orEmpty(),
    jibunAddr = jibunAddr.orEmpty(),
    zipNo = zipNo.orEmpty()
)

