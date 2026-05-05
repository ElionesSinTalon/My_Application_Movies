package mendoza.ruiz.myapplicationmovies.ui.theme

//import androidx.compose.material.icons.filled.LibraryBooks
//import androidx.compose.material.icons.filled.Movie
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Colors
private val NavBackground = Color(0xFF12121C)
private val NavBorder     = Color(0xFF2A2A3A)
private val AccentCyan    = Color(0xFF00E5FF)
//private val AccentPink    = Color(0xFFE040FB)
//private val TextActive    = Color(0xFFFFFFFF)
private val TextInactive  = Color(0xFF6B6B80)

// ── Modelo de ítem
data class NavItem(
    val label: String,
    val icon: ImageVector
)

private val navItems = listOf(
    NavItem("HOME",    Icons.Default.Home),
    NavItem("MOVIES",  Icons.Default.PlayArrow),
    NavItem("PREMIERE", Icons.Default.Search),
    NavItem("PROFILE", Icons.Default.AccountCircle),
)

// ── Bottom Navigation Bar
@Composable
fun BottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    onCenterClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(NavBackground)
            .border(
                width = 2.dp,
                color = NavBorder,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Primeros 2 ítems (HOME, MOVIES)
            navItems.take(2).forEachIndexed { index, item ->
                NavBarItem(
                    item = item,
                    isSelected = selectedIndex == index,
                    onClick = { onItemSelected(index) },
                    modifier = Modifier.weight(1f)
                )
            }

            // Botón central "+" con degradado
            CenterButton(
                onClick = onCenterClick,
                modifier = Modifier.weight(1f)
            )

            // Últimos 2 ítems (LIBRARY, PROFILE)
            navItems.takeLast(2).forEachIndexed { index, item ->
                val realIndex = index + 2
                NavBarItem(
                    item = item,
                    isSelected = selectedIndex == realIndex,
                    onClick = { onItemSelected(realIndex) },
                    modifier = Modifier.weight(1f),
                    )
            }
        }
    }
}

// ── Ítem normal
@Composable
fun NavBarItem(
    item: NavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDashed: Boolean = false
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .then(
                    if (isDashed)
                        Modifier.border(
                            width = 1.5.dp,
                            color = AccentCyan.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(8.dp)
                        )
                    else Modifier
                )
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = if (isSelected) AccentCyan else TextInactive,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.label,
            color = if (isSelected) AccentCyan else TextInactive,
            fontSize = 9.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            letterSpacing = 0.8.sp
        )
    }
}

// ── Botón central "+"
@Composable
fun CenterButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(52.dp)
                .shadow(12.dp, CircleShape)
                .clip(CircleShape)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFCC44FF),
                            Color(0xFF7744FF),
                            Color(0xFF44AAFF)
                        )
                    )
                )
                .clickable(onClick = onClick)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}