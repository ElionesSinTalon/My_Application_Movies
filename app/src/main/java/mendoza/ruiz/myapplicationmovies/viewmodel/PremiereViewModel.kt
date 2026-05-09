package mendoza.ruiz.myapplicationmovies.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mendoza.ruiz.myapplicationmovies.repository.PeliculaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mendoza.ruiz.myapplicationmovies.screens.PeliculaDetalle

data class PremiereUiState(
    val featured: List<PeliculaDetalle>  = emptyList(),  // isPremiere = true
    val upcoming: List<PeliculaDetalle>  = emptyList(),  // isPremiere = true, ordenadas por fecha
    val isLoading: Boolean               = false,
    val errorMessage: String?            = null
)
//val featured = lista.filter { it.isPremiere }.toDetalleList()
//val upcoming = lista.filter { it.isPremiere }.sortedBy { it.releaseDate }.toDetalleList()
class PremiereViewModel(
    private val repository: PeliculaRepository = PeliculaRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(PremiereUiState())
    val uiState: StateFlow<PremiereUiState> = _uiState

    // Paleta de colors para cards
    private val cardPalette = listOf(
        listOf(Color(0xFF1A0030), Color(0xFF0A0018)),
        listOf(Color(0xFF0A0A14), Color(0xFF14141E)),
        listOf(Color(0xFF1A0800), Color(0xFF0E0500)),
        listOf(Color(0xFF0A1208), Color(0xFF141E10)),
        listOf(Color(0xFF0A0A1A), Color(0xFF10102E)),
    )

    init {
        cargarPremiere()
    }

    fun cargarPremiere() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            repository.getPeliculas().onSuccess { lista ->

                // featured → películas con isPremiere = true
                val featured = lista
                    .filter { it.isPremiere }
                    .mapIndexed { i, p -> p.toDetalle(i, cardPalette) }

                // upcoming → todas ordenadas por releaseDate (más próximas primero)
                val upcoming = lista
                    .filter { it.isPremiere }
                    .sortedBy { it.releaseDate }
                    .mapIndexed { i, p -> p.toDetalle(i, cardPalette) }

                _uiState.value = _uiState.value.copy(
                    featured     = featured,
                    upcoming     = upcoming,
                    isLoading    = false,
                    errorMessage = null
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