package com.looktabinc.feature.wallet

data class NftResponse(
    val token_id: String,
    val owner_id: String,
    val metadata: Metadata
)

data class Metadata(
    val title: String,
    val description: String,
    val media: String,
    val issued_at: String,
)