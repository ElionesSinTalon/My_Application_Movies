package mendoza.ruiz.myapplicationmovies.screens

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
import androidx.lifecycle.ViewModel

// ── Colores
private val Background    = Color(0xFF0D0D12)
private val SurfaceCard   = Color(0xFF16161F)
private val AccentCyan    = Color(0xFF00E5FF)
private val AccentPink    = Color(0xFFE040FB)
private val TextPrimary   = Color(0xFFFFFFFF)
private val TextSecondary = Color(0xFFB0B0C0)
private val SearchBg      = Color(0xFF1A1A26)
private val StarYellow    = Color(0xFFFFC107)

// ── Modelos de datos ───────────────────────────────────────────────────────────
data class Pelicula(
    val id: Int,
    val title: String,
    val overview: String,
    val year: Int,
    val rating: Double,
    val category: String
)

class ExploreViewModel : ViewModel(){
    var peliculas by mutableStateOf<List<Pelicula>>(emptyList())
        private set

    fun cargarPelicula(lista: List<Pelicula>){
        peliculas = lista
    }
}

private val genres = listOf("ALL", "ACTION", "DRAMA", "SCI-FI", "HORROR", "MYSTERY", "CLASSIC", "NOIR")
private const val PAGE_SIZE = 10

// ── Pantalla principal
@Composable
fun ExploreScreen(peliculas: List<Pelicula> = emptyList()) {
    var searchQuery by remember {mutableStateOf("")}
    var selectedGenre by remember {mutableStateOf("ALL")}
    var visibleCount by remember {mutableIntStateOf(PAGE_SIZE)}

    //Generos dinamicos desde la api
    val genres = listOf("ALL") + peliculas.map { it.category }.distinct()
    val filtered = remember(searchQuery, selectedGenre, peliculas){
        peliculas.filter { pelicula ->
            val matchesGenre = selectedGenre == "ALL" || pelicula.category == selectedGenre
            val matchesSearch = searchQuery.isBlank() ||
                    pelicula.title.contains(searchQuery, ignoreCase = true) ||
                    pelicula.category.contains(searchQuery, ignoreCase = true)
            matchesGenre && matchesSearch
        }.also{visibleCount = PAGE_SIZE}
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
        itemsIndexed(visibleMovies) { index, pelicula ->
            val isFirstInRow = index % 2 == 0
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 3 })
            ) {
                MovieExploreCard(
                    pelicula = pelicula,
                    modifier = Modifier.padding(
                        start = if (isFirstInRow) 16.dp else 0.dp,
                        end   = if (!isFirstInRow) 16.dp else 0.dp
                    )
                )
            }
        }
        // ── Botón Reveal More
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
fun MovieExploreCard(pelicula: Pelicula, modifier: Modifier = Modifier) {

    // Color del card basado en el id (reemplaza cardColors de la lista hardcodeada)
    val cardColors = remember(pelicula.id) {
        val palette = listOf(
            listOf(Color(0xFF0A0A1A), Color(0xFF1A0A2E)),
            listOf(Color(0xFF1A0F00), Color(0xFF2E1A00)),
            listOf(Color(0xFF001A1A), Color(0xFF002E2E)),
            listOf(Color(0xFF0F0F0F), Color(0xFF1A1A1A)),
            listOf(Color(0xFF0A1A00), Color(0xFF142E00)),
        )
        palette[pelicula.id % palette.size]
    }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.72f)
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.verticalGradient(cardColors))
                .border(1.dp, Color(0xFF252535), RoundedCornerShape(12.dp))
        ) {
            // Badge rating
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
                        text = String.format("%.1f", pelicula.rating),
                        color = TextPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = pelicula.title,
            color = TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(3.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = pelicula.category, color = TextSecondary, fontSize = 11.sp)
            Text(text = " · ", color = TextSecondary, fontSize = 11.sp)
            Text(text = "${pelicula.year}", color = TextSecondary, fontSize = 11.sp)
        }
    }
}

// ── Reveal More Button ─────────────────────────────────────────────────────────
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
