package di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import rendering.IViewModel
import rendering.ViewModel
import simulation.Engine
import simulation.IEngine
import simulation.models.Agent
import simulation.models.Vector

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
        return Engine(agents, listOf(), 30)
    }

    val appScope = CoroutineScope(SupervisorJob())
}