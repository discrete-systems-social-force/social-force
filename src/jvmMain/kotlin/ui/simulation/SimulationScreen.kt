package ui.simulation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
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
        SimulationPage(
            state = state,
            onChangeRunningState = viewModel::changeRunningState,
        )
    }
}

@Composable
@Preview
private fun SimulationPage(
    state: SimulationState,
    onChangeRunningState: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
        ) {
            if (state.isRunning) {
                Text(text = "Przerwij")
            } else {
                Text("Wznów")
            }
        }

        SceneCanvas {
            drawWalls(walls = state.walls)
            drawHumans(humans = state.humans)
        }
    }
}
