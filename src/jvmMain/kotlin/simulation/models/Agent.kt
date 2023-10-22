package simulation.models

class Agent {
    var mass: Float = 0f

    var id: Int = 0
    var radius: Float = 0f
    var comfortableSpeed: Float = 0f
    var visionAngle: Float = 0f
    var horizonDistance: Float = 0f
    var relaxationTime: Float = 0f

    var visionCenter: Float = 0f
    var destination = Vector(0f, 0f)
    var position = Vector(0f, 0f)
    var next = Vector(0f, 0f)
    var angle: Float = 0f
    var speed: Float = 0f
    var acceleration: Float = 0f
    var finished: Boolean = false
    var pressure: Float = 0f
}