package com.looktabinc.feature.model

data class ReviewHistory(
    val id:Int,
    val image:List<String>,
    val category:String,
    val storeName:String,
    val content:String,
    val date:String
)