package com.example.androidlab.data.remote.juso

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 행안부 실시간 주소검색 API
 * baseUrl = https://business.juso.go.kr/
 * path    = addrlink/addrLinkApi.do
 *
 * 필수 파라미터:
 *  - confmKey(BuildConfig.JUSO_KEY)
 *  - keyword(검색어)
 *  - resultType=json
 * 옵션:
 *  - currentPage, countPerPage
 */
interface JusoApi {
    @GET("addrlink/addrLinkApi.do")
    suspend fun search(
        @Query("confmKey") key: String,
        @Query("keyword") keyword: String,
        @Query("resultType") resultType: String = "json",
        @Query("currentPage") page: Int = 1,
        @Query("countPerPage") size: Int = 20,
    ): JusoResponseDto
}

