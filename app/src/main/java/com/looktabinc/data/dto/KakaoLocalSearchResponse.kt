package com.looktabinc.data.dto

import org.jetbrains.kotlin.com.google.gson.annotations.SerializedName

data class KakaoLocalSearchResponse(
    @SerializedName("meta")
    val meta: String,
    @SerializedName("documents")
    val documents: List<String>
)