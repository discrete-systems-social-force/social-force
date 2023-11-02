package rendering

import di.DIModule
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import simulation.IEngine

class ViewModel(
    engine: IEngine
): IViewModel {
    private val viewModelScope = DIModule.appScope

    override val state: StateFlow<AppState>
        get() = _state.asStateFlow()

    private val _state = MutableStateFlow(
        AppState(
            humans = emptyList(),
            walls = engine.walls,
        )
    )

    init {
        viewModelScope.launch {
            engine.start()
            engine.humans().collect { newHumans ->
                _state.update {
                    it.copy(
                        humans = newHumans,
                    )
                }
            }
        }
    }
}