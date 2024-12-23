import androidx.compose.runtime.Composable
import pages.LoginPageContent
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition

@Composable
fun App() {
    Navigator(LoginPageContent()) { navigator ->
        SlideTransition(navigator)
    }
}
