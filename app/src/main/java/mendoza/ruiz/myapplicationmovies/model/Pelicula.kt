package mendoza.ruiz.myapplicationmovies.model

import java.util.Date

data class Pelicula(
    val id: Int,
    val title: String,
    val overview: String,
    val year: Int,
    val rating: Double,
    val category: String,
    val director: String,
    val duration: Int, //en min
    val format: String, //Imax, 4DX, Dolby, etc
    val isPremiere: Boolean,//true = proximo estreno
    val releaseDate: Date //12 oct, 25 nov, etc
)
