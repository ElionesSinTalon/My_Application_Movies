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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mendoza.ruiz.myapplicationmovies.model.PeliculaDetalle
import mendoza.ruiz.myapplicationmovies.screens.components.ErrorStateView
import mendoza.ruiz.myapplicationmovies.viewmodel.ExploreViewModel

// ── Colors
private val Background    = Color(0xFF0D0D12)
private val AccentCyan    = Color(0xFF00E5FF)
private val AccentPink    = Color(0xFFE040FB)
private val TextPrimary   = Color(0xFFFFFFFF)
private val TextSecondary = Color(0xFFB0B0C0)
private val SearchBg      = Color(0xFF1A1A26)
private val StarYellow    = Color(0xFFFFC107)

@Composable
fun ExplorerScreen(
    viewModel: ExploreViewModel,
    onPeliculaClick: (PeliculaDetalle) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(Background)) {
        if (uiState.isLoading && uiState.peliculas.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = AccentCyan)
        } else if (uiState.errorMessage != null && uiState.peliculas.isEmpty()) {
            ErrorStateView(
                message = uiState.errorMessage ?: "Error desconocido",
                onRetry = { viewModel.cargarPeliculas() }
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Barra de búsqueda
                item(span = { GridItemSpan(2) }) {
                    Spacer(modifier = Modifier.height(16.dp))
                    SearchBar(
                        query = uiState.searchQuery,
                        onQueryChange = { viewModel.onSearchQueryChange(it) },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                // Filtros de géneros
                item(span = { GridItemSpan(2) }) {
                    GenreFilterRow(
                        genres = uiState.categorias,
                        selected = uiState.selectedCategoria,
                        onSelect = { viewModel.onCategoriaSelected(it) }
                    )
                }

                // Grid de películas
                itemsIndexed(uiState.filteredPeliculas) { index, movie ->
                    val isFirstInRow = index % 2 == 0
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
        }
    }
}

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
                            text = "Search for titles or genres...",
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

@Composable
fun GenreFilterRow(
    genres: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    LazyRow(
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
                            SolidColor(SearchBg)
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

@Composable
fun MovieExploreCard(
    movie: PeliculaDetalle,
    onPeliculaClick: (PeliculaDetalle) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.72f)
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.verticalGradient(movie.cardColors))
                .border(1.dp, Color(0xFF252535), RoundedCornerShape(12.dp))
                .clickable { onPeliculaClick(movie) }
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .align(Alignment.TopEnd)
                    .background(Brush.radialGradient(listOf(Color(0x18FFFFFF), Color.Transparent)))
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xCC0D0D12))
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = StarYellow, modifier = Modifier.size(10.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = movie.rating.toString(), color = TextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = movie.title, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = movie.category, color = TextSecondary, fontSize = 11.sp)
            Text(text = " · ", color = TextSecondary, fontSize = 11.sp)
            Text(text = "${movie.year}", color = TextSecondary, fontSize = 11.sp)
        }
    }
}