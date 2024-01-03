package ui.start

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ui.GetWallsFromImageUseCase

class StartViewModel(
    private val getWallsFromImageUseCase: GetWallsFromImageUseCase,
    private val appScope: CoroutineScope,
) : StateScreenModel<StartState>(
    initialState = StartState(
        walls = emptyList(),
        isLoading = false,
        agentPositions = emptyList(),
        endPoints = emptyList(),
    ),
) {

    fun onNewFile(path: String) {
        appScope.launch {
            mutableState.update {
                it.copy(isLoading = true)
            }

            val (newWalls, newAgentPositions, endPoints) = getWallsFromImageUseCase(path)
            mutableState.update {
                it.copy(
                    walls = newWalls,
                    agentPositions = newAgentPositions,
                    endPoints = endPoints,
                )
            }

            mutableState.update {
                it.copy(isLoading = false)
            }
        }
    }

    fun clearWalls() {
        mutableState.update {
            it.copy(walls = emptyList())
        }
    }
}
