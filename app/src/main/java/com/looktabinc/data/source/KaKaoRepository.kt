package com.looktabinc.data.source

import com.looktabinc.data.api.KakaoApi
import com.looktabinc.data.dto.KakaoLocalSearchResponse
import io.reactivex.rxjava3.core.Single

class KaKaoRepository(
    private val kakaoApi: KakaoApi
) {
    fun search(query: String, page: Int?, size: Int?): Single<KakaoLocalSearchResponse> {
        return kakaoApi.getSearch(
            query, page, size
        )
    }


}