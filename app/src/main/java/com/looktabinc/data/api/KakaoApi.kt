package com.looktabinc.data.api

import com.looktabinc.data.dto.KakaoLocalSearchResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface KakaoApi {

    @Headers("Authorization:KakaoAK 806a1f98f17b044322d80914ff5bcea8")
    @GET("search/keyword")
    fun getSearch(
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): Single<KakaoLocalSearchResponse>

}