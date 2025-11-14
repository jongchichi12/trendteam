package com.example.androidlab.data.repository

import com.example.androidlab.BuildConfig
import com.example.androidlab.data.remote.juso.*
import javax.inject.Inject

class JusoRepository @Inject constructor(
    private val api: JusoApi
) {
    suspend fun search(keyword: String, page: Int = 1, size: Int = 20): List<SimpleAddr> {
        require(keyword.isNotBlank()) { "검색어를 입력하세요." }

        val dto = api.search(
            key = BuildConfig.JUSO_KEY,
            keyword = keyword,
            page = page,
            size = size
        )

        val errorCode = dto.results?.common?.errorCode
        if (!errorCode.isNullOrBlank() && errorCode != "0") {
            // 공식 스펙: errorCode 0 이 정상
            val msg = dto.results?.common?.errorMessage ?: "주소 검색 실패($errorCode)"
            throw IllegalStateException(msg)
        }

        return dto.results?.juso.orEmpty().map { it.toDomain() }
    }
}



