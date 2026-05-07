package mendoza.ruiz.myapplicationmovies.model

data class Pelicula(
    val id: Int,
    val title: String,
    val overview: String,
    val year: Int,
    val rating: Double,
    val category: String,
    val director: String,
    val duration: Int, //en minutos
    val format: String, //Imax, 4DX, Dolby, etc
    val isPremiereM: Boolean,//true = proximo estreno
    val releaseDate: String //12 oct, 25 nov, etc
)
