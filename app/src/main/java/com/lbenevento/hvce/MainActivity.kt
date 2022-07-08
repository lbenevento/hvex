package com.lbenevento.hvce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lbenevento.hvce.domain.Movie
import com.lbenevento.hvce.domain.MovieType
import com.lbenevento.hvce.repository.MoviesRepository
import com.lbenevento.hvce.ui.screens.Details
import com.lbenevento.hvce.ui.screens.Home
import com.lbenevento.hvce.ui.theme.HvceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = MoviesRepository()
        repo.refresh()

        setContent {
            HvceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {

                    val navController = rememberNavController()

                    val movies by repo.movies.observeAsState()
                    val hasErrors by repo.hasErrors.observeAsState(false)

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            Home(
                                movies = movies,
                                hasErrors = hasErrors,
                                onRefresh = { repo.refresh() },
                                onDetails = { movie ->
                                    navController.navigate(
                                        route = "details/${movie.toString().replace("/", "")}"
                                    )
                                },
                                onLoadNextPage = {
                                    repo.loadNextPage()
                                }
                            )
                        }
                        composable(
                            "details/{movie}",
                            arguments = listOf(navArgument("movie") { type = MovieType() })
                        ) { backStackEntry ->

                            val movie = backStackEntry.arguments?.getParcelable("movie") ?: Movie()

                            Details(movie)
                        }
                    }
                }
            }
        }
    }
}