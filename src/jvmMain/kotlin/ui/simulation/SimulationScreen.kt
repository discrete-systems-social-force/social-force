package ui.simulation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import di.di
import org.kodein.di.instance
import simulation.models.Vector
import simulation.models.Wall
import ui.utils.SceneCanvas
import ui.utils.drawEndingPoint
import ui.utils.drawHumans
import ui.utils.drawWalls
import java.util.*

data class SimulationScreen(
    private val walls: List<Wall>,
    private val agentStartPositions: List<Vector>,
    private val endingPoints: List<Vector>,
    private val uniqueKey: String = UUID.randomUUID().toString(),
) : Screen {

    private val viewModel by di.instance<SimulationStartingEntry, ISimulationViewModel>(
        arg = SimulationStartingEntry(
            walls = walls,
            agentStartPositions = agentStartPositions,
            endingPoints = endingPoints,
        ),
    )

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val state by viewModel.state.collectAsState()
        SimulationPage(
            state = state,
            onChangeRunningState = viewModel::changeRunningState,
            onBackClick = {
                viewModel.dispose()
                navigator.pop()
            },
            onResetClick = {
                viewModel.dispose()
                navigator.replace(
                    SimulationScreen(
                        walls = walls,
                        agentStartPositions = agentStartPositions,
                        endingPoints = endingPoints,
                    ),
                )
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
    onResetClick: () -> Unit,
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

            Row(
                modifier = Modifier.align(Alignment.Center),
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            ) {
                Button(
                    onClick = onChangeRunningState,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (state.isRunning) {
                            Color.Red
                        } else {
                            Color.Magenta
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
                Button(
                    onClick = onResetClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White,
                    ),
                ) {
                    Text("Resetuj")
                }
            }
        }

        SceneCanvas {
            drawWalls(walls = state.walls)
            drawHumans(humans = state.humans.map { it.position })

            state.endingPoints.forEach {
                drawEndingPoint(endPoint = it)
            }
        }
    }
}
