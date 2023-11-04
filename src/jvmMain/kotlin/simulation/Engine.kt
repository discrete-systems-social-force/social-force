package simulation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import simulation.dto.Human
import simulation.models.Agent
import simulation.models.Vector
import simulation.models.Wall
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sin
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
            val obstacleForce = calculateObstacleForce(agent)

            // interaction force
            // x = (agent1.getAgentRadius() + agent2.getAgentRadius() - distance) * scaleCoefficient
            // y - calculateMutualAngle
            // y = normalized atan2(agent2.x - agent1.x, agent2.y - agent1.y)
            // y >= 0
            val interactionForce = Vector(0f, 0f)

            agent.force = destinationForce.add(obstacleForce).add(interactionForce)
            agent.position = agent.position.add(agent.force)

        }
    }

    private fun calculateObstacleForce(agent: Agent): Vector {
        var obstacleForce = Vector(0f, 0f)
        walls.forEach { wall ->
            val distance = wall.position.distance(agent.position)
            val angle = atan2(wall.position.y - agent.position.y, wall.position.x - agent.position.x)
            val coed = -exp(-distance / 2) / 10
            val force = Vector(
                x = distance * cos(angle.toDouble()).toFloat() * coed,
                y = distance * sin(angle.toDouble()).toFloat() * coed,
            )
            if (force.x.isFinite() && force.y.isFinite()) {
                obstacleForce = obstacleForce.add(force)
            }
        }
        return obstacleForce
    }


}