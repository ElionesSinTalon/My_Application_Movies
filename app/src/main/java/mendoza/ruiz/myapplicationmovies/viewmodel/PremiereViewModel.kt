package mendoza.ruiz.myapplicationmovies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mendoza.ruiz.myapplicationmovies.Model.Pelicula
import mendoza.ruiz.myapplicationmovies.repository.PeliculaRepository

data class PremiereUiState(
    val featured: List<Pelicula>  = emptyList(),
    val upcoming: List<Pelicula>  = emptyList(),
    val isLoading: Boolean        = false,
    val errorMessage: String?     = null
)

class PremiereViewModel(
    private val repository: PeliculaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PremiereUiState())
    val uiState: StateFlow<PremiereUiState> = _uiState

    init {
        cargarPremiere()
    }

    fun cargarPremiere() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            val featuredResult = repository.getFeaturedPeliculas()
            val upcomingResult = repository.getUpcoming()

            _uiState.value = _uiState.value.copy(
                featured     = featuredResult.getOrDefault(emptyList()),
                upcoming     = upcomingResult.getOrDefault(emptyList()),
                isLoading    = false,
                errorMessage = upcomingResult.exceptionOrNull()?.message
            )
        }
    }
}