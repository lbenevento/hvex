package com.lbenevento.hvce.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.lbenevento.hvce.R
import com.lbenevento.hvce.domain.Movie
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Movie(movie: Movie, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surface,
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Box() {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500" + movie.posterPath,
                    contentDescription = stringResource(R.string.image_des_movie_poster),
                    modifier = Modifier.height(150.dp),
                )
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Text(
                        text = movie.title ?: "",
                        fontWeight = FontWeight.Bold,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = LocalDate
                            .parse(movie.releaseDate)
                            .format(DateTimeFormatter.ofPattern("LLL dd, uuuu")),
                        fontStyle = FontStyle.Italic,
                        fontSize = 10.sp,
                        modifier = Modifier.alpha(.5f)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = movie.overview ?: "",
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            if (movie.adult == true) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(60.dp)
                        .background(Color.Black.copy(alpha = .5f), CircleShape)
                        .border(1.dp, Color.Red, CircleShape)
                        .padding(10.dp)
                ) {
                    Text(
                        text = movie.voteAverage.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Red
                    )
                }
            }

        }
    }
}