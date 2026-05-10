package mendoza.ruiz.myapplicationmovies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mendoza.ruiz.myapplicationmovies.repository.PeliculaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mendoza.ruiz.myapplicationmovies.model.PeliculaDetalle
import mendoza.ruiz.myapplicationmovies.model.toDetalleList

data class PremiereUiState(
    val featured: List<PeliculaDetalle>  = emptyList(),  // isPremiere = true
    val upcoming: List<PeliculaDetalle>  = emptyList(),  // isPremiere = true, ordenadas por fecha
    val isLoading: Boolean               = false,
    val errorMessage: String?            = null
)

class PremiereViewModel(
    private val repository: PeliculaRepository = PeliculaRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(PremiereUiState())
    val uiState: StateFlow<PremiereUiState> = _uiState

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
                    .toDetalleList()

                // upcoming → todas ordenadas por releaseDate (más próximas primero)
                val upcoming = lista
                    .filter { it.isPremiere }
                    .sortedBy { it.releaseDate }
                    .toDetalleList()

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