package simulation.models

import kotlin.math.sqrt

data class Vector(
    val x: Float,
    val y: Float
) {
    fun unit(): Vector {
        val length = length()
        return Vector(x / length, y / length)
    }

    fun length(): Float = sqrt((x * x + y * y).toDouble()).toFloat()

    fun add(vector: Vector): Vector = Vector(x + vector.x, y + vector.y)

    fun subtract(vector: Vector): Vector = Vector(x - vector.x, y - vector.y)

    fun multiply(scalar: Float): Vector = Vector(x * scalar, y * scalar)

    fun distance(vector: Vector): Float = subtract(vector).length()

    override fun toString(): String = "Vector(x=$x, y=$y)"
}