package mendoza.ruiz.myapplicationmovies.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import mendoza.ruiz.myapplicationmovies.repository.PeliculaRepository
import mendoza.ruiz.myapplicationmovies.ui.theme.MyApplicationMoviesTheme
import mendoza.ruiz.myapplicationmovies.viewmodel.HomeViewModel
import mendoza.ruiz.myapplicationmovies.viewmodel.ViewModelFactory

// ── Paleta de colors
private val Background   = Color(0xFF0D0D12)
private val AccentCyan   = Color(0xFF00E5FF)
private val AccentPink   = Color(0xFFE040FB)
private val TextPrimary  = Color(0xFFFFFFFF)
private val TextSecondary= Color(0xFFB0B0C0)
private val StarYellow   = Color(0xFFFFC107)
private val PremiereTag  = Color(0xFFE040FB)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    /*
    val factory = ViewModelFactory(PeliculaRepository(/*PeliculaApiService*/))
    val viewModel: HomeViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsState()


    // Tu UI usando uiState
    MyApplicationMoviesTheme(
        featured   = uiState.featuredPeliculas,
        nowPlaying = uiState.nowPlaying,
        trending   = uiState.trending,
        isLoading  = uiState.isLoading
    )*/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        HeroSection()
        Spacer(modifier = Modifier.height(24.dp))
        NowPlayingSection()
        Spacer(modifier = Modifier.height(32.dp))
        PopularGenresSection()
        Spacer(modifier = Modifier.height(32.dp))
        TrendingNowSection()
        Spacer(modifier = Modifier.height(32.dp))
        ContinueWatchingSection()
        Spacer(modifier = Modifier.height(40.dp))
    }
}
@Composable
fun HeroSection(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
    ) {
        // Imagen principal → cuadro vacío con degradado
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1C0A0A),
                            Color(0xFF0D0D12)
                        )
                    )
                )
        ) {
            // Simulación de textura/ambiente con capas de color
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0x553D0000),
                                Color(0x00000000)
                            )
                        )
                    )
            )
        }
        // Degradado inferior para mezclar con el fondo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Background
                        )
                    )
                )
        )
        // Contenido del hero
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, end = 20.dp, bottom = 24.dp)
        ) {
            // Badge PREMIERE
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
                    text = "9.2 RATING",
                    color = StarYellow,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Título con estilo mixto
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = TextPrimary, fontWeight = FontWeight.ExtraBold)) {
                        append("Shadows of ")
                    }
                    withStyle(SpanStyle(color = AccentCyan, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)) {
                        append("Aeon")
                    }
                },
                fontSize = 34.sp,
                lineHeight = 38.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            // Descripción
            Text(
                text = "Content Description",
                color = TextSecondary,
                fontSize = 13.sp,
                lineHeight = 19.sp,
                modifier = Modifier.fillMaxWidth(0.85f)
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Botones
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // WATCH NOW
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentCyan
                    ),
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
                    Text(
                        text = "Ver ahora",
                        color = Background,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                // MY LIST
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
                    Text(
                        text = "Mi Lista",
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
@Composable
fun NowPlayingSection(modifier: Modifier = Modifier) {
    Column {
        // Encabezado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Now Playing",
                    color = TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                // Línea decorativa cyan debajo del título
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(2.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(AccentCyan, AccentPink)
                            ),
                            RoundedCornerShape(1.dp)
                        )
                )
            }
            Text(
                text = "VIEW ALL",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Carrusel de posters
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(6) { index ->
                MovieCard(index = index)
            }
        }
    }
}
// ── Card de película
@Composable
fun MovieCard(index: Int) {
    val cardColors = listOf(
        listOf(Color(0xFF0A1A1A), Color(0xFF003333)),
        listOf(Color(0xFF1A0000), Color(0xFF330000)),
        listOf(Color(0xFF000D1A), Color(0xFF001A33)),
        listOf(Color(0xFF1A1000), Color(0xFF332000)),
        listOf(Color(0xFF0D001A), Color(0xFF1A0033)),
        listOf(Color(0xFF001A0A), Color(0xFF00331A)),
    )
    val colors = cardColors[index % cardColors.size]

    Box(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.verticalGradient(colors = colors))
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0x33FFFFFF), Color(0x00FFFFFF))
                ),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.TopEnd)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0x22FFFFFF), Color.Transparent)
                    )
                )
        )
        Text(
            text = "${index + 1}",
            color = Color(0x44FFFFFF),
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
private val genres = listOf(
    Triple("NOIR",    listOf(Color(0xFF12121E), Color(0xFF1E1E32)), AccentCyan),
    Triple("SCI-FI",  listOf(Color(0xFF1A0D2E), Color(0xFF2D1060)), AccentPink),
    Triple("PRESTIGE",listOf(Color(0xFF2A0A0A), Color(0xFF1A0000)), Color(0xFFFF5252)),
)

@Composable
fun PopularGenresSection(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = "Generos populares",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
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
                        .border(
                            width = 1.dp,
                            color = accent.copy(alpha = 0.25f),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Punto decorativo izquierdo (solo SCI-FI)
                    if (label == "SCI-FI") {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 10.dp)
                                .size(4.dp)
                                .background(accent.copy(alpha = 0.6f), RoundedCornerShape(2.dp))
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 10.dp)
                                .size(4.dp)
                                .background(accent.copy(alpha = 0.6f), RoundedCornerShape(2.dp))
                        )
                    }
                    Text(
                        text = label,
                        color = TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.5.sp
                    )
                }
            }
        }
    }
}

