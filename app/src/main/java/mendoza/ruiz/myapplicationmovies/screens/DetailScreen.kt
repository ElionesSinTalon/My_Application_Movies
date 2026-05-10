package mendoza.ruiz.myapplicationmovies.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mendoza.ruiz.myapplicationmovies.model.PeliculaDetalle
import java.util.Locale

// ── Colors
private val Background    = Color(0xFF0D0D12)
private val SurfaceCard   = Color(0xFF16161F)
private val AccentCyan    = Color(0xFF00E5FF)
private val AccentPink    = Color(0xFFE040FB)
private val TextPrimary   = Color(0xFFFFFFFF)
private val TextSecondary = Color(0xFFB0B0C0)
private val DividerColor  = Color(0xFF2A2A3A)
private val StarYellow    = Color(0xFFFFC107)

// ── Detail Screen
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    pelicula: PeliculaDetalle,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        // Hero con poster
        HeroDetail(pelicula = pelicula, onBack = onBack)

        Spacer(modifier = Modifier.height(24.dp))

        // Info principal
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {

            // Título
            Text(
                text = pelicula.title,
                color = TextPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Rating + género + duración
            MetadataRow(pelicula = pelicula)

            Spacer(modifier = Modifier.height(20.dp))

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(DividerColor)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Sinopsis
            Text(
                text = "Sinopsis",
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = pelicula.overview,
                color = TextSecondary,
                fontSize = 14.sp,
                lineHeight = 22.sp,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Chips de info adicional
            InfoChipsRow(pelicula = pelicula)

            if (pelicula.director.isNotBlank()) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Director: ${pelicula.director}",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Ver ahora
            WatchButton()

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// ── Hero con poster y botón back
@Composable
fun HeroDetail(pelicula: PeliculaDetalle, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(340.dp)
    ) {
        // Poster (cuadro de color)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(pelicula.cardColors))
        ) {
            // Luz decorativa central
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color(0x30FFFFFF), Color.Transparent)
                        )
                    )
            )
        }

        // Degradado inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Background)
                    )
                )
        )

        // Botón Back
        Box(
            modifier = Modifier
                .padding(top = 48.dp, start = 16.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xAA0D0D12))
                .border(1.dp, DividerColor, CircleShape)
                .clickable { onBack() }
                .align(Alignment.TopStart),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Regresar",
                tint = TextPrimary,
                modifier = Modifier.size(20.dp)
            )
        }

        // Badge de rating sobre el poster
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 48.dp, end = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xAA0D0D12))
                .border(1.dp, DividerColor, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = StarYellow,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = String.format(Locale.US, "%.1f", pelicula.rating),
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

// ── Fila de metadata
@Composable
fun MetadataRow(pelicula: PeliculaDetalle) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Año
        Text(
            text = "${pelicula.year}",
            color = TextSecondary,
            fontSize = 13.sp
        )

        Dot()

        // Duración
        val horas   = pelicula.duration / 60
        val minutos = pelicula.duration % 60
        Text(
            text = if (horas > 0) "${horas}h ${minutos}m" else "${minutos}m",
            color = TextSecondary,
            fontSize = 13.sp
        )

        Dot()
        // Género con badge
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(AccentCyan.copy(alpha = 0.12f))
                .border(1.dp, AccentCyan.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Text(
                text = pelicula.category,
                color = AccentCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun Dot() {
    Box(
        modifier = Modifier
            .size(4.dp)
            .clip(CircleShape)
            .background(TextSecondary.copy(alpha = 0.5f))
    )
}

// ── Chips de info adicional
@Composable
fun InfoChipsRow(pelicula: PeliculaDetalle) {
    val items = listOf(
        Pair("GÉNERO",    pelicula.category),
        Pair("DURACIÓN",  "${pelicula.duration} min"),
        Pair("AÑO",       "${pelicula.year}"),
        Pair("RATING",    String.format(Locale.US, "%.1f/10", pelicula.rating)),
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items.forEach { (label, value) ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceCard)
                    .border(1.dp, DividerColor, RoundedCornerShape(12.dp))
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = value,
                        color = TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = label,
                        color = TextSecondary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

// ── Botón Ver ahora
@Composable
fun WatchButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.horizontalGradient(listOf(AccentCyan, AccentPink))
            )
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Background,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "VER AHORA",
                color = Background,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
        }
    }
}