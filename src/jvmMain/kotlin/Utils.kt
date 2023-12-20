import simulation.models.Vector

object Utils {
    const val SCENE_SIZE = 200
    const val SCENE_BORDERS_WIDTH = 0.05f

    val DEFAULT_ENDING_POINT
        get() = Vector(x = SCENE_SIZE.toFloat(), y = SCENE_SIZE.toFloat())
}
