package mendoza.ruiz.myapplicationmovies.repository

import mendoza.ruiz.myapplicationmovies.network.PeliculaApiService
import mendoza.ruiz.myapplicationmovies.network.PeliculaRequest
import mendoza.ruiz.myapplicationmovies.network.PeliculaResponse
import mendoza.ruiz.myapplicationmovies.network.RetrofitInstance

class PeliculaRepository(
    private val api: PeliculaApiService = RetrofitInstance.api
) {

    // ── GET / peliculas - Todas las peliculas
    suspend fun getPeliculas(): Result<List<PeliculaResponse>> = runCatching {
        api.getPeliculas()
    }

    // GET - /Peliculas por id
    suspend fun getPeliculaById(id: Int): Result<PeliculaResponse> = runCatching {
        api.getPeliculaById(id)
    }

    //POST - Crear peliculas
    suspend fun createPelicula(pelicula: PeliculaRequest): Result<PeliculaResponse> = runCatching {
        api.createPelicula(pelicula)
    }


    //PUT - Actulizar
    suspend fun updatePelicula(id: Int, pelicula: PeliculaRequest): Result<PeliculaResponse> = runCatching {
        api.updatePelicula(id, pelicula)
    }

    //DELETE
    suspend fun deletePelicula(id: Int): Result<Unit> = runCatching {
        api.deletePelicula(id)
    }


}
