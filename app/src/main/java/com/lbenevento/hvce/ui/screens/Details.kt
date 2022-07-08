package com.lbenevento.hvce.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.lbenevento.hvce.R
import com.lbenevento.hvce.domain.Movie
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Details(movie: Movie, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original/" + movie.posterPath,
                contentDescription = stringResource(R.string.image_des_movie_poster),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp)),
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(10.dp)
                    .size(60.dp)
                    .background(Color.Black.copy(alpha = .5f), CircleShape)
                    .border(1.dp, Color.White, CircleShape)
                    .padding(10.dp)
            ) {
                Text(
                    text = movie.voteAverage.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Card {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = movie.title ?: "",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    lineHeight = 40.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = LocalDate
                        .parse(movie.releaseDate)
                        .format(DateTimeFormatter.ofPattern("LLL dd, uuuu")),
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp,
                    modifier = Modifier.alpha(.5f)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = movie.overview ?: "",
                )
            }
        }
    }
}