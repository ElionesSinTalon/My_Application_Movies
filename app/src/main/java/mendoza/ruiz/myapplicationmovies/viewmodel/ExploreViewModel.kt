package mendoza.ruiz.myapplicationmovies.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mendoza.ruiz.myapplicationmovies.model.Pelicula
import mendoza.ruiz.myapplicationmovies.repository.PeliculaRepository
import kotlin.collections.map

data class ExploreUiState(
    val peliculas: List<Pelicula>         = emptyList(),
    val filteredPeliculas: List<Pelicula> = emptyList(),
    val categorias: List<String>          = listOf("ALL"),
    val selectedCategoria: String         = "ALL",
    val searchQuery: String               = "",
    val currentPage: Int                  = 1,
    val hasMore: Boolean                  = true,
    val isLoading: Boolean                = false,
    val errorMessage: String?             = null
)

class ExploreViewModel(
    private val repository: PeliculaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExploreUiState())
    val uiState: StateFlow<ExploreUiState> = _uiState

    init {
        cargarPeliculas()
    }

    fun cargarPeliculas() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            val result = repository.getPeliculas(page = 1)

            result.onSuccess { lista ->
                val categorias = listOf("ALL") + lista.map { it.category }.distinct()
                _uiState.value = _uiState.value.copy(
                    peliculas         = lista,
                    filteredPeliculas = lista,
                    categorias        = categorias,
                    currentPage       = 1,
                    hasMore           = lista.size >= 20,
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

    fun cargarMas() {
        if (_uiState.value.isLoading || !_uiState.value.hasMore) return
        viewModelScope.launch {
            val nextPage = _uiState.value.currentPage + 1
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = repository.getPeliculas(page = nextPage)

            result.onSuccess { nuevas ->
                val todas = _uiState.value.peliculas + nuevas
                _uiState.value = _uiState.value.copy(
                    peliculas         = todas,
                    filteredPeliculas = aplicarFiltros(todas),
                    currentPage       = nextPage,
                    hasMore           = nuevas.size >= 20,
                    isLoading         = false
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        aplicarYActualizar()
    }

    fun onCategoriaSelected(categoria: String) {
        _uiState.value = _uiState.value.copy(selectedCategoria = categoria)
        aplicarYActualizar()
    }

    private fun aplicarYActualizar() {
        val filtered = aplicarFiltros(_uiState.value.peliculas)
        _uiState.value = _uiState.value.copy(filteredPeliculas = filtered)
    }

    private fun aplicarFiltros(lista: List<Pelicula>): List<Pelicula> {
        val query     = _uiState.value.searchQuery
        val categoria = _uiState.value.selectedCategoria
        return lista.filter { pelicula ->
            val matchesCategoria = categoria == "ALL" || pelicula.category == categoria
            val matchesSearch    = query.isBlank() ||
                    pelicula.title.contains(query, ignoreCase = true) ||
                    pelicula.category.contains(query, ignoreCase = true)
            matchesCategoria && matchesSearch
        }
    }
}
