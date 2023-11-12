package ui.start

import simulation.models.Wall

data class StartState(
    val walls: List<Wall>,
    val isLoading: Boolean,
)
