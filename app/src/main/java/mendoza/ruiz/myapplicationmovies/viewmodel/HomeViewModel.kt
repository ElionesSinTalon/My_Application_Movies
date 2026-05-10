package mendoza.ruiz.myapplicationmovies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mendoza.ruiz.myapplicationmovies.repository.PeliculaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mendoza.ruiz.myapplicationmovies.model.PeliculaDetalle
import mendoza.ruiz.myapplicationmovies.model.toDetalleList

data class HomeUiState(
    val featuredPeliculas: List<PeliculaDetalle> = emptyList(), // isPremiere = true
    val nowPlaying: List<PeliculaDetalle>         = emptyList(), // primeras 6
    val trending: List<PeliculaDetalle>           = emptyList(), // top por rating
    val isLoading: Boolean                        = false,
    val errorMessage: String?                     = null
)

class HomeViewModel(
    private val repository: PeliculaRepository = PeliculaRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        cargarHome()
    }

    fun cargarHome() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            repository.getPeliculas().onSuccess { lista ->

                // featured → isPremiere = true
                val featured = lista
                    .filter { it.isPremiere }
                    .toDetalleList()

                // nowPlaying → primeras 6 películas
                val nowPlaying = lista
                    .take(6)
                    .toDetalleList()

                // trending → top 3 por rating más alto
                val trending = lista
                    .sortedByDescending { it.rating }
                    .take(3)
                    .toDetalleList()

                _uiState.value = _uiState.value.copy(
                    featuredPeliculas = featured,
                    nowPlaying        = nowPlaying,
                    trending          = trending,
                    isLoading         = false,
                    errorMessage      = null
                )

            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading    = false,
                    errorMessage = error.message
                )
            }
        }
    }
}