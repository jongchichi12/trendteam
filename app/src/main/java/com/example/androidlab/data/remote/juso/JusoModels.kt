package com.example.androidlab.data.remote.juso

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Juso API JSON 스키마
 * 예시:
 * {
 *   "results": {
 *     "common": { "errorCode": "...", "errorMessage": "...", "totalCount": "..." },
 *     "juso": [
 *       {"roadAddr":"...", "jibunAddr":"...", "zipNo":"...", "siNm":"...", "sggNm":"...", "emdNm":"..."}
 *     ]
 *   }
 * }
 */

data class JusoResponseDto(
    @SerializedName("results")
    val results: ResultsDto = ResultsDto()
)

data class ResultsDto(
    @SerializedName("common")
    val common: CommonDto = CommonDto(),

    @SerializedName("juso")
    val juso: List<JusoItemDto> = emptyList()
)

data class CommonDto(
    @SerializedName("errorCode")    val errorCode: String = "",
    @SerializedName("errorMessage") val errorMessage: String = "",
    @SerializedName("totalCount")   val totalCount: String = ""
)

data class JusoItemDto(
    @SerializedName("roadAddr")  val roadAddr: String = "",   // 도로명 주소
    @SerializedName("jibunAddr") val jibunAddr: String = "",  // 지번 주소
    @SerializedName("zipNo")     val zipNo: String = "",      // 우편번호
    @SerializedName("siNm")      val siNm: String = "",       // 시/도
    @SerializedName("sggNm")     val sggNm: String = "",      // 시/군/구
    @SerializedName("emdNm")     val emdNm: String = ""       // 읍/면/동
)

/** 화면에서 쓰는 심플 모델 */
@Parcelize
data class SimpleAddr(
    val roadAddr: String,
    val jibunAddr: String,
    val zipNo: String
) : Parcelable

fun JusoItemDto.toDomain(): SimpleAddr = SimpleAddr(
    roadAddr = roadAddr,
    jibunAddr = jibunAddr,
    zipNo = zipNo
)

/** 편의 확장함수: 통째로 바로 리스트로 꺼내고 싶을 때 */
fun JusoResponseDto.toSimpleList(): List<SimpleAddr> =
    results.juso.map { it.toDomain() }