package mendoza.ruiz.myapplicationmovies.screens

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// ── Colors
private val Background    = Color(0xFF0D0D12)
private val SurfaceCard   = Color(0xFF16161F)
private val AccentCyan    = Color(0xFF00E5FF)
private val AccentPink    = Color(0xFFE040FB)
private val TextPrimary   = Color(0xFFFFFFFF)
private val TextSecondary = Color(0xFFB0B0C0)
private val DividerColor  = Color(0xFF2A2A3A)

// ── Models
data class FeaturedMovie(
    val title: String,
    val titleAccent: String,
    val duration: String,
    val genre: String,
    val format: String,
    val badge: String,
    val bgColors: List<Color>
)

private val featuredMovies = listOf(
    FeaturedMovie("CYBERPUNK ", "2077",  "154 min", "Acción / Sci-Fi",   "IMAX 4D", "ESTRENO MUNDIAL", listOf(Color(0xFF051014), Color(0xFF0A1A20), Color(0xFF0D0D12))),
    FeaturedMovie("NEON ",     "NIGHTS", "118 min", "Thriller / Noir",   "DOLBY",   "PRE-ESTRENO",     listOf(Color(0xFF1A0030), Color(0xFF0D001A), Color(0xFF0D0D12))),
    FeaturedMovie("THE ",      "VOID",   "132 min", "Horror / Misterio", "4DX",     "PRÓXIMAMENTE",    listOf(Color(0xFF080810), Color(0xFF10101A), Color(0xFF0D0D12))),
    FeaturedMovie("CHROME ",   "SOUL",   "145 min", "Acción / Drama",    "IMAX",    "ESTRENO MUNDIAL", listOf(Color(0xFF1A0800), Color(0xFF100500), Color(0xFF0D0D12))),
)

data class ProximaMovie(
    val title: String,
    val director: String,
    val date: String,
    val isHighlighted: Boolean = false,
    val cardColors: List<Color>
)

data class EntradaAnticipada(
    val title: String,
    val badge: String,
    val sala: String,
    val fecha: String,
    val badgeColor: Color,
    val buttonLabel: String,
    val buttonColors: List<Color>,
    val thumbColors: List<Color>
)

private val proximasMovies = listOf(
    ProximaMovie("NEON NIGHTS", "Director: K. Arasaka", "12 OCT", true,  listOf(Color(0xFF1A0030), Color(0xFF0A0018))),
    ProximaMovie("THE VOID",    "Director: S. Jenkins",  "25 OCT", false, listOf(Color(0xFF0A0A14), Color(0xFF14141E))),
    ProximaMovie("CHROME SOUL", "Director: M. Tanaka",   "03 NOV", false, listOf(Color(0xFF1A0800), Color(0xFF0E0500))),
    ProximaMovie("AFTERLIGHT",  "Director: L. Vane",     "15 NOV", false, listOf(Color(0xFF0A1208), Color(0xFF141E10))),
    ProximaMovie("DARK SIGNAL", "Director: R. Mori",     "22 NOV", false, listOf(Color(0xFF0A0A1A), Color(0xFF10102E))),
)

private val entradasAnticipadas = listOf(
    EntradaAnticipada("CYBERPUNK 2077", "VENTA PRIORITARIA", "ATMOS 01", "20/10", AccentPink, "RESERVAR AHORA", listOf(Color(0xFF1A1A2A), Color(0xFF252535)), listOf(Color(0xFF1A0800), Color(0xFF2A1000))),
    EntradaAnticipada("NEON NIGHTS",   "PRE-ESTRENO FAN",   "IMAX 04",  "11/10", AccentCyan,  "ADQUIRIR PASE",  listOf(Color(0xFF1A0030), Color(0xFF2A0050)), listOf(Color(0xFF1A0030), Color(0xFF0A0018))),
)

// ── PremiereScreen
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PremiereScreen(
    onPeliculaClick: (PeliculaDetalle) -> Unit = {}   // 👈 agregado
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        FeaturedMoviePager()
        Spacer(modifier = Modifier.height(32.dp))
        ProximamenteSection(onPeliculaClick = onPeliculaClick)   // 👈 propagado
        Spacer(modifier = Modifier.height(36.dp))
        EntradasAnticipadasSection()
        Spacer(modifier = Modifier.height(40.dp))
    }
}

// ── Featured Movie Auto-Pager
@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun FeaturedMoviePager() {
    val pagerState = rememberPagerState(pageCount = { maxOf(featuredMovies.size, 1) })

    LaunchedEffect(pagerState) {
        while (true) {
            delay(5000)
            val next = (pagerState.currentPage + 1) % featuredMovies.size
            pagerState.animateScrollToPage(next)
        }
    }

    Box(modifier = Modifier.fillMaxWidth().height(320.dp)) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            FeaturedMovieSlide(movie = featuredMovies[page])
        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(featuredMovies.size) { index ->
                val isActive = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .size(if (isActive) 20.dp else 6.dp, 6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            if (isActive) Brush.horizontalGradient(listOf(AccentCyan, AccentPink))
                            else Brush.horizontalGradient(listOf(Color(0xFF444455), Color(0xFF444455)))
                        )
                )
            }
        }
    }
}

