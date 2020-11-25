package com.gksoftwaresolutions.catapp.util

import java.text.SimpleDateFormat
import java.util.*

object Common {
    const val BASE_URL = "https://api.thecatapi.com/"
    const val PAGE = 1
    const val POST_PER_PAGE = 10
    const val SIZE_IMAGE = "med"

    val PARAMS = mutableMapOf<String, String>()

    val SDF = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
}