package ui.utils

import Utils.SCENE_BORDERS_WIDTH
import Utils.SCENE_SIZE
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.dp
import simulation.dto.Human
import simulation.models.Wall

@Composable
internal fun SceneCanvas(
    modifier: Modifier = Modifier,
    sceneContent: DrawScope.() -> Unit,
) {
    Canvas(modifier = modifier.fillMaxSize().padding(all = 5.dp).clipToBounds()) {
        val maxHeight = size.height
        val maxWidth = size.width
        val minSquare = minOf(maxHeight, maxWidth)
        val scale = minSquare / SCENE_SIZE

        scale(scaleX = scale, scaleY = scale, pivot = Offset.Zero) {
            drawGrid()
            sceneContent()
        }

        drawBounds(size = minSquare)
    }
}

private fun DrawScope.drawBounds(size: Float) {
    drawRect(
        color = Color.Black,
        size = Size(
            width = size,
            height = size,
        ),
        style = Stroke(width = 1.0f),
    )
}

internal fun DrawScope.drawGrid() {
    (0..SCENE_SIZE).forEach {
        drawLine(
            color = Color.LightGray,
            strokeWidth = SCENE_BORDERS_WIDTH,
            start = Offset(x = 0f, y = it.toFloat()),
            end = Offset(x = SCENE_SIZE.toFloat(), y = it.toFloat()),
        )

        drawLine(
            color = Color.LightGray,
            strokeWidth = SCENE_BORDERS_WIDTH,
            start = Offset(x = it.toFloat(), y = 0f),
            end = Offset(x = it.toFloat(), y = SCENE_SIZE.toFloat()),
        )
    }
}

internal fun DrawScope.drawWalls(walls: List<Wall>) {
    walls.forEach { wall ->
        drawRect(
            color = Color.Red,
            topLeft = Offset(x = wall.position.x, y = SCENE_SIZE - wall.position.y),
            size = Size(
                width = 1f,
                height = 1f,
            ),
        )
    }
}

internal fun DrawScope.drawHumans(humans: List<Human>) {
    val humanPoints = humans.map { human ->
        human.position.let {
            Offset(x = it.x, y = it.y)
        }
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
