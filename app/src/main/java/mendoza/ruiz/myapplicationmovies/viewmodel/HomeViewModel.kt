package mendoza.ruiz.myapplicationmovies.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mendoza.ruiz.myapplicationmovies.repository.PeliculaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mendoza.ruiz.myapplicationmovies.screens.PeliculaDetalle

data class HomeUiState(
    val featuredPeliculas: List<PeliculaDetalle> = emptyList(), // isPremiere = true
    val nowPlaying: List<PeliculaDetalle>         = emptyList(), // primeras6
    val trending: List<PeliculaDetalle>           = emptyList(), // top por rating
    val isLoading: Boolean                        = false,
    val errorMessage: String?                     = null
)
//val nowPlaying = lista.take(6).toDetalleList()
//val trending   = lista.sortedByDescending { it.rating }.take(3).toDetalleList()
//val featured   = lista.filter { it.isPremiere }.toDetalleList()
class HomeViewModel(
    private val repository: PeliculaRepository = PeliculaRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private val cardPalette = listOf(
        listOf(Color(0xFF0A1A1A), Color(0xFF003333)),
        listOf(Color(0xFF1A0000), Color(0xFF330000)),
        listOf(Color(0xFF000D1A), Color(0xFF001A33)),
        listOf(Color(0xFF1A1000), Color(0xFF332000)),
        listOf(Color(0xFF0D001A), Color(0xFF1A0033)),
        listOf(Color(0xFF001A0A), Color(0xFF00331A)),
    )

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
                    .mapIndexed { i, p -> p.toDetalle(i, cardPalette) }

                // nowPlaying → primeras 6 películas
                val nowPlaying = lista
                    .take(6)
                    .mapIndexed { i, p -> p.toDetalle(i, cardPalette) }

                // trending → top 3 por rating más alto
                val trending = lista
                    .sortedByDescending { it.rating }
                    .take(3)
                    .mapIndexed { i, p -> p.toDetalle(i, cardPalette) }

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