package com.lbenevento.hvce.domain

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.parcelize.Parcelize
import java.lang.IllegalStateException


@JsonClass(generateAdapter = true)
@Parcelize
data class Movie(
    @Json(name = "poster_path") val posterPath: String? = null,
    @Json(name = "adult") val adult: Boolean? = null,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "release_date") val releaseDate: String? = null,
    @Json(name = "genre_ids") val genreIds: Set<Int>? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "original_title") val originalTitle: String? = null,
    @Json(name = "original_language") val originalLanguage: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "backdrop_path") val backdropPath: String? = null,
    @Json(name = "popularity") val popularity: Float? = null,
    @Json(name = "vote_count") val voteCount: Int? = null,
    @Json(name = "video") val video: Boolean? = null,
    @Json(name = "vote_average") val voteAverage: Float? = null
) : Parcelable {

    override fun toString(): String {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(Movie::class.java)
        return adapter.toJson(this)
    }

}

class MovieType : NavType<Movie>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Movie? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Movie {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(Movie::class.java)

        return adapter.fromJson(value) ?: throw IllegalStateException("Movie should not be null.")
    }

    override fun put(bundle: Bundle, key: String, value: Movie) {
        bundle.putParcelable(key, value)
    }

}