package mendoza.ruiz.myapplicationmovies.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import mendoza.ruiz.myapplicationmovies.model.PeliculaDetalle
import mendoza.ruiz.myapplicationmovies.screens.components.BannerCarousel
import mendoza.ruiz.myapplicationmovies.screens.components.ErrorStateView
import mendoza.ruiz.myapplicationmovies.viewmodel.PremiereViewModel

// ── Colors
private val Background    = Color(0xFF0D0D12)
private val SurfaceCard   = Color(0xFF16161F)
private val AccentCyan    = Color(0xFF00E5FF) 
private val AccentPink    = Color(0xFFE040FB)
private val TextPrimary   = Color(0xFFFFFFFF)
private val TextSecondary = Color(0xFFB0B0C0)
private val DividerColor  = Color(0xFF2A2A3A)

@Composable
fun PremiereScreen(
    viewModel: PremiereViewModel,
    onPeliculaClick: (PeliculaDetalle) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(Background)) {
        if (uiState.isLoading && uiState.featured.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = AccentCyan)
        } else if (uiState.errorMessage != null && uiState.featured.isEmpty()) {
            ErrorStateView(
                message = uiState.errorMessage ?: "Error desconocido",
                onRetry = { viewModel.cargarPremiere() }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Top Banner Carousel con estrenos
                BannerCarousel(
                    movies = uiState.premierePosters,
                    onPeliculaClick = onPeliculaClick
                )

                if (uiState.featured.isNotEmpty()) {
                    FeaturedMoviePager(movies = uiState.featured, onPeliculaClick = onPeliculaClick)
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                ProximamenteSection(movies = uiState.upcoming, onPeliculaClick = onPeliculaClick)
                
                Spacer(modifier = Modifier.height(36.dp))
                EntradasAnticipadasSection()
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun FeaturedMoviePager(
    movies: List<PeliculaDetalle>,
    onPeliculaClick: (PeliculaDetalle) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { movies.size })

    LaunchedEffect(pagerState) {
        while (true) {
            delay(5000)
            if (movies.isNotEmpty()) {
                val next = (pagerState.currentPage + 1) % movies.size
                pagerState.animateScrollToPage(next)
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth().height(320.dp)) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            FeaturedMovieSlide(movie = movies[page], onPeliculaClick = onPeliculaClick)
        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(movies.size) { index ->
                val isActive = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .size(if (isActive) 20.dp else 6.dp, 6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            if
                                    (isActive) Brush.horizontalGradient(listOf(AccentCyan, AccentPink))
                            else
                                SolidColor(Color(0xFF444455))
                        )
                )
            }
        }
    }
}

@Composable
fun FeaturedMovieSlide(
    movie: PeliculaDetalle,
    onPeliculaClick: (PeliculaDetalle) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(movie.cardColors))) {
        if (movie.posterUrl.isNotBlank()) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Box(modifier = Modifier.fillMaxWidth().height(200.dp).align(Alignment.BottomCenter).background(Brush.verticalGradient(listOf(Color.Transparent, Background))))
        Column(modifier = Modifier.align(Alignment.BottomStart).padding(start = 20.dp, end = 20.dp, bottom = 36.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "ESTRENO", color = AccentCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.title,
                color = TextPrimary,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 40.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "⏱ ${movie.duration} min", color = TextSecondary, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "🎬 ${movie.category}", color = TextSecondary, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onPeliculaClick(movie) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(46.dp).widthIn(min = 160.dp).clip(RoundedCornerShape(10.dp)).background(Brush.horizontalGradient(listOf(AccentCyan, AccentPink)))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 20.dp)) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Background, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "DETALLES", color = Background, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 0.5.sp)
                }
            }
        }
    }
}

@Composable
fun ProximamenteSection(
    movies: List<PeliculaDetalle>,
    onPeliculaClick: (PeliculaDetalle) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "PRÓXIMAMENTE", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(movies) { _, movie ->
                ProximaMovieCard(
                    movie = movie,
                    onPeliculaClick = onPeliculaClick
                )
            }
        }
    }
}

@Composable
fun ProximaMovieCard(
    movie: PeliculaDetalle,
    onPeliculaClick: (PeliculaDetalle) -> Unit
) {
    Box(
        modifier = Modifier
            .width(160.dp)
            .height(260.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Brush.verticalGradient(movie.cardColors))
            .border(1.dp, DividerColor, RoundedCornerShape(14.dp))
            .clickable { onPeliculaClick(movie) }
    ) {
        if (movie.posterUrl.isNotBlank()) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xEE0D0D12))))
                .padding(start = 12.dp, end = 12.dp, top = 32.dp, bottom = 14.dp)
        ) {
            Column {
                Text(text = movie.title, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = movie.director, color = TextSecondary, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
fun EntradasAnticipadasSection() {
    val entradasAnticipadas = listOf(
        EntradaAnticipada("CYBERPUNK 2077", "VENTA PRIORITARIA", "ATMOS 01", "20/10", AccentPink, "RESERVAR AHORA", listOf(Color(0xFF1A1A2A), Color(0xFF252535)), listOf(Color(0xFF1A0800), Color(0xFF2A1000))),
        EntradaAnticipada("NEON NIGHTS",   "PRE-ESTRENO FAN",   "IMAX 04",  "11/10", AccentCyan,  "ADQUIRIR PASE",  listOf(Color(0xFF1A0030), Color(0xFF2A0050)), listOf(Color(0xFF1A0030), Color(0xFF0A0018))),
    )
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(text = "ENTRADAS ANTICIPADAS", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
        Spacer(modifier = Modifier.height(16.dp))
        entradasAnticipadas.forEach { entrada ->
            EntradaCard(entrada = entrada, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
        }
    }
}

@Composable
fun EntradaCard(entrada: EntradaAnticipada, modifier: Modifier = Modifier) {
    Column(modifier = modifier.clip(RoundedCornerShape(14.dp)).background(SurfaceCard).border(1.dp, DividerColor, RoundedCornerShape(14.dp)).padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(width = 52.dp, height = 68.dp).clip(RoundedCornerShape(8.dp)).background(Brush.verticalGradient(entrada.thumbColors)).border(1.dp, DividerColor, RoundedCornerShape(8.dp)))
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Box(modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(entrada.badgeColor.copy(alpha = 0.15f)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text(text = entrada.badge, color = entrada.badgeColor, fontSize = 8.sp, fontWeight = FontWeight.ExtraBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = entrada.title, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column {
                        Text(text = "SALA",  color = TextSecondary, fontSize = 8.sp)
                        Text(text = entrada.sala,  color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Column {
                        Text(text = "FECHA", color = TextSecondary, fontSize = 8.sp)
                        Text(text = entrada.fecha, color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(38.dp).clip(RoundedCornerShape(8.dp))
                .background(Brush.horizontalGradient(entrada.buttonColors))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Text(text = entrada.buttonLabel, color = entrada.badgeColor, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}
data class EntradaAnticipada(val title: String, val badge: String, val sala: String, val fecha: String, val badgeColor: Color, val buttonLabel: String, val buttonColors: List<Color>, val thumbColors: List<Color>)
