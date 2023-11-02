// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.DIModule
import kotlinx.coroutines.cancel
import rendering.AppState
import rendering.IViewModel

@Composable
@Preview
fun App(
    state: AppState,
) {
    MaterialTheme {
        Column {
            Canvas(modifier = Modifier.fillMaxSize().padding(all = 5.dp).clipToBounds()) {
                val maxHeight = size.height
                val maxWidth = size.width
                val minSquare = minOf(maxHeight, maxWidth)
                val scale = minSquare/500f


                val humanPoints = state.humans.map { human ->
                    human.position.let {
                        Offset(x = it.x, y = it.y) to human.radius
                    }
                }

                scale(scaleX = scale, scaleY = scale, pivot = Offset.Zero) {
                    humanPoints.forEach { (position, radius) ->
                        drawCircle(
                            center = position,
                            color = Color.Green,
                            radius = radius,
                        )
                    }
                }

                drawRect(
                    color = Color.Black,
                    size = Size(
                        width = minSquare,
                        height = minSquare,
                    ),
                    style = Stroke(width = 1.0f)
                )
            }
        }

    }
}

fun main() {
    val viewModel: IViewModel = DIModule.provideViewModel()

    application {

        Window(onCloseRequest = ::exitApplication) {
            val state by viewModel.state.collectAsState()
            App(state = state)
        }
    }

    DIModule.appScope.cancel()
}

//@OptIn(InternalCoroutinesApi::class)
//fun main() {
//    val agent = Agent()
//    agent.destination = Vector(15f, 3f)
//    agent.position = Vector(0f, 0f)
//    agent.speed = 2f
//    val agents = listOf(
//        agent
//    )
//    val engine = Engine(agents, listOf(), 1)
//    engine.start()
//    runBlocking {
//        engine.humans().toList()
//    }
//}