// ── Slide individual
@Composable
fun FeaturedMovieSlide(movie: FeaturedMovie) {
    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(movie.bgColors))) {
        Box(modifier = Modifier.fillMaxWidth().height(200.dp).align(Alignment.BottomCenter).background(Brush.verticalGradient(listOf(Color.Transparent, Background))))
        Column(modifier = Modifier.align(Alignment.BottomStart).padding(start = 20.dp, end = 20.dp, bottom = 36.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = movie.badge, color = AccentCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = TextPrimary, fontWeight = FontWeight.ExtraBold)) { append(movie.title) }
                    withStyle(SpanStyle(brush = Brush.horizontalGradient(listOf(AccentCyan, AccentPink)), fontWeight = FontWeight.ExtraBold)) { append(movie.titleAccent) }
                },
                fontSize = 36.sp, lineHeight = 40.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "⏱ ${movie.duration}", color = TextSecondary, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "🎬 ${movie.genre}", color = TextSecondary, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.clip(RoundedCornerShape(4.dp)).border(1.dp, TextSecondary.copy(alpha = 0.5f), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text(text = movie.format, color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(46.dp).widthIn(min = 160.dp).clip(RoundedCornerShape(10.dp)).background(Brush.horizontalGradient(listOf(AccentCyan, AccentPink)))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 20.dp)) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Background, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "VER TRÁILER", color = Background, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 0.5.sp)
                }
            }
        }
    }
}

// ── Próximamente
@Composable
fun ProximamenteSection(
    onPeliculaClick: (PeliculaDetalle) -> Unit = {}   // 👈 agregado
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "PRÓXIMAMENTE", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
            Row(modifier = Modifier.clickable { }, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "VER TODO", color = AccentCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Spacer(modifier = Modifier.width(2.dp))
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(proximasMovies) { _, movie ->
                ProximaMovieCard(
                    movie = movie,
                    onPeliculaClick = onPeliculaClick   // 👈 propagado
                )
            }
        }
    }
}

// ── Card de película próxima
@Composable
fun ProximaMovieCard(
    movie: ProximaMovie,
    onPeliculaClick: (PeliculaDetalle) -> Unit = {}   // 👈 agregado
) {
    val width = if (movie.isHighlighted) 200.dp else 160.dp

    Box(
        modifier = Modifier
            .width(width)
            .height(260.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Brush.verticalGradient(movie.cardColors))
            .border(
                width = if (movie.isHighlighted) 1.5.dp else 1.dp,
                brush = if (movie.isHighlighted)
                    Brush.verticalGradient(listOf(AccentPink, AccentCyan))
                else
                    Brush.verticalGradient(listOf(DividerColor, DividerColor)),
                shape = RoundedCornerShape(14.dp)
            )
            // 👈 clickable que construye PeliculaDetalle
            .clickable {
                onPeliculaClick(
                    PeliculaDetalle(
                        id         = movie.title.hashCode(),
                        title      = movie.title,
                        overview   = "Próximo estreno — ${movie.director}. Una experiencia cinematográfica única.",
                        rating     = if (movie.isHighlighted) 9.2 else 8.5,
                        category   = "PREMIERE",
                        duration   = 120,
                        year       = 2024,
                        director   = movie.director,
                        cardColors = movie.cardColors
                    )
                )
            }
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(120.dp).align(Alignment.TopCenter).background(Brush.radialGradient(colors = listOf(Color(0x22FFFFFF), Color.Transparent))))

        Box(
            modifier = Modifier.align(Alignment.TopEnd).padding(10.dp)
                .clip(RoundedCornerShape(6.dp)).background(Color(0xCC0D0D12))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(text = movie.date, color = if (movie.isHighlighted) AccentPink else TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
        }

        Box(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xEE0D0D12))))
                .padding(start = 12.dp, end = 12.dp, top = 32.dp, bottom = 14.dp)
        ) {
            Column {
                Text(text = movie.title, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 0.5.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = movie.director, color = TextSecondary, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

// ── Entradas Anticipadas
@Composable
fun EntradasAnticipadasSection() {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(text = "ENTRADAS ANTICIPADAS", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
        Spacer(modifier = Modifier.height(16.dp))
        entradasAnticipadas.chunked(2).forEach { rowItems ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                rowItems.forEach { entrada -> EntradaCard(entrada = entrada, modifier = Modifier.weight(1f)) }
                if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

// ── Card de entrada anticipada
@Composable
fun EntradaCard(entrada: EntradaAnticipada, modifier: Modifier = Modifier) {
    Column(modifier = modifier.clip(RoundedCornerShape(14.dp)).background(SurfaceCard).border(1.dp, DividerColor, RoundedCornerShape(14.dp)).padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(width = 52.dp, height = 68.dp).clip(RoundedCornerShape(8.dp)).background(Brush.verticalGradient(entrada.thumbColors)).border(1.dp, DividerColor, RoundedCornerShape(8.dp)))
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Box(modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(entrada.badgeColor.copy(alpha = 0.15f)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text(text = entrada.badge, color = entrada.badgeColor, fontSize = 8.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 0.5.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = entrada.title, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column {
                        Text(text = "SALA",  color = TextSecondary, fontSize = 8.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp)
                        Text(text = entrada.sala,  color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Column {
                        Text(text = "FECHA", color = TextSecondary, fontSize = 8.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp)
                        Text(text = entrada.fecha, color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(38.dp).clip(RoundedCornerShape(8.dp))
                .background(Brush.horizontalGradient(entrada.buttonColors))
                .border(1.dp, entrada.badgeColor.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Done, contentDescription = null, tint = entrada.badgeColor, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = entrada.buttonLabel, color = entrada.badgeColor, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 0.8.sp)
            }
        }
    }
}