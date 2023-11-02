// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import simulation.Engine
import simulation.models.Agent
import simulation.models.Vector

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Column {
            Button(onClick = {
                text = "Hello, Desktop!"
            }) {
                Text(text)
            }
            Column {
                val counter = remember {
                    mutableStateOf(0)
                }
                Text(text = "Counter: ${counter.value}", modifier = Modifier.align(Alignment.CenterHorizontally))

                Canvas(modifier = Modifier.size(200.dp).clickable {
                    counter.value++
                }) {
                    drawRect(color = Color.Yellow)
                }
            }
        }

    }
}

//fun main() = application {
//    Window(onCloseRequest = ::exitApplication) {
//        App()
//    }
//}

@OptIn(InternalCoroutinesApi::class)
fun main() {
    val agent = Agent()
    agent.destination = Vector(15f, 3f)
    agent.position = Vector(0f, 0f)
    agent.speed = 2f
    val agents = listOf(
        agent
    )
    val engine = Engine(agents, listOf(), 1)
    engine.start()
    runBlocking {
        engine.humans().toList()
    }
}