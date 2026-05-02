package mendoza.ruiz.myapplicationmovies.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
//import androidx.compose.material.icons.filled.ChevronRight
//import androidx.compose.material.icons.filled.ConfirmationNumber
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

// ── Colores ────────────────────────────────────────────────────────────────────
private val Background    = Color(0xFF0D0D12)
private val SurfaceCard   = Color(0xFF16161F)
private val AccentCyan    = Color(0xFF00E5FF)
private val AccentPink    = Color(0xFFE040FB)
private val TextPrimary   = Color(0xFFFFFFFF)
private val TextSecondary = Color(0xFFB0B0C0)
private val DividerColor  = Color(0xFF2A2A3A)

// ── Modelos
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
    ProximaMovie(
        title = "NEON NIGHTS",
        director = "Director: K. Arasaka",
        date = "12 OCT",
        isHighlighted = true,
        cardColors = listOf(Color(0xFF1A0030), Color(0xFF0A0018))
    ),
    ProximaMovie(
        title = "THE VOID",
        director = "Director: S. Jenkins",
        date = "25 OCT",
        cardColors = listOf(Color(0xFF0A0A14), Color(0xFF14141E))
    ),
    ProximaMovie(
        title = "CHROME SOUL",
        director = "Director: M. Tanaka",
        date = "03 NOV",
        cardColors = listOf(Color(0xFF1A0800), Color(0xFF0E0500))
    ),
    ProximaMovie(
        title = "AFTERLIGHT",
        director = "Director: L. Vane",
        date = "15 NOV",
        cardColors = listOf(Color(0xFF0A1208), Color(0xFF141E10))
    ),
    ProximaMovie(
        title = "DARK SIGNAL",
        director = "Director: R. Mori",
        date = "22 NOV",
        cardColors = listOf(Color(0xFF0A0A1A), Color(0xFF10102E))
    ),
)

private val entradasAnticipadas = listOf(
    EntradaAnticipada(
        title = "CYBERPUNK 2077",
        badge = "VENTA PRIORITARIA",
        sala = "ATMOS 01",
        fecha = "20/10",
        badgeColor = AccentPink,
        buttonLabel = "RESERVAR AHORA",
        buttonColors = listOf(Color(0xFF1A1A2A), Color(0xFF252535)),
        thumbColors = listOf(Color(0xFF1A0800), Color(0xFF2A1000))
    ),
    EntradaAnticipada(
        title = "NEON NIGHTS",
        badge = "PRE-ESTRENO FAN",
        sala = "IMAX 04",
        fecha = "11/10",
        badgeColor = AccentCyan,
        buttonLabel = "ADQUIRIR PASE",
        buttonColors = listOf(Color(0xFF1A0030), Color(0xFF2A0050)),
        thumbColors = listOf(Color(0xFF1A0030), Color(0xFF0A0018))
    ),
)

// ── PremiereScreen
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PremiereScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        ProximamenteSection()
        Spacer(modifier = Modifier.height(36.dp))
        EntradasAnticipadasSection()
        Spacer(modifier = Modifier.height(40.dp))
    }
}

// ── Próximamente
@Composable
fun ProximamenteSection() {
    Column {
        // Encabezado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PRÓXIMAMENTE",
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
            Row(
                modifier = Modifier.clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "VER TODO",
                    color = AccentCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = AccentCyan,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Carrusel horizontal
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(proximasMovies) { _, movie ->
                ProximaMovieCard(movie = movie)
            }
        }
    }
}

// ── Card de película próxima
@Composable
fun ProximaMovieCard(movie: ProximaMovie) {
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
            .clickable { }
    ) {
        // Brillo decorativo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0x22FFFFFF), Color.Transparent)
                    )
                )
        )

        // Badge de fecha (arriba derecha)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xCC0D0D12))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = movie.date,
                color = if (movie.isHighlighted) AccentPink else TextSecondary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }

        // Info en la parte inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color(0xEE0D0D12))
                    )
                )
                .padding(start = 12.dp, end = 12.dp, top = 32.dp, bottom = 14.dp)
        ) {
            Column {
                Text(
                    text = movie.title,
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = movie.director,
                    color = TextSecondary,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// ── Entradas Anticipadas
@Composable
fun EntradasAnticipadasSection() {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        // Encabezado
        Text(
            text = "ENTRADAS ANTICIPADAS",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Cards en grid 2 columnas
        val rows = entradasAnticipadas.chunked(2)
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { entrada ->
                    EntradaCard(
                        entrada = entrada,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

// ── Card de entrada anticipada
@Composable
fun EntradaCard(entrada: EntradaAnticipada, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceCard)
            .border(1.dp, DividerColor, RoundedCornerShape(14.dp))
            .padding(12.dp)
    ) {
        // Thumbnail + info
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Thumbnail
            Box(
                modifier = Modifier
                    .size(width = 52.dp, height = 68.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Brush.verticalGradient(entrada.thumbColors))
                    .border(1.dp, DividerColor, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Info
            Column(modifier = Modifier.weight(1f)) {
                // Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(entrada.badgeColor.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = entrada.badge,
                        color = entrada.badgeColor,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = entrada.title,
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Sala y fecha
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column {
                        Text(
                            text = "SALA",
                            color = TextSecondary,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = entrada.sala,
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column {
                        Text(
                            text = "FECHA",
                            color = TextSecondary,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = entrada.fecha,
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón de acción
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Brush.horizontalGradient(entrada.buttonColors))
                .border(
                    1.dp,
                    entrada.badgeColor.copy(alpha = 0.4f),
                    RoundedCornerShape(8.dp)
                )
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = entrada.badgeColor,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = entrada.buttonLabel,
                    color = entrada.badgeColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.8.sp
                )
            }
        }
    }
}