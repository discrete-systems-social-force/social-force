import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import di.appModule
import org.kodein.di.compose.withDI
import ui.start.StartScreen

fun main() {
    application {
        Window(
            title = "Social Force",
            onCloseRequest = ::exitApplication,
        ) {
            MaterialTheme {
                NavigationRoot()
            }
        }
    }
}

@Composable
private fun NavigationRoot() {
    withDI(appModule) {
        Navigator(StartScreen())
    }
}
