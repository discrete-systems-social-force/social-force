package ui.simulation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
        )
    }
}

@Composable
@Preview
private fun SimulationPage(
    state: SimulationState,
) {
    Column {
        SceneCanvas {
            drawWalls(walls = state.walls)
            drawHumans(humans = state.humans)
        }
    }
}
