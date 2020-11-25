package com.gksoftwaresolutions.catapp.model

data class VoteListItem(
    val country_code: String,
    val created_at: String,
    val id: Int,
    val image_id: String,
    val sub_id: String,
    val value: Int
)