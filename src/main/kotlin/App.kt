import androidx.compose.runtime.Composable
import pages.LoginContent
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition

@Composable
fun App() {
    Navigator(LoginContent()) { navigator ->
        SlideTransition(navigator)
    }
}
