package di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import rendering.IViewModel
import rendering.ViewModel
import simulation.Engine
import simulation.IEngine
import simulation.models.Agent
import simulation.models.Vector
import simulation.models.Wall

object DIModule {

    fun provideViewModel(
        engine: IEngine = provideEngine(),
    ): IViewModel {
        return ViewModel(
            engine = engine,
        )
    }

    fun provideEngine(): IEngine {
        val agent = Agent().apply {
            destination = Vector(100f, 100f)
            position = Vector(0f, 0f)
            speed = 1f
        }

        val agents = listOf(
            agent
        )
        val walls =
            (10..30).map {  Wall(x = 10, y = it) } +
            (50..60).map { Wall(x = 60, y = it) } +
            (50..60).map { Wall(x = it, y = 70) }
        return Engine(agents, walls, 30)
    }

    val appScope = CoroutineScope(SupervisorJob())
}