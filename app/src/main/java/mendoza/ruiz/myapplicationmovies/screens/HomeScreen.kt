package mendoza.ruiz.myapplicationmovies.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import mendoza.ruiz.myapplicationmovies.model.PeliculaDetalle
import mendoza.ruiz.myapplicationmovies.screens.components.BannerCarousel
import mendoza.ruiz.myapplicationmovies.screens.components.ErrorStateView
import mendoza.ruiz.myapplicationmovies.viewmodel.HomeViewModel

// ── Paleta de colors
private val Background    = Color(0xFF0D0D12)
private val AccentCyan    = Color(0xFF00E5FF)
private val AccentPink    = Color(0xFFE040FB)
private val TextPrimary   = Color(0xFFFFFFFF)
private val TextSecondary = Color(0xFFB0B0C0)
private val StarYellow    = Color(0xFFFFC107)
private val PremiereTag   = Color(0xFFE040FB)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    onPeliculaClick: (PeliculaDetalle) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = modifier.fillMaxSize().background(Background)) {
        if (uiState.isLoading && uiState.featuredPeliculas.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = AccentCyan)
        } else if (uiState.errorMessage != null && uiState.featuredPeliculas.isEmpty()) {
            ErrorStateView(
                message = uiState.errorMessage ?: "Unknown Error",
                onRetry = { viewModel.cargarHome() }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Top Banner Carousel con posters aleatorios
                BannerCarousel(
                    movies = uiState.randomPosters,
                    onPeliculaClick = onPeliculaClick
                )

                // Hero Section con la primera de featured o una por defecto
                val featured = uiState.featuredPeliculas.firstOrNull()
                HeroSection(pelicula = featured, onPeliculaClick = onPeliculaClick)

                Spacer(modifier = Modifier.height(24.dp))

                NowPlayingSection(
                    peliculas = uiState.nowPlaying,
                    onPeliculaClick = onPeliculaClick
                )

                Spacer(modifier = Modifier.height(32.dp))
                PopularGenresSection()

                Spacer(modifier = Modifier.height(32.dp))
                TrendingNowSection(peliculas = uiState.trending)

                Spacer(modifier = Modifier.height(32.dp))
                ContinueWatchingSection()

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun HeroSection(
    pelicula: PeliculaDetalle?,
    onPeliculaClick: (PeliculaDetalle) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
    ) {
        // Fondo con colores de la película o gradiente por defecto
        val bgColors = pelicula?.cardColors ?: listOf(Color(0xFF1C0A0A), Color(0xFF0D0D12))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors = bgColors))
        )

        // Poster real con AsyncImage
        pelicula?.let { p ->
            if (p.posterUrl.isNotBlank()) {
                AsyncImage(
                    model = p.posterUrl,
                    contentDescription = p.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(colors = listOf(Color.Transparent, Background))
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, end = 20.dp, bottom = 24.dp)
        ) {
            pelicula?.let { p ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .background(PremiereTag, RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "PREMIERE",
                            color = TextPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = StarYellow,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${p.rating} Rating",
                        color = StarYellow,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = p.title,
                    color = TextPrimary,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 38.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = p.overview,
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(0.85f)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = { onPeliculaClick(p) },
                        colors = ButtonDefaults.buttonColors(containerColor = AccentCyan),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Background,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Watch now", color = Background, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }

                    OutlinedButton(
                        onClick = {},
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, TextSecondary),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = TextPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "My List", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun NowPlayingSection(
    peliculas: List<PeliculaDetalle>,
    onPeliculaClick: (PeliculaDetalle) -> Unit
) {
    Column {
        SectionHeader(title = "Now Playing")

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(peliculas) { index, pelicula ->
                MovieCard(
                    index = index,
                    pelicula = pelicula,
                    onPeliculaClick = onPeliculaClick
                )
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = title, color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(2.dp)
                    .background(
                        Brush.horizontalGradient(colors = listOf(AccentCyan, AccentPink)),
                        RoundedCornerShape(1.dp)
                    )
            )
        }
        Text(text = "View all", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.5.sp)
    }
}

@Composable
fun MovieCard(
    index: Int,
    pelicula: PeliculaDetalle,
    onPeliculaClick: (PeliculaDetalle) -> Unit
) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.verticalGradient(colors = pelicula.cardColors))
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(colors = listOf(Color(0x33FFFFFF), Color(0x00FFFFFF))),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onPeliculaClick(pelicula) }
    ) {
        if (pelicula.posterUrl.isNotBlank()) {
            AsyncImage(
                model = pelicula.posterUrl,
                contentDescription = pelicula.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Box(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.TopEnd)
                .background(
                    Brush.radialGradient(colors = listOf(Color(0x22FFFFFF), Color.Transparent))
                )
        )
        Text(
            text = pelicula.title.take(1),
            color = Color(0x44FFFFFF),
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.Center)
        )
        Text(
            text = pelicula.title,
            color = TextPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.BottomCenter).padding(8.dp)
        )
    }
}

@Composable
fun PopularGenresSection() {
    val genres = listOf(
        Triple("Action/Adventure",     listOf(Color(0xFF12121E), Color(0xFF1E1E32)), AccentCyan),
        Triple("Sci-Fi/Adventure",   listOf(Color(0xFF1A0D2E), Color(0xFF2D1060)), AccentPink),
        Triple("Animation", listOf(Color(0xFF2A0A0A), Color(0xFF1A0000)), Color(0xFFFF5252)),
    )
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(text = "Popular genres", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            genres.forEach { (label, colors, accent) ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(90.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Brush.verticalGradient(colors))
                        .border(width = 1.dp, color = accent.copy(alpha = 0.25f), shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = label, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.5.sp)
                }
            }
        }
    }
}

@Composable
fun TrendingNowSection(peliculas: List<PeliculaDetalle>) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Trending Now", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            peliculas.forEachIndexed { index, pelicula ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .border(1.dp, Color(0xFF333344), RoundedCornerShape(20.dp))
                        .background(Color(0xFF1A1A28))
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "#${String.format("%02d", index + 1)}", color = AccentCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = pelicula.title.uppercase(), color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
fun ContinueWatchingSection() {
    val continueItems = listOf(
        ContinueItem("Crossing the Divide", "DOCUMENTARY", "45 mins left", false, 0.35f, listOf(Color(0xFF1A1200), Color(0xFF2A1E00))),
        ContinueItem("Static Frequencies",  "THRILLER",    "NEW EPISODE",  true,  -1f,   listOf(Color(0xFF0D1A0D), Color(0xFF1A2A00))),
    )
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(text = "Continue watching", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            continueItems.forEach { item ->
                ContinueCard(item = item, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun ContinueCard(item: ContinueItem, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Brush.verticalGradient(item.cardColors))
                .border(width = 1.dp, color = Color(0xFF333344), shape = RoundedCornerShape(10.dp))
        )
        if (item.progress >= 0f) {
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier.fillMaxWidth().height(3.dp).background(Color(0xFF333344), RoundedCornerShape(2.dp))
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(item.progress).fillMaxHeight()
                        .background(Brush.horizontalGradient(listOf(AccentCyan, AccentPink)), RoundedCornerShape(2.dp))
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = item.title, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = item.genre, color = TextSecondary, fontSize = 11.sp)
            Text(text = " · ", color = TextSecondary, fontSize = 11.sp)
            Text(text = item.subtitle, color = if (item.subtitleAccent) AccentCyan else TextSecondary, fontSize = 11.sp)
        }
    }
}
data class ContinueItem(val title: String, val genre: String, val subtitle: String, val subtitleAccent: Boolean, val progress: Float, val cardColors: List<Color>)
