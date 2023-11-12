package rendering

import Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import simulation.models.Vector
import simulation.models.Wall
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ChangeSceneUseCase {

    suspend operator fun invoke(newFilePath: String): List<Wall> {
        val newWalls = withContext(Dispatchers.IO) {
            val file = File(newFilePath)
            val image: BufferedImage = ImageIO.read(file)
            println("Size: ${image.width}x${image.height}")

            val width = image.width
            val height = image.height

            buildList {
                for (y in 0 until height) {
                    for (x in 0 until width) {
                        val color = image.getRGB(x, y)
                        val red = (color shr 16) and 0xff
                        val green = (color shr 8) and 0xff
                        val blue = color and 0xff

                        if (red == 0 && green == 0 && blue == 0) {
                            add(Wall(position = Vector(x = x.toFloat(), y = Utils.SCENE_SIZE+1-y.toFloat())))
                        }
                    }
                }
            }
        }

        return newWalls
    }
}
