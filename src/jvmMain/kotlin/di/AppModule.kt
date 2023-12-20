package di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.kodein.di.*
import simulation.Engine
import simulation.IEngine
import simulation.models.Agent
import ui.GetWallsFromImageUseCase
import ui.simulation.ISimulationViewModel
import ui.simulation.SimulationStartingEntry
import ui.simulation.SimulationViewModel
import ui.start.StartViewModel

val appModule = DI.Module("App") {
    bind<ISimulationViewModel> {
        factory { startingEntry: SimulationStartingEntry ->
            SimulationViewModel(
                engine = instance(arg = startingEntry),
                appScope = instance(),
                startingEntry = startingEntry,
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
        factory { startingEntry: SimulationStartingEntry ->
            val agents = startingEntry.agentStartPositions
                .map { agentPosition ->
                    Agent().apply {
                        destination = startingEntry.endingPoint
                        position = agentPosition
                        speed = 0.5f
                    }
                }
            Engine(agents, startingEntry.walls, 30)
        }
    }

    bindProvider<GetWallsFromImageUseCase> {
        GetWallsFromImageUseCase()
    }

    bindSingleton<CoroutineScope> {
        CoroutineScope(SupervisorJob())
    }
}

val di = DI.from(listOf(appModule))
