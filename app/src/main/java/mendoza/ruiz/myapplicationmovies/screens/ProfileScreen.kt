package mendoza.ruiz.myapplicationmovies.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Colors
private val Background    = Color(0xFF0D0D12)
private val SurfaceCard   = Color(0xFF16161F)
private val AccentCyan    = Color(0xFF00E5FF)
private val AccentPink    = Color(0xFFE040FB)
private val GoldColor     = Color(0xFFFFD700)
private val TextPrimary   = Color(0xFFFFFFFF)
private val TextSecondary = Color(0xFFB0B0C0)
private val DividerColor  = Color(0xFF2A2A3A)

// ── ProfileScreen
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Avatar + badge
        AvatarSection()

        Spacer(modifier = Modifier.height(20.dp))

        // Nombre
        Text(
            text = "xXTilinInsanoXx",
            color = TextPrimary,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Cita
        Text(
            text = "\"Me atrapaste esto si es cine.\"",
            color = TextSecondary,
            fontSize = 13.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tarjeta de estadísticas
        StatsCard()

        Spacer(modifier = Modifier.height(28.dp))

        // Botón Edit Profile
        EditProfileButton()

        Spacer(modifier = Modifier.height(36.dp))

        // Mi Lista
        MiListaSection()

        Spacer(modifier = Modifier.height(36.dp))

        // My Collections
        MyCollectionsSection()

        Spacer(modifier = Modifier.height(40.dp))
    }
}

// ── Avatar con badge GOLD MEMBER
@Composable
fun AvatarSection() {
    Box(contentAlignment = Alignment.BottomCenter) {
        // Círculo exterior con borde degradado
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(
                    Brush.verticalGradient(
                        listOf(AccentCyan.copy(alpha = 0.4f), AccentPink.copy(alpha = 0.4f))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // Avatar placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF1E1E2E), Color(0xFF2A2A3A))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Silueta simple con formas
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Cabeza
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF3A3A4A))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    // Cuerpo
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(28.dp)
                            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                            .background(Color(0xFF3A3A4A))
                    )
                }
            }
        }

        // Badge GOLD MEMBER
        Box(
            modifier = Modifier
                .offset(y = 10.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFFFFAA00), GoldColor)
                    )
                )
                .padding(horizontal = 10.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "GOLD\nMEMBER",
                color = Color(0xFF1A0A00),
                fontSize = 8.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                lineHeight = 11.sp,
                letterSpacing = 0.5.sp
            )
        }
    }
}

// ── Tarjeta de estadísticas
@Composable
fun StatsCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceCard)
            .border(1.dp, DividerColor, RoundedCornerShape(20.dp))
            .padding(vertical = 28.dp, horizontal = 16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Fila superior: Movies Watched + Total Hours
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(value = "142", label = "MOVIES WATCHED")

                // Divisor vertical
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(50.dp)
                        .background(DividerColor)
                )

                StatItem(value = "384", label = "TOTAL HOURS")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Divisor horizontal
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(1.dp)
                    .background(DividerColor)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Fila inferior: Curated Lists (centrado)
            StatItem(value = "12", label = "CURATED LISTS")
        }
    }
}

// ── Stat individual
@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            color = TextPrimary,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = TextSecondary,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp
        )
    }
}

// ── Botón Edit Profile
@Composable
fun EditProfileButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(AccentCyan, AccentPink)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { },
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(14.dp),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = Background,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "EDIT PROFILE",
                color = Background,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
        }
    }
}

// ── Datos Mi Lista
data class ListaItem(
    val title: String,
    val date: String,
    val year: Int,
    val rating: Float,
    val cardColors: List<Color>,
    val isHighlighted: Boolean = false
)

private val miListaItems = listOf(
    ListaItem("L'Ombre Blanche", "OCT 12", 1954, 6.9f,
        listOf(Color(0xFF0A0A14), Color(0xFF14141E))),
    ListaItem("Midnight Tokyo",  "OCT 10", 2023, 9.2f,
        listOf(Color(0xFF0A1018), Color(0xFF101828)), isHighlighted = true),
    ListaItem("Iron Recall",     "OCT 08", 2021, 7.5f,
        listOf(Color(0xFF101010), Color(0xFF1A1A1A))),
    ListaItem("Safe Signal",     "OCT 05", 2019, 8.1f,
        listOf(Color(0xFF0A0A10), Color(0xFF141420))),
)

private val listaTabs = listOf("All", "Movies", "Recent")

