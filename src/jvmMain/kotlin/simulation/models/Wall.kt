package simulation.models

data class Wall(val start: Vector, val end: Vector) {
    var thickness: Float = 0f
}