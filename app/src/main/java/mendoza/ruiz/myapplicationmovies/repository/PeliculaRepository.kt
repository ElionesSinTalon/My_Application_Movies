package mendoza.ruiz.myapplicationmovies.repository

import mendoza.ruiz.myapplicationmovies.model.Pelicula
import mendoza.ruiz.myapplicationmovies.network.PeliculaApiService

class PeliculaRepository(
    private val api: PeliculaApiService
) {

    // ── Home
    suspend fun getFeaturedPeliculas(): Result<List<Pelicula>> = runCatching {
        api.getFeaturedPeliculas()
    }

    suspend fun getNowPlaying(): Result<List<Pelicula>> = runCatching {
        api.getNowPlaying()
    }

    suspend fun getTrending(): Result<List<Pelicula>> = runCatching {
        api.getTrending()
    }

    // ── Explore
    suspend fun getPeliculas(page: Int = 1, limit: Int = 20): Result<List<Pelicula>> = runCatching {
        api.getPeliculas(page, limit)
    }

    suspend fun getPeliculasByCategoria(category: String): Result<List<Pelicula>> = runCatching {
        api.getPeliculasByCategoria(category)
    }

    // ── Premiere
    suspend fun getUpcoming(): Result<List<Pelicula>> = runCatching {
        api.getUpcoming()
    }

    // ── Detalle
    suspend fun getPeliculaById(id: Int): Result<Pelicula> = runCatching {
        api.getPeliculaById(id)
    }
}
