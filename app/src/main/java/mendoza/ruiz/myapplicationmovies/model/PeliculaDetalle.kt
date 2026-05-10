package mendoza.ruiz.myapplicationmovies.model

import androidx.compose.ui.graphics.Color

data class PeliculaDetalle(
    val id: Int,
    val title: String,
    val overview: String,
    val rating: Double,
    val category: String,
    val duration: Int,         // min
    val year: Int,
    val director: String = "",
    val posterUrl: String = "",
    val cardColors: List<Color> = listOf(Color(0xFF0A0A1A), Color(0xFF1A0A2E))
)
