package simulation

import kotlinx.coroutines.flow.Flow
import simulation.dto.Human
import simulation.models.Wall

interface IEngine {
    fun humans(): Flow<List<Human>>
    fun start()
    fun stop()
    val walls: List<Wall>
}