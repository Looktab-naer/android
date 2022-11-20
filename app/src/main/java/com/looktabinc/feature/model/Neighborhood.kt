package com.looktabinc.feature.model

import android.net.Uri

data class Neighborhood(
    val id: Int,
    val image: String?,
    val category: String,
    val storeName: String,
    val content: String,
    val status: Int,
    val distance: String,
    val date: String
)