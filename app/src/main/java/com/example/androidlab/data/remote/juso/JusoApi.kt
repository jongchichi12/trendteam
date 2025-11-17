package com.example.androidlab.data.remote.juso

// data/remote/juso/JusoApi.kt
import retrofit2.http.GET
import retrofit2.http.Query

interface JusoApi {
    @GET("addrlink/addrLinkApi.do")
    suspend fun search(
        @Query("confmKey") key: String,
        @Query("currentPage") page: Int = 1,
        @Query("countPerPage") size: Int = 30,
        @Query("keyword") keyword: String,
        @Query("resultType") resultType: String = "json"   // ★ JSON 강제
    ): JusoResponseDto
}