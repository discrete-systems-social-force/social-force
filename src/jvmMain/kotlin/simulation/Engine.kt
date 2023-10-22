package simulation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import simulation.dto.Human
import simulation.models.Agent
import simulation.models.Wall
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
                delay((1000 / fps).seconds)
            }
        }
    }

    private fun step() {

    }


}