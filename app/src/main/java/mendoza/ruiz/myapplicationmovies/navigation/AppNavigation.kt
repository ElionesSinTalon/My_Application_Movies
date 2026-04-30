import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mendoza.ruiz.myapplicationmovies.screens.ExploreScreen
import mendoza.ruiz.myapplicationmovies.screens.HomeScreen
import mendoza.ruiz.myapplicationmovies.screens.ProfileScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
        composable("explorer"){
            ExploreScreen()
        }
        composable("profile") {
            ProfileScreen()
        }
    }
}