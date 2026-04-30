package mendoza.ruiz.myapplicationmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import mendoza.ruiz.myapplicationmovies.screens.ExploreScreen
import mendoza.ruiz.myapplicationmovies.screens.HomeScreen
import mendoza.ruiz.myapplicationmovies.ui.theme.BottomBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import mendoza.ruiz.myapplicationmovies.screens.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var selectedIndex by remember { mutableIntStateOf(0) }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomBar(
                        selectedIndex = selectedIndex,
                        onItemSelected = { selectedIndex = it }
                    )
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)){
                    when(selectedIndex){
                        0 -> HomeScreen()
                        1 -> ExploreScreen()
                        3 -> ProfileScreen()
                    }
                }
            }
        }
    }
}

