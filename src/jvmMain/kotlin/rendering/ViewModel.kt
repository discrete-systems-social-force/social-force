package rendering

import di.DIModule
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import simulation.IEngine
import simulation.dto.Human

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

    private fun List<Human>.toDomain() = map {  human ->
        human.copy(
            position = human.position.let {  position ->
                position.copy(
                    y = 500 - position.y,
                )
            }
        )
    }

    init {
        viewModelScope.launch {
            engine.start()
            engine.humans()
                .map { humans ->
                    humans.toDomain()
                }
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