// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Utils.SCENE_BORDERS_WIDTH
import Utils.SCENE_SIZE
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
import simulation.models.Vector

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
                val scale = minSquare / SCENE_SIZE


                val humanPoints = state.humans.map { human ->
                    human.position.let {
                        Offset(x = it.x, y = it.y)
                    }
                }

                scale(scaleX = scale, scaleY = scale, pivot = Offset.Zero) {
                    (0..SCENE_SIZE).forEach {
                        // Horizontal line
                        drawLine(
                            color = Color.LightGray,
                            strokeWidth = SCENE_BORDERS_WIDTH,
                            start = Offset(x = 0f, y = it.toFloat()),
                            end = Offset(x = SCENE_SIZE.toFloat(), y = it.toFloat()),
                        )
                        // Vertical line
                        drawLine(
                            color = Color.LightGray,
                            strokeWidth = SCENE_BORDERS_WIDTH,
                            start = Offset(x = it.toFloat(), y = 0f),
                            end = Offset(x = it.toFloat(), y = SCENE_SIZE.toFloat()),
                        )
                    }

                    state.walls.forEach { wall ->
                        drawRect(
                            color = Color.Red,
                            topLeft = Offset(x = wall.x.toFloat(), y = wall.y.toFloat()),
                            size = Size(
                                width = 1f,
                                height = 1f,
                            ),
                        )
                    }

                    humanPoints.forEach { position ->
                        drawRect(
                            topLeft = position,
                            color = Color.Green,
                            size = Size(
                                width = 1f,
                                height = 1f,
                            ),
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

private fun Vector.toOffset(): Offset = Offset(x = x, y = y)

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
