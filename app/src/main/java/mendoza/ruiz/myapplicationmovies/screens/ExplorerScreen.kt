package mendoza.ruiz.myapplicationmovies.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Colors
private val Background    = Color(0xFF0D0D12)
//private val SurfaceCard   = Color(0xFF16161F)
private val AccentCyan    = Color(0xFF00E5FF)
private val AccentPink    = Color(0xFFE040FB)
private val TextPrimary   = Color(0xFFFFFFFF)
private val TextSecondary = Color(0xFFB0B0C0)
private val SearchBg      = Color(0xFF1A1A26)
private val StarYellow    = Color(0xFFFFC107)

// ── Modelos de datos
data class Movie(
    val title: String,
    val genre: String,
    val duration: String,
    val rating: Float,
    val cardColors: List<Color>,
    val ratingColor: Color = AccentCyan
)

private val allMovies = listOf(
    Movie("The Last Echo",      "THRILLER",  "2H 15M", 8.9f, listOf(Color(0xFF0A0A1A), Color(0xFF1A0A2E))),
    Movie("Cinema Paradiso",    "DRAMA",     "1H 45M", 8.2f, listOf(Color(0xFF1A0F00), Color(0xFF2E1A00))),
    Movie("Vector Prime",       "SCI-FI",    "2H 40M", 9.1f, listOf(Color(0xFF001A1A), Color(0xFF002E2E))),
    Movie("Shadow Waltz",       "MYSTERY",   "2H 10M", 7.8f, listOf(Color(0xFF0F0F0F), Color(0xFF1A1A1A))),
    Movie("Bloom Realm",        "ANIMATION", "1H 35M", 8.5f, listOf(Color(0xFF0A1A00), Color(0xFF142E00))),
    Movie("Iron Cathedral",     "ACTION",    "2H 05M", 7.4f, listOf(Color(0xFF1A0A00), Color(0xFF0F0800))),
    Movie("Ember Protocol",     "ACTION",    "1H 55M", 8.7f, listOf(Color(0xFF1A0800), Color(0xFF2E1000))),
    Movie("Space Sarlmink",     "SCI-FI",    "2H 20M", 9.3f, listOf(Color(0xFF000A1A), Color(0xFF00102E))),
    Movie("Hollow Signal",      "THRILLER",  "1H 50M", 8.1f, listOf(Color(0xFF0A000A), Color(0xFF1A001A))),
    Movie("Golden Hour",        "DRAMA",     "1H 30M", 7.2f, listOf(Color(0xFF1A1200), Color(0xFF2E1E00))),
    Movie("Neon Requiem",       "NOIR",      "2H 00M", 8.8f, listOf(Color(0xFF00001A), Color(0xFF00002E))),
    Movie("Phantom Archive",    "MYSTERY",   "2H 30M", 8.4f, listOf(Color(0xFF0A0A0A), Color(0xFF141414))),
    Movie("Crystal Drift",      "SCI-FI",    "1H 45M", 7.9f, listOf(Color(0xFF001A0A), Color(0xFF002E14))),
    Movie("Crimson Veil",       "HORROR",    "1H 40M", 8.3f, listOf(Color(0xFF1A0000), Color(0xFF2E0000))),
    Movie("The Classic",        "CLASSIC",   "2H 10M", 9.0f, listOf(Color(0xFF12100A), Color(0xFF1E1A0A))),
    Movie("Deep Current",       "DRAMA",     "1H 55M", 7.6f, listOf(Color(0xFF001018), Color(0xFF001A28))),
    Movie("Midnight Frequency", "NOIR",      "2H 15M", 8.6f, listOf(Color(0xFF080818), Color(0xFF10102A))),
    Movie("Surge Protocol",     "ACTION",    "1H 50M", 7.8f, listOf(Color(0xFF180800), Color(0xFF281200))),
)

private val genres = listOf("ALL", "ACTION", "DRAMA", "SCI-FI", "HORROR", "MYSTERY", "CLASSIC", "NOIR")
private const val PAGE_SIZE = 10

