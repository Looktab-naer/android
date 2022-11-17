package com.looktabinc.data.injection

import com.looktabinc.data.api.KakaoApiProvider
import com.looktabinc.data.source.KaKaoRepository

object KakaoInjection {
    fun provideRepository(): KaKaoRepository =
        KaKaoRepository(KakaoApiProvider.provideApi())
}