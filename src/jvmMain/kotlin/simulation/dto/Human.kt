package simulation.dto

import simulation.models.Vector

data class Human(val id:Int, val position: Vector, val radius: Float, val force: Vector = Vector(0f, 0f))
