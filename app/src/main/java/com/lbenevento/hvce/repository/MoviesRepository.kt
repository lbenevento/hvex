package com.lbenevento.hvce.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lbenevento.hvce.domain.Movie
import com.lbenevento.hvce.domain.TheMovieDbResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbApi {
    @GET("3/movie/popular")
    fun getMovies(
        @Query("page") pageNumber: Int,
        @Query("api_key") apiKey: String
    ): Call<TheMovieDbResponse>
}

class MoviesRepository {

    private val _movies: MutableLiveData<List<Pair<Int, List<Movie>>>> = MutableLiveData()
    val movies: LiveData<List<Movie>>
        get() = Transformations.map(_movies) { pages ->
            val movies = mutableListOf<Movie>()

            for (page in pages) {
                if (page.second.isNotEmpty()) {
                    movies.addAll(page.second)
                }
            }

            return@map movies.toList()
        }

    private val _hasErrors: MutableLiveData<Boolean> = MutableLiveData()
    val hasErrors: LiveData<Boolean>
        get() = _hasErrors

    // This could be done a bit better probably, but for the purpose of this exercise it should be enough.
    val currentPage: Int
        get() {
            var currentPage = 1
            _movies.value?.forEach {
                if (it.first > currentPage) currentPage = it.first
            }
            return currentPage
        }

    fun refresh() {
        _hasErrors.value = false
        _movies.value = listOf()
        loadPage(1)
    }

    fun loadNextPage() {
        loadPage(currentPage + 1)
    }

    private fun loadPage(pageNumber: Int = 1) {

        CoroutineScope(Dispatchers.IO).launch {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val theMovieDbApi = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(TheMovieDbApi::class.java)

            theMovieDbApi.getMovies(
                pageNumber,
                "3d2fe57eede3598225dd82e80e3c4040"
            ).enqueue(

                object : Callback<TheMovieDbResponse> {
                    override fun onResponse(
                        call: Call<TheMovieDbResponse>,
                        response: Response<TheMovieDbResponse>
                    ) {
                        val oldPages = _movies.value ?: listOf()
                        _movies.value = oldPages + Pair(
                            response.body()?.pageNumber ?: -1,
                            response.body()?.results ?: listOf()
                        )
                        _hasErrors.value = false
                    }

                    override fun onFailure(call: Call<TheMovieDbResponse>, t: Throwable) {
                        _hasErrors.value = true
                        Log.e("API error", t.toString())
                    }
                }

            )
        }

    }

}