package ui.simulation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import simulation.IEngine
import simulation.models.Wall

class SimulationViewModel(
    engine: IEngine,
    appScope: CoroutineScope,
    walls: List<Wall>,
) : ISimulationViewModel {
    private val viewModelScope = appScope

    override val state: StateFlow<SimulationState>
        get() = _state.asStateFlow()

    private val _state = MutableStateFlow(
        SimulationState(
            humans = emptyList(),
            walls = engine.walls,
        ),
    )

    init {
        viewModelScope.launch {
            engine.start()
            engine.humans()
                .collect { newHumans ->
                    _state.update {
                        it.copy(
                            humans = newHumans,
                        )
                    }
                }
        }
    }
}