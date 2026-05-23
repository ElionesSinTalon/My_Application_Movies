package mendoza.ruiz.myapplicationmovies.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import mendoza.ruiz.myapplicationmovies.model.PeliculaDetalle

@Composable
fun BannerCarousel(
    movies: List<PeliculaDetalle>,
    onPeliculaClick: (PeliculaDetalle) -> Unit
) {
    if (movies.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { movies.size })

    // Auto-scroll logic every 5 seconds
    LaunchedEffect(pagerState) {
        while (true) {
            delay(5000)
            if (movies.isNotEmpty()) {
                val nextPage = (pagerState.currentPage + 1) % movies.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp) // Height for the banner
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val movie = movies[page]
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onPeliculaClick(movie) }
            ) {
                // Background gradient from movie colors
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(movie.cardColors))
                )

                // Poster Image
                if (movie.posterUrl.isNotBlank()) {
                    AsyncImage(
                        model = movie.posterUrl,
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Bottom Gradient Overlay for depth
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xFF0D0D12))
                            )
                        )
                )
            }
        }

        // Pager Indicators
        Row(
            Modifier
                .height(40.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(movies.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color(0xFF00E5FF) else Color.White.copy(alpha = 0.5f)
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(color)
                        .size(width = if (pagerState.currentPage == iteration) 16.dp else 8.dp, height = 4.dp)
                )
            }
        }
    }
}
