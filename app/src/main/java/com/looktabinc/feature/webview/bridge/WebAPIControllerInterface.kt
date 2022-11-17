package com.looktabinc.feature.webview.bridge

interface WebAPIControllerInterface {
    fun onEventFunction(id: String)
    fun onEventWriteReview(
        functionName: String,
        orderDetailSeq: Int?,
        seq: Int?,
        orderNo: Int?,
        thumImgDir: Int?,
        productName: Int?,
        productOptionEa: Int?,
        productOptionName: Int?,
        authdate: Int?
    )
    fun close()
}