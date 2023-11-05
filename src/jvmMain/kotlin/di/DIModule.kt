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

    private fun provideEngine(): IEngine {
        val agents = listOf(
            Agent().apply {
                destination = Vector(50f, 80f)
                position = Vector(0f, 0f)
                speed = 0.5f
            },
        )
        val walls =
            (5..30).map { Wall(position = Vector(x = it.toFloat(), y = 30f)) }+
            (5..50).map { Wall(position = Vector(x = it.toFloat(), y = 50f)) }
        return Engine(agents, walls, 30)
    }

    val appScope = CoroutineScope(SupervisorJob())
}