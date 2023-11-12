package ui.simulation

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.StateFlow

interface ISimulationViewModel : ScreenModel {
    val state: StateFlow<SimulationState>

    fun changeRunningState()
}
