package com.looktabinc.data.source

import com.looktabinc.data.dto.KakaoLocalSearchResponse
import io.reactivex.rxjava3.core.Single

interface KaKaoSource {
    fun search(query: String, page: Int?, size: Int?): Single<KakaoLocalSearchResponse>
}