// ── Mi Lista Section ───────────────────────────────────────────────────────────
@Composable
fun MiListaSection() {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth()) {

        // Encabezado
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My List",
                color = TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${miListaItems.size * 6} titles",
                color = TextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Tabs filtro
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            listaTabs.forEachIndexed { index, tab ->
                val isActive = selectedTab == index
                Column(
                    modifier = Modifier.clickable { selectedTab = index },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = tab,
                            color = if (isActive) TextPrimary else TextSecondary,
                            fontSize = 13.sp,
                            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
                        )
                        if (tab == "Recent") {
                            Spacer(modifier = Modifier.width(3.dp))
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = TextSecondary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                    if (isActive) {
                        Spacer(modifier = Modifier.height(3.dp))
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(2.dp)
                                .background(
                                    Brush.horizontalGradient(listOf(AccentCyan, AccentPink)),
                                    RoundedCornerShape(1.dp)
                                )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Grid de cards — usamos Column+Row para evitar scroll anidado
        val rows = miListaItems.chunked(2)
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { item ->
                    ListaCard(item = item, modifier = Modifier.weight(1f))
                }
                // Si la fila tiene solo 1 ítem, rellena el espacio
                if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

// ── Card individual de Mi Lista
@Composable
fun ListaCard(item: ListaItem, modifier: Modifier = Modifier) {
    var showActions by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.75f)
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.verticalGradient(item.cardColors))
                .border(
                    width = if (item.isHighlighted) 1.5.dp else 1.dp,
                    brush = if (item.isHighlighted)
                        Brush.verticalGradient(listOf(AccentCyan, AccentPink))
                    else
                        Brush.verticalGradient(listOf(Color(0xFF252535), Color(0xFF252535))),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { showActions = !showActions }
        ) {
            // Badge rating
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (item.isHighlighted)
                            Brush.horizontalGradient(listOf(AccentCyan, AccentPink))
                        else
                            Brush.horizontalGradient(listOf(Color(0xCC0D0D12), Color(0xCC0D0D12)))
                    )
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (item.isHighlighted) Background else Color(0xFFFFC107),
                        modifier = Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = item.rating.toString(),
                        color = if (item.isHighlighted) Background else TextPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Botones Play / Delete al tocar
            if (showActions) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xAA000000)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        // Play
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(AccentCyan)
                                .clickable { showActions = false },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = Background,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        // Delete
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE53935))
                                .clickable { showActions = false },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = TextPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.title,
            color = TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = "${item.date} · ${item.year}",
            color = TextSecondary,
            fontSize = 11.sp
        )
    }
}

// ── Datos My Collections
data class Collection(
    val name: String,
    val type: String,          // "PUBLIC COLLECTION" / "PRIVATE COLLECTION"
    val count: Int,
    val isPrivate: Boolean,
    val thumbColors: List<List<Color>>   // colores para los thumbnails apilados
)

private val collections = listOf(
    Collection(
        name = "Interestellar",
        type = "PUBLIC COLLECTION",
        count = 14,
        isPrivate = false,
        thumbColors = listOf(
            listOf(Color(0xFF0A0A1A), Color(0xFF14142A)),
            listOf(Color(0xFF1A0A0A), Color(0xFF2A1414)),
        )
    ),
    Collection(
        name = "Modern Thrillers",
        type = "PRIVATE COLLECTION",
        count = 5,
        isPrivate = true,
        thumbColors = listOf(
            listOf(Color(0xFF0A1018), Color(0xFF141828)),
        )
    ),
)

// ── My Collections Section
@Composable
fun MyCollectionsSection() {
    Column(modifier = Modifier.fillMaxWidth()) {

        // Título
        Text(
            text = "My Collections",
            color = TextPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Cards de colecciones
        collections.forEach { collection ->
            CollectionCard(collection = collection)
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Botón New Collection
        NewCollectionButton()
    }
}

// ── Collection Card
@Composable
fun CollectionCard(collection: Collection) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceCard)
            .border(1.dp, DividerColor, RoundedCornerShape(16.dp))
            .clickable { }
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Thumbnails apilados
                StackedThumbnails(
                    colors = collection.thumbColors,
                    count = collection.count
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = collection.name,
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = collection.type,
                color = TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp
            )
        }
    }
}

// ── Thumbnails apilados
@Composable
fun StackedThumbnails(colors: List<List<Color>>, count: Int) {
    Box(modifier = Modifier.height(48.dp)) {
        colors.forEachIndexed { index, colorPair ->
            Box(
                modifier = Modifier
                    .offset(x = (index * 30).dp)
                    .size(width = 48.dp, height = 48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Brush.verticalGradient(colorPair))
                    .border(1.dp, DividerColor, RoundedCornerShape(8.dp))
            )
        }
        // Badge con contador "+N"
        Box(
            modifier = Modifier
                .offset(x = (colors.size * 30).dp)
                .size(width = 48.dp, height = 48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF252535))
                .border(1.dp, DividerColor, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+$count",
                color = AccentPink,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

// ── Botón New Collection
@Composable
fun NewCollectionButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF12121C))
            .border(
                width = 1.dp,
                color = DividerColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { }
            .padding(vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Ícono "+"  circular con borde
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.5.dp,
                        brush = Brush.verticalGradient(listOf(AccentCyan, AccentPink)),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+",
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Light
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "New Collection",
                color = TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
        }
    }
}