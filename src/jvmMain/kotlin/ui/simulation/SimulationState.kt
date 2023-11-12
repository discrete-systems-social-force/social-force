package ui.simulation

import simulation.dto.Human
import simulation.models.Wall

data class SimulationState(
    val humans: List<Human>,
    val walls: List<Wall>,
    val isRunning: Boolean = true,
    val shouldNavigateBack: Boolean = false,
)
