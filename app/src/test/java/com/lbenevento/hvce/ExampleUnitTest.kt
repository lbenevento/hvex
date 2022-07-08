package com.lbenevento.hvce

import android.util.Log
import com.lbenevento.hvce.repository.TheMovieDbApi
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun tmdb_interface() {
        val TheMovieDbApiInterface = TheMovieDbApi.create()

        Log.i("TMDB Interface", TheMovieDbApiInterface.getMovies().toString())
    }
}