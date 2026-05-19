package mendoza.ruiz.myapplicationmovies.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import mendoza.ruiz.myapplicationmovies.model.PeliculaDetalle
import mendoza.ruiz.myapplicationmovies.model.toDetalle
import mendoza.ruiz.myapplicationmovies.repository.PeliculaRepository
import mendoza.ruiz.myapplicationmovies.screens.DetailScreen
import mendoza.ruiz.myapplicationmovies.screens.ExplorerScreen
import mendoza.ruiz.myapplicationmovies.screens.HomeScreen
import mendoza.ruiz.myapplicationmovies.screens.PremiereScreen
import mendoza.ruiz.myapplicationmovies.screens.ProfileScreen
import mendoza.ruiz.myapplicationmovies.screens.components.QuickActionsBottomSheet
import mendoza.ruiz.myapplicationmovies.ui.theme.BottomBar
import mendoza.ruiz.myapplicationmovies.viewmodel.ExploreViewModel
import mendoza.ruiz.myapplicationmovies.viewmodel.HomeViewModel
import mendoza.ruiz.myapplicationmovies.viewmodel.PremiereViewModel
import mendoza.ruiz.myapplicationmovies.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var peliculaSeleccionada by remember { mutableStateOf<PeliculaDetalle?>(null) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val repository = remember { PeliculaRepository() }
    val factory = remember { ViewModelFactory(repository) }

    var showBottomSheet by remember { mutableStateOf(false) }

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    //Oculta el BottomBar en la pantalla de detalle
    val showBottomBar = currentRoute != "detail"

    val selectedIndex = when (currentRoute){
        "home" -> 0
        "explorer" -> 1
        "premiere" -> 2
        "profile" -> 3
        else -> 0
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    selectedIndex = selectedIndex,
                    onItemSelected = { index ->
                        val route = when (index) {
                            0 -> "home"
                            1 -> "explorer"
                            2 -> "premiere"
                            3 -> "profile"
                            else -> "home"
                        }
                        navController.navigate(route) {
                            //Evita apilar pantallas al presionar tabs
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onCenterClick = {
                        showBottomSheet = true
                    }
                )
            }
        }
    ){ innerPadding ->
        if (showBottomSheet) {
            QuickActionsBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                onAddToList = {
                    showBottomSheet = false
                    scope.launch {
                        snackbarHostState.showSnackbar("Added to My List")
                    }
                },
                onRandomMovie = {
                    showBottomSheet = false
                    scope.launch {
                        repository.getPeliculas().onSuccess { lista ->
                            if (lista.isNotEmpty()) {
                                val randomMovie = lista.random().toDetalle()
                                peliculaSeleccionada = randomMovie
                                navController.navigate("detail")
                            }
                        }.onFailure {
                            snackbarHostState.showSnackbar("Error fetching movies")
                        }
                    }
                },
                onCreateCollection = {
                    showBottomSheet = false
                    scope.launch {
                        snackbarHostState.showSnackbar("Collection created successfully")
                    }
                },
                onTopRated = {
                    showBottomSheet = false
                    navController.navigate("explorer")
                }
            )
        }
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                val viewModel: HomeViewModel = viewModel(factory = factory)
                HomeScreen(
                    viewModel = viewModel,
                    onPeliculaClick = { pelicula ->
                        peliculaSeleccionada = pelicula
                        navController.navigate("detail")
                    }
                )
            }
            composable("explorer") {
                val viewModel: ExploreViewModel = viewModel(factory = factory)
                ExplorerScreen(
                    viewModel = viewModel,
                    onPeliculaClick = { pelicula ->
                        peliculaSeleccionada = pelicula
                        navController.navigate("detail")
                    }
                )
            }
            composable("premiere") {
                val viewModel: PremiereViewModel = viewModel(factory = factory)
                PremiereScreen(
                    viewModel = viewModel,
                    onPeliculaClick = { pelicula ->
                        peliculaSeleccionada = pelicula
                        navController.navigate("detail")
                    }
                )
            }
            composable("profile") {
                ProfileScreen()
            }
            composable("detail") {
                peliculaSeleccionada?.let { pelicula ->
                    DetailScreen(
                        pelicula = pelicula,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}