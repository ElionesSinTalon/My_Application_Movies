package mendoza.ruiz.myapplicationmovies.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitInstance {
    private const val BASE_URL = "https://fastapi-peliculas-genf.onrender.com/"

    val api: PeliculaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PeliculaApiService::class.java)
    }
}