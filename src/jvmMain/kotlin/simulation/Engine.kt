package simulation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import simulation.dto.Human
import simulation.models.Agent
import simulation.models.Vector
import simulation.models.Wall
import kotlin.math.atan2
import kotlin.system.exitProcess
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class Engine(private val agents: List<Agent>, override val walls: List<Wall>, private val fps: Int = 60) : IEngine {

    private var running = false

    override fun start() {
        running = true
    }

    override fun stop() {
        running = false
    }

    @OptIn(ExperimentalTime::class)
    override fun humans(): Flow<List<Human>> {
        return flow {
            while (true) {
                if (running) {
                    step()
                    emit(agents.map(Agent::toHuman))
                }
                delay((1000 / fps).milliseconds)
            }
        }
    }

    private fun step() {
        agents.forEach { agent ->

            // destination force
            val direction = agent.destination.subtract(agent.position)
            var destinationForce = direction
            if (direction.length() > 1f) {
                destinationForce = destinationForce.unit().multiply(agent.speed)
            }

            // obstacle force
            val obstacleForce = Vector(0f, 0f)

            // interaction force
            // x = (agent1.getAgentRadius() + agent2.getAgentRadius() - distance) * scaleCoefficient
            // y - calculateMutualAngle
            // y = normalized atan2(agent2.x - agent1.x, agent2.y - agent1.y)
            // y >= 0
            val interactionForce = Vector(0f, 0f)

            agent.force = destinationForce.add(obstacleForce).add(interactionForce)
            agent.position = agent.position.add(agent.force)

            println(agent)

//            exitProcess(0)
        }
    }

    fun calculateObstacleForce(agent: Agent): Vector {
        val obstacleForce = Vector(0f, 0f)
        walls.forEach { wall ->
            obstacleForce.add(Vector(0f, 0f))
        }
        return obstacleForce
    }

    fun calculateMutualAngle(p1: Vector, p2: Vector): Float {
        val angle = atan2(
            p1.x - p2.x,
            p1.y - p2.y
        )
        return if (angle >= 0) {
            angle
        } else {
            (2 * Math.PI + angle).toFloat()
        }
    }

}