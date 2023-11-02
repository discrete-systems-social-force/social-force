package simulation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import simulation.dto.Human
import simulation.models.Agent
import simulation.models.Vector
import simulation.models.Wall
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
                    emit(agents.map { Human(it.id, it.position) })
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
            val interactionForce = Vector(0f, 0f)

            agent.force = destinationForce.add(obstacleForce).add(interactionForce)
            agent.position = agent.position.add(agent.force)


            println(agent)

//            exitProcess(0)
        }
    }

}