package mendoza.ruiz.myapplicationmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mendoza.ruiz.myapplicationmovies.navigation.AppNavigation
import mendoza.ruiz.myapplicationmovies.ui.theme.MyApplicationMoviesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationMoviesTheme {
                AppNavigation()
            }
        }
    }
}
