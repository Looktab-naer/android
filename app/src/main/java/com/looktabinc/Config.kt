package com.looktabinc

object Config {
    const val LOAD_URL = "load_url"
    const val IS_FIRST_ACCESS = "isFirstAccess"

    //로그인 최초 프래그먼트
    const val LOGIN_COMPLETE = "loginComplete"

    //콜백
    object CodeWebToHome {
        var requestCode = 100
        var resultCode = 1
    }

    const val CALL_BACK_WEB_RELOAD = "callBackWebReload"
    const val CALL_BACK_MY_PAGE_RELOAD = "callBackMyPageReload"
    const val CALL_BACK_HOME = "callBackHome"

    //문의하기 prodseq
    const val PRODUCT_SEQ = "product"

    //PrdListActivity 화면 init
    const val PROD_BRAND_SEQ = "brandSeq"
    const val PROD_LIST_CATEGORY_SEQ = "categorySeq"
    const val PROD_LIST_TYPE = "prodListType"
    enum class ProdListType(val type: String) { BEST("BEST"), SUB("SUB"), SEARCH("SEARCH"), BRAND("BRAND") }
    enum class ProdListFilter(val type: String) { CATEGORY("카테고리"), AGE("연령대"), STYLE("스타일") }

    enum class SearchOrder(val sortNum: Int, val sortName: String) {
        NEW(6, "최신상품"),
        BEST(5, "판매량"),
        POPULARITY(1, "인기순"),
        LOW_PRICE(3, "저가순"),
        HIGH_PRICE(4, "고가순"),
        REVIEW(2, "리뷰많은순"),
    }

    fun searchOrderName(int: Int): String {
        return when {
            SearchOrder.NEW.sortNum == int -> SearchOrder.NEW.sortName
            SearchOrder.BEST.sortNum == int -> SearchOrder.BEST.sortName
            SearchOrder.POPULARITY.sortNum == int -> SearchOrder.POPULARITY.sortName
            SearchOrder.LOW_PRICE.sortNum == int -> SearchOrder.LOW_PRICE.sortName
            SearchOrder.HIGH_PRICE.sortNum == int -> SearchOrder.HIGH_PRICE.sortName
            SearchOrder.REVIEW.sortNum == int -> SearchOrder.REVIEW.sortName
            else -> ""
        }
    }

}
