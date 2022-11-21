package com.looktabinc

object Config {
    const val LOAD_URL = "load_url"
    const val Base_URL =  "https://stately-puffpuff-8836be.netlify.app/all.html"

    enum class ArType(val type: String)
    { CAFE("CAFE"), RESTAURANT("RESTAURANT"), BAR("BAR"), SHOPPING("SHOPPING")
    , LIFE("life"), FITNESS("FITNESS") }

}