// ── Pantalla principal
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExplorerScreen(
    onPeliculaClick: (PeliculaDetalle) -> Unit = {}
) {
    var searchQuery    by remember { mutableStateOf("") }
    var selectedGenre  by remember { mutableStateOf("ALL") }
    var visibleCount   by remember { mutableIntStateOf(PAGE_SIZE) }

    val filtered = remember(searchQuery, selectedGenre) {
        allMovies.filter { movie ->
            val matchesGenre  = selectedGenre == "ALL" || movie.genre == selectedGenre
            val matchesSearch = searchQuery.isBlank() ||
                    movie.title.contains(searchQuery, ignoreCase = true) ||
                    movie.genre.contains(searchQuery, ignoreCase = true)
            matchesGenre && matchesSearch
        }.also { visibleCount = PAGE_SIZE }   // reset paginación al filtrar
    }

    val visibleMovies = filtered.take(visibleCount)
    val hasMore       = visibleCount < filtered.size

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // ── Barra de búsqueda
        item(span = { GridItemSpan(2) }) {
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // ── Filtros de géneros
        item(span = { GridItemSpan(2) }) {
            GenreFilterRow(
                genres = genres,
                selected = selectedGenre,
                onSelect = { selectedGenre = it }
            )
        }

        // ── Grid de películas
        itemsIndexed(visibleMovies) { index, movie ->
            val isFirstInRow = index % 2 == 0
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 3 })
            ) {
                MovieExploreCard(
                    movie = movie,
                    onPeliculaClick = onPeliculaClick,
                    modifier = Modifier.padding(
                        start = if (isFirstInRow) 16.dp else 0.dp,
                        end   = if (!isFirstInRow) 16.dp else 0.dp
                    )
                )
            }
        }

        // ── Bottom Reveal More
        if (hasMore) {
            item(span = { GridItemSpan(2) }) {
                RevealMoreButton(
                    onClick = { visibleCount += PAGE_SIZE },
                    remaining = filtered.size - visibleCount,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

// ── Search Bar
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(SearchBg)
            .border(1.dp, Color(0xFF2A2A3A), RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = TextPrimary,
                    fontSize = 14.sp
                ),
                decorationBox = { inner ->
                    if (query.isEmpty()) {
                        Text(
                            text = "Search for titles, directors, or genres...",
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                    }
                    inner()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ── Genre Filter Row
@Composable
fun GenreFilterRow(
    genres: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    androidx.compose.foundation.lazy.LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(genres.size) { i ->
            val genre    = genres[i]
            val isActive = genre == selected
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        if (isActive)
                            Brush.horizontalGradient(listOf(AccentCyan, AccentPink))
                        else
                            Brush.horizontalGradient(listOf(SearchBg, SearchBg))
                    )
                    .border(
                        width = 1.dp,
                        color = if (isActive) Color.Transparent else Color(0xFF2A2A3A),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { onSelect(genre) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = genre,
                    color = if (isActive) Background else TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

// ── Movie Explore Card
@Composable
fun MovieExploreCard(
    movie: Movie,
    onPeliculaClick: (PeliculaDetalle) -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Cuadro de imagen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.72f)
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.verticalGradient(movie.cardColors))
                .border(1.dp, Color(0xFF252535), RoundedCornerShape(12.dp))
                .clickable {
                    onPeliculaClick(
                        PeliculaDetalle(
                            id          = movie.title.hashCode(),
                            title       = movie.title,
                            overview    = "Una historia imperdible llena de acción y suspenso.",
                            rating      = movie.rating.toDouble(),
                            category    = movie.genre,
                            duration    = parseDuration(movie.duration),
                            year        = 2024,
                            cardColors  = movie.cardColors
                        )
                    )
                }
        ) {
            // Degradado sutil en la esquina superior derecha
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .align(Alignment.TopEnd)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color(0x18FFFFFF), Color.Transparent)
                        )
                    )
            )

            // Badge de rating (esquina superior derecha)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xCC0D0D12))
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = StarYellow,
                        modifier = Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = movie.rating.toString(),
                        color = TextPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Info debajo del card
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title,
            color = TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(3.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = movie.genre,
                color = TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            Text(text = " · ", color = TextSecondary, fontSize = 11.sp)
            Text(
                text = movie.duration,
                color = TextSecondary,
                fontSize = 11.sp
            )
        }
    }
}

// ── Reveal More Button
@Composable
fun RevealMoreButton(
    onClick: () -> Unit,
    remaining: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF0A2A2A), Color(0xFF1A0A2A))
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(
                        listOf(AccentCyan.copy(alpha = 0.5f), AccentPink.copy(alpha = 0.5f))
                    ),
                    shape = RoundedCornerShape(14.dp)
                )
                .clickable(onClick = onClick)
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Reveal More",
                    color = TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$remaining titles waiting",
                    color = AccentCyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// ── Helper: convierte "2H 15M" a minutos
fun parseDuration(duration: String): Int {
    val horas    = Regex("""(\d+)H""").find(duration)?.groupValues?.get(1)?.toIntOrNull() ?: 0
    val minutos  = Regex("""(\d+)M""").find(duration)?.groupValues?.get(1)?.toIntOrNull() ?: 0
    return horas * 60 + minutos
}