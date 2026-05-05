package mendoza.ruiz.myapplicationmovies.network

import mendoza.ruiz.myapplicationmovies.Model.Pelicula
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeliculaApiService {

    @GET("peliculas")
    suspend fun getPeliculas(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): List<Pelicula>

    // Películas por categoría
    @GET("peliculas")
    suspend fun getPeliculasByCategoria(
        @Query("category") category: String
    ): List<Pelicula>

    // Películas destacadas para el hero
    @GET("peliculas/featured")
    suspend fun getFeaturedPeliculas(): List<Pelicula>

    // Películas en cartelera (Now Playing)
    @GET("peliculas/now-playing")
    suspend fun getNowPlaying(): List<Pelicula>

    // Próximos estrenos
    @GET("peliculas/upcoming")
    suspend fun getUpcoming(): List<Pelicula>

    // Tendencias
    @GET("peliculas/trending")
    suspend fun getTrending(): List<Pelicula>

    // Detalle de una película
    @GET("peliculas/{id}")
    suspend fun getPeliculaById(
        @Path("id") id: Int
    ): Pelicula
}