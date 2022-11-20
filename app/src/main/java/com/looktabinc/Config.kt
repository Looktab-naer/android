package com.looktabinc

object Config {
    const val LOAD_URL = "load_url"
    const val Base_URL =  "https://stately-puffpuff-8836be.netlify.app/all.html"

    enum class ArType(val type: String)
    { CAFE("cafe"), RESTAURANT("restaurant"), BAR("bar"), SHOPPING("shopping")
    , LIFE("life"), FITNESS("fitness") }

}
