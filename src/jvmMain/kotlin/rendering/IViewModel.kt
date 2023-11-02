package rendering

import kotlinx.coroutines.flow.StateFlow

interface IViewModel {
    val state: StateFlow<AppState>
}