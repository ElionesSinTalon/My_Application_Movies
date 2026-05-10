package mendoza.ruiz.myapplicationmovies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mendoza.ruiz.myapplicationmovies.repository.PeliculaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mendoza.ruiz.myapplicationmovies.model.PeliculaDetalle
import mendoza.ruiz.myapplicationmovies.model.toDetalleList


data class ExploreUiState(
    val peliculas: List<PeliculaDetalle>         = emptyList(),
    val filteredPeliculas: List<PeliculaDetalle> = emptyList(),
    val categorias: List<String>                 = listOf("ALL"),
    val selectedCategoria: String                = "ALL",
    val searchQuery: String                      = "",
    val isLoading: Boolean                       = false,
    val errorMessage: String?                    = null
)

class ExploreViewModel(
    private val repository: PeliculaRepository = PeliculaRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExploreUiState())
    val uiState: StateFlow<ExploreUiState> = _uiState

    init {
        cargarPeliculas()
    }

    fun cargarPeliculas() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            repository.getPeliculas().onSuccess { lista ->
                val detalle    = lista.toDetalleList()
                val categorias = listOf("ALL") + lista.map { it.category }.distinct()
                _uiState.value = _uiState.value.copy(
                    peliculas         = detalle,
                    filteredPeliculas = detalle,
                    categorias        = categorias,
                    isLoading         = false
                )
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading    = false,
                    errorMessage = error.message
                )
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        aplicarFiltros()
    }

    fun onCategoriaSelected(categoria: String) {
        _uiState.value = _uiState.value.copy(selectedCategoria = categoria)
        aplicarFiltros()
    }

    private fun aplicarFiltros() {
        val query     = _uiState.value.searchQuery
        val categoria = _uiState.value.selectedCategoria
        val filtered  = _uiState.value.peliculas.filter { p ->
            val matchesCategoria = categoria == "ALL" || p.category == categoria
            val matchesSearch    = query.isBlank() ||
                    p.title.contains(query, ignoreCase = true) ||
                    p.category.contains(query, ignoreCase = true)
            matchesCategoria && matchesSearch
        }
        _uiState.value = _uiState.value.copy(filteredPeliculas = filtered)
    }
}
