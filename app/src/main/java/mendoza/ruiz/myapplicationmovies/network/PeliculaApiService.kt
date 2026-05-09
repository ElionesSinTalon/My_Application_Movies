package mendoza.ruiz.myapplicationmovies.network

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// Modelo de respuesta - Connection with Api
data class PeliculaResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("year") val year: Int,
    @SerializedName("rating") val rating: Double,
    @SerializedName("category") val category: String,
    @SerializedName("director") val director: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("format") val format: String,
    @SerializedName("isPremiere") val isPremiere: Boolean,
    @SerializedName("releaseDate") val releaseDate: String
)

// Modelo para crear / actualizar
data class PeliculaRequest(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("year") val year: Int,
    @SerializedName("rating") val rating: Double,
    @SerializedName("category") val category: String,
    @SerializedName("director") val director: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("format") val format: String,
    @SerializedName("isPremiere") val isPremiere: Boolean,
    @SerializedName("releaseDate") val releaseDate: String
)
// EndPoints
interface PeliculaApiService {
    @GET("peliculas")
    suspend fun getPeliculas(): List<PeliculaResponse>

    @GET("peliculas/{pelicula_id}")
    suspend fun getPeliculaById(
        @Path("pelicula_id") peliculaId: Int
    ): PeliculaResponse

    @POST("peliculas")
    suspend fun createPelicula(
        @Body pelicula: PeliculaRequest
    ): PeliculaResponse

    @PUT("peliculas/{pelicula_id}")
    suspend fun updatePelicula(
        @Path("pelicula_id") peliculaId: Int,
        @Body pelicula: PeliculaRequest
    ): PeliculaResponse

    @DELETE("peliculas/{pelicula_id}")
    suspend fun deletePelicula(
        @Path("pelicula_id") peliculaId: Int
    )
}
