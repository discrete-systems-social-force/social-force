package simulation

import simulation.dto.Human
import simulation.models.Agent
import simulation.models.Vector
import kotlin.math.roundToInt

fun Agent.toHuman() = Human(
    id = id,
    position = position.toScenePosition(),
    force = force,
)

fun Vector.toScenePosition(): Vector = Vector(
    x = x.roundToInt().toFloat(),
    y = (Utils.SCENE_SIZE - y).roundToInt().toFloat(),
)
