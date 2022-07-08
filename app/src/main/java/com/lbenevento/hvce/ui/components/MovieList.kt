package com.lbenevento.hvce.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lbenevento.hvce.domain.Movie
import com.lbenevento.hvce.ui.theme.HvceTheme


@Composable
fun MovieList(
    movies: List<Movie>?,
    onDetails: (movie: Movie) -> Unit,
    onLoadNextPage: () -> Unit,
    modifier: Modifier = Modifier
) {

    if (movies.isNullOrEmpty()) {
        Column(
            modifier = modifier.padding(50.dp)
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(25.dp)
            )
            Text(
                text = "If this takes too long check your internet connection.",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    } else {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            items(movies.size + 1) { index ->
                if (index == movies.size) {
                    Button(
                        shape = RoundedCornerShape(100),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ),
                        onClick = onLoadNextPage
                    ) {
                        Text(
                            text = "Load next page"
                        )
                    }
                } else {
                    Movie(movie = movies[index], onClick = { onDetails(movies[index]) })
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieListPreview() {
    HvceTheme {
        MovieList(
            movies = null,
            onDetails = {},
            onLoadNextPage = {}
        )
    }
}