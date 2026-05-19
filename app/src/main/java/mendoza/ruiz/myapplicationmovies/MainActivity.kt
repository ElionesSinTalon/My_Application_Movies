package mendoza.ruiz.myapplicationmovies

import mendoza.ruiz.myapplicationmovies.navigation.AppNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mendoza.ruiz.myapplicationmovies.network.RetrofitInstance
import mendoza.ruiz.myapplicationmovies.ui.theme.MyApplicationMoviesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Despertar la Api antes de cargar la UI
        lifecycleScope.launch {
            try {
                RetrofitInstance.api.getPeliculas()
            }catch (e: Exception){
                //Ignorar error del ping
            }
        }
        setContent {
            MyApplicationMoviesTheme{
                AppNavigation()
            }
        }
    }
}

