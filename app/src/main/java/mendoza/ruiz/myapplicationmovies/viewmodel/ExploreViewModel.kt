package mendoza.ruiz.myapplicationmovies.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mendoza.ruiz.myapplicationmovies.network.PeliculaResponse
import mendoza.ruiz.myapplicationmovies.repository.PeliculaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mendoza.ruiz.myapplicationmovies.screens.PeliculaDetalle

data class ExploreUiState(
    val peliculas: List<PeliculaDetalle>         = emptyList(),
    val filteredPeliculas: List<PeliculaDetalle> = emptyList(),
    val categorias: List<String>                 = listOf("ALL"),
    val selectedCategoria: String                = "ALL",
    val searchQuery: String                      = "",
    val isLoading: Boolean                       = false,
    val errorMessage: String?                    = null
)
//val detalle = lista.toDetalleList()
class ExploreViewModel(
    private val repository: PeliculaRepository = PeliculaRepository(
        api = TODO()
    )
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExploreUiState())
    val uiState: StateFlow<ExploreUiState> = _uiState

    // Paleta de colors para los cards
    private val cardPalette = listOf(
        listOf(Color(0xFF0A0A1A), Color(0xFF1A0A2E)),
        listOf(Color(0xFF1A0F00), Color(0xFF2E1A00)),
        listOf(Color(0xFF001A1A), Color(0xFF002E2E)),
        listOf(Color(0xFF0F0F0F), Color(0xFF1A1A1A)),
        listOf(Color(0xFF0A1A00), Color(0xFF142E00)),
        listOf(Color(0xFF1A0000), Color(0xFF2E0000)),
    )

    init {
        cargarPeliculas()
    }

    fun cargarPeliculas() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            repository.getPeliculas().onSuccess { lista ->
                val detalle    = lista.mapIndexed { i, p -> p.toDetalle(i, cardPalette) }
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

// ── Extensión: PeliculaResponse → PeliculaDetalle
fun PeliculaResponse.toDetalle(
    index: Int,
    palette: List<List<Color>>
): PeliculaDetalle = PeliculaDetalle(
    id         = id,
    title      = title,
    overview   = overview,
    rating     = rating,
    category   = category,
    duration   = duration,
    year       = year,
    director   = director,
    cardColors = palette[index % palette.size]
)