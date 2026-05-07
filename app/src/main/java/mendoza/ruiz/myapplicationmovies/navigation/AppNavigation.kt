package mendoza.ruiz.myapplicationmovies.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mendoza.ruiz.myapplicationmovies.screens.DetailScreen
import mendoza.ruiz.myapplicationmovies.screens.ExplorerScreen
import mendoza.ruiz.myapplicationmovies.screens.HomeScreen
import mendoza.ruiz.myapplicationmovies.screens.PeliculaDetalle
import mendoza.ruiz.myapplicationmovies.screens.PremiereScreen
import mendoza.ruiz.myapplicationmovies.screens.ProfileScreen
import mendoza.ruiz.myapplicationmovies.ui.theme.BottomBar

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var peliculaSeleccionada by remember { mutableStateOf<PeliculaDetalle?>(null) }

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
                        navController.navigate("premiere"){
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true

                        }
                    }
                )
            }
        }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ){
            composable("home") {
                HomeScreen(
                    onPeliculaClick = { pelicula ->
                        peliculaSeleccionada = pelicula
                        navController.navigate("detail")
                    }
                )
            }
            composable("explorer") {
                ExplorerScreen(
                    onPeliculaClick = { pelicula ->
                        peliculaSeleccionada = pelicula
                        navController.navigate("detail")
                    }
                )
            }
            composable("premiere") {
                PremiereScreen(
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