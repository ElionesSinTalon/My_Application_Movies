package mendoza.ruiz.myapplicationmovies.model

import androidx.compose.ui.graphics.Color
import mendoza.ruiz.myapplicationmovies.network.PeliculaResponse

// ── Paleta de colors
val defaultCardPalette = listOf(
    listOf(Color(0xFF0A0A1A), Color(0xFF1A0A2E)),
    listOf(Color(0xFF1A0F00), Color(0xFF2E1A00)),
    listOf(Color(0xFF001A1A), Color(0xFF002E2E)),
    listOf(Color(0xFF0F0F0F), Color(0xFF1A1A1A)),
    listOf(Color(0xFF0A1A00), Color(0xFF142E00)),
    listOf(Color(0xFF1A0000), Color(0xFF2E0000)),
    listOf(Color(0xFF0A0A1A), Color(0xFF10102E)),
    listOf(Color(0xFF1A0800), Color(0xFF0E0500)),
    listOf(Color(0xFF0A1208), Color(0xFF141E10)),
    listOf(Color(0xFF1A0030), Color(0xFF0A0018)),
)


 // Maps PeliculaResponse (API) to PeliculaDetalle (UI)

fun PeliculaResponse.toDetalle(
    index: Int = 0,
    palette: List<List<Color>> = defaultCardPalette
): PeliculaDetalle = PeliculaDetalle(
    id         = id,
    title      = title,
    overview   = overview,
    rating     = rating,
    category   = category,
    duration   = duration,
    year       = year,
    director   = director,
    posterUrl  = posterUrl,
    cardColors = palette[index % palette.size]
)

// Converts a list of PeliculaResponse to a list of PeliculaDetalle
fun List<PeliculaResponse>.toDetalleList(
    palette: List<List<Color>> = defaultCardPalette
): List<PeliculaDetalle> = mapIndexed { index, pelicula ->
    pelicula.toDetalle(index, palette)
}
