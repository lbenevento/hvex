package com.lbenevento.hvce.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.lbenevento.hvce.R
import com.lbenevento.hvce.domain.Movie
import com.lbenevento.hvce.ui.components.MovieList

@Composable
fun Home(
    movies: List<Movie>?,
    hasErrors: Boolean,
    onRefresh: () -> Unit,
    onDetails: (movie: Movie) -> Unit,
    onLoadNextPage: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.the_movie_db),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        Text(
            text = stringResource(R.string.popular)
        )
        Spacer(modifier = Modifier.height(15.dp))
        if (hasErrors) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(50.dp).clickable { onRefresh() },
            ) {
                Text(
                    text = stringResource(R.string.loading_error),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        } else {
            MovieList(
                movies = movies,
                onDetails = onDetails,
                onLoadNextPage = onLoadNextPage
            )
        }
    }
}