package rendering

import simulation.dto.Human
import simulation.models.Wall

data class AppState(
    val humans: List<Human>,
    val walls: List<Wall>,
)
