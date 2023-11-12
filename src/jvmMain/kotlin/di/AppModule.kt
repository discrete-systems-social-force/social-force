package di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.kodein.di.*
import simulation.Engine
import simulation.IEngine
import simulation.models.Agent
import simulation.models.Vector
import simulation.models.Wall
import ui.GetWallsFromImageUseCase
import ui.simulation.ISimulationViewModel
import ui.simulation.SimulationViewModel
import ui.start.StartViewModel

val appModule = DI.Module("App") {
    bind<ISimulationViewModel> {
        factory { walls: List<Wall> ->
            SimulationViewModel(
                engine = instance(arg = walls),
                appScope = instance(),
                walls = walls,
            )
        }
    }
    bindProvider<StartViewModel> {
        StartViewModel(
            getWallsFromImageUseCase = instance(),
            appScope = instance(),
        )
    }
    bind<IEngine> {
        factory { walls: List<Wall> ->
            val agents = listOf(
                Agent().apply {
                    destination = Vector(50f, 80f)
                    position = Vector(0f, 0f)
                    speed = 0.5f
                },
            )
            /*val walls =
                (5..30).map { Wall(position = Vector(x = it.toFloat(), y = 30f)) } +
                        (5..50).map { Wall(position = Vector(x = it.toFloat(), y = 50f)) }*/
            Engine(agents, walls, 30)
        }
    }

    bindProvider<GetWallsFromImageUseCase> {
        GetWallsFromImageUseCase()
    }

    bindSingleton<CoroutineScope> {
        CoroutineScope(SupervisorJob())
    }
}
