package ui.simulation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import simulation.models.Wall
import ui.utils.SceneCanvas
import ui.utils.drawHumans
import ui.utils.drawWalls

data class SimulationScreen(
    val walls: List<Wall>,
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<List<Wall>, ISimulationViewModel>(arg = walls)
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        SimulationPage(
            state = state,
            onChangeRunningState = viewModel::changeRunningState,
            onBackClick = {
                navigator.pop()
            },
        )
    }
}

@Composable
@Preview
private fun SimulationPage(
    state: SimulationState,
    onChangeRunningState: () -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart),
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black,
                )
            }

            Button(
                onClick = onChangeRunningState,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (state.isRunning) {
                        Color.Red
                    } else {
                        Color.Black
                    },
                    contentColor = Color.White,
                ),
                modifier = Modifier.align(Alignment.Center),
            ) {
                if (state.isRunning) {
                    Text(text = "Przerwij")
                } else {
                    Text("Wznów")
                }
            }
        }

        SceneCanvas {
            drawWalls(walls = state.walls)
            drawHumans(humans = state.humans)
        }
    }
}
