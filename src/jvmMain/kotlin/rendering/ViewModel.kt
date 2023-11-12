package rendering

import di.DIModule
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import simulation.IEngine

class ViewModel(
    engine: IEngine,
    private val changeSceneUseCase: ChangeSceneUseCase,
) : IViewModel {
    private val viewModelScope = DIModule.appScope

    override val state: StateFlow<AppState>
        get() = _state.asStateFlow()

    override fun onNewFile(path: String) {
        viewModelScope.launch {
            val newWalls = changeSceneUseCase(path)
            _state.update {
                it.copy(walls = newWalls)
            }
        }
    }

    private val _state = MutableStateFlow(
        AppState(
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
