package simulation.models

class Agent {
    var mass: Float = 0f

    var id: Int = 0

    var destination = Vector(0f, 0f)
    var position = Vector(0f, 0f)
    var speed: Float = 5f
    var force: Vector = Vector(0f, 0f)
    var finished: Boolean = false
    val radius: Float = 5f

    override fun toString(): String = "Agent(id=$id, destination=$destination, position=$position, force=$force)"
}