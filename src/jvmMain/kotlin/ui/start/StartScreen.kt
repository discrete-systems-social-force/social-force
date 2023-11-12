package ui.start

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import ui.simulation.SimulationScreen
import ui.utils.SceneCanvas
import ui.utils.drawWalls

class StartScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<StartViewModel>()
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        StartPage(
            state = state,
            onNewFile = {
                viewModel.onNewFile(it)
            },
            onNextClick = {
                navigator.push(SimulationScreen(walls = state.walls))
            },
        )
    }
}

@Composable
private fun StartPage(
    state: StartState,
    onNewFile: (String) -> Unit,
    onNextClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Wybierz scenę")

            var showFilePicker by remember { mutableStateOf(false) }

            if (state.isLoading) {
                CircularProgressIndicator()
            }

            Button(
                onClick = {
                    showFilePicker = true
                },
                enabled = !state.isLoading,
            ) {
                Text(text = "Wybierz plik")
            }

            val fileType = listOf("bmp")
            FilePicker(show = showFilePicker, fileExtensions = fileType) { file ->
                showFilePicker = false
                file?.path?.let {
                    onNewFile(file.path)
                }
            }

            Button(
                onClick = onNextClick,
            ) {
                Text("Przejdź dalej")
            }

            Text("Podgląd:")

            SceneCanvas {
                drawWalls(walls = state.walls)
            }
        }
    }
}
