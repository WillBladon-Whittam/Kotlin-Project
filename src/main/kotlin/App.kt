import androidx.compose.runtime.Composable
import pages.LoginPageContent
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun App() {
    Navigator(LoginPageContent())
}
