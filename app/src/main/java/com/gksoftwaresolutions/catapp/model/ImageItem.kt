package com.gksoftwaresolutions.catapp.model

data class ImageItem(
    val breeds: List<BreedItem>,
    val categories: List<Category>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)