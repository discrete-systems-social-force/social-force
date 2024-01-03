package ui.start

import simulation.models.Vector
import simulation.models.Wall

data class StartState(
    val walls: List<Wall>,
    val agentPositions: List<Vector>,
    val endPoints: List<Vector>,
    val isLoading: Boolean,
)
