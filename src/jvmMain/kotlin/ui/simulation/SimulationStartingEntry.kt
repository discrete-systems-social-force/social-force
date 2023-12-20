package ui.simulation

import simulation.models.Vector
import simulation.models.Wall

data class SimulationStartingEntry(
    val walls: List<Wall>,
    val agentStartPositions: List<Vector>,
    val endingPoint: Vector,
)
