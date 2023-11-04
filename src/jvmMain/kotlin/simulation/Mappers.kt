package simulation

import simulation.dto.Human
import simulation.models.Agent
import simulation.models.Vector
import kotlin.math.roundToInt

fun Agent.toHuman() = Human(
    id = id,
    position = position.toScenePosition(),
    force = force,
    radius = 3f,
)

fun Vector.toScenePosition(): Vector = Vector(
    x = x.roundToInt().toFloat(),
    y = y.roundToInt().toFloat(),
)