// ── Trending Now
private val trendingItems = listOf(
    Pair("#01", "CYBERPUNK 2077"),
    Pair("#02", "MIDNIGHT LIBRARY"),
    Pair("#03", "QUANTUM STATE"),
)
@Composable
fun TrendingNowSection(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        // Encabezado
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Trending Now",
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                tint = AccentCyan,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        // Chips de trending
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            trendingItems.forEach { (number, title) ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .border(1.dp, Color(0xFF333344), RoundedCornerShape(20.dp))
                        .background(Color(0xFF1A1A28))
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = number,
                            color = AccentCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = title,
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
// ── Continue Watching
data class ContinueItem(
    val title: String,
    val genre: String,
    val subtitle: String,
    val subtitleAccent: Boolean,
    val progress: Float,// 0f–1f, -1 = sin barra
    val cardColors: List<Color>
)
private val continueItems = listOf(
    ContinueItem(
        title = "Crossing the Divide",
        genre = "DOCUMENTARY",
        subtitle = "45 mins left",
        subtitleAccent = false,
        progress = 0.35f,
        cardColors = listOf(Color(0xFF1A1200), Color(0xFF2A1E00))
    ),
    ContinueItem(
        title = "Static Frequencies",
        genre = "THRILLER",
        subtitle = "NEW EPISODE",
        subtitleAccent = true,
                progress = -1f,
        cardColors = listOf(Color(0xFF0D1A0D), Color(0xFF1A2A00))
    ),
)
@Composable
fun ContinueWatchingSection(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = "Continuar viendo",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
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
        // Cuadro de imagen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Brush.verticalGradient(item.cardColors))
                .border(
                    width = 1.dp,
                    color = Color(0xFF333344),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            // Degradado inferior dentro del card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color(0xAA000000))
                        )
                    )
            )
        }

        // Barra de progreso (solo si aplica)
        if (item.progress >= 0f) {
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFF333344))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(item.progress)
                        .fillMaxHeight()
                        .background(
                            Brush.horizontalGradient(
                                listOf(AccentCyan, AccentPink)
                            ),
                            RoundedCornerShape(2.dp)
                        )
                )
            }
        } else {
            Spacer(modifier = Modifier.height(9.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.title,
            color = TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = item.genre,
                color = TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = " · ",
                color = TextSecondary,
                fontSize = 11.sp
            )
            Text(
                text = item.subtitle,
                color = if (item.subtitleAccent) AccentCyan else TextSecondary,
                fontSize = 11.sp,
                fontWeight = if (item.subtitleAccent) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}