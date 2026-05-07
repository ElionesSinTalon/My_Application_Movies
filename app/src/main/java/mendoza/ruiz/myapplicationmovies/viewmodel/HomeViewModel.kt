package mendoza.ruiz.myapplicationmovies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mendoza.ruiz.myapplicationmovies.model.Pelicula
import mendoza.ruiz.myapplicationmovies.repository.PeliculaRepository

data class HomeUiState(
    val featuredPeliculas: List<Pelicula> = emptyList(),
    val nowPlaying: List<Pelicula>        = emptyList(),
    val trending: List<Pelicula>          = emptyList(),
    val isLoading: Boolean                = false,
    val errorMessage: String?             = null
)

class HomeViewModel(
    private val repository: PeliculaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        cargarHome()
    }

    fun cargarHome() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            // Llamadas en paralelo
            val featuredResult = repository.getFeaturedPeliculas()
            val nowPlayingResult = repository.getNowPlaying()
            val trendingResult = repository.getTrending()

            _uiState.value = _uiState.value.copy(
                featuredPeliculas = featuredResult.getOrDefault(emptyList()),
                nowPlaying        = nowPlayingResult.getOrDefault(emptyList()),
                trending          = trendingResult.getOrDefault(emptyList()),
                isLoading         = false,
                errorMessage      = featuredResult.exceptionOrNull()?.message
            )
        }
    }
}
