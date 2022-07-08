package com.lbenevento.hvce.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class TheMovieDbResponse(
    @Json(name = "page") val pageNumber: Int,
    @Json(name = "results") val results: List<Movie>
)