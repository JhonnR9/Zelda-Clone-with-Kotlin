package graphics

import constants.GameConstants
import entities.Entity
import entities.Player
import listeners.Keyboard
import world.World
import java.awt.*
import java.awt.image.BufferedImage
import javax.swing.JFrame

class Window : Canvas() {
    private val jFrame: JFrame = JFrame()

    private val world = World()
    private val keyboard = Keyboard(world.player)

    init {
        initFrame()
        addKeyListener(keyboard)
        createBufferStrategy(3)
    }

    private fun initFrame() {
        val icon: Image = Toolkit.getDefaultToolkit().getImage(javaClass.getResource("/icon/icon.png"))
        jFrame.apply {
            title = "Rpg Game"
            iconImage = icon
            preferredSize = GameConstants.gameDimension
            add(this@Window)
            isResizable = true
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            isVisible = true
            pack()
            setLocationRelativeTo(null)

        }

    }

    private fun clearScreen(graphics: Graphics) {
        graphics.apply {
            color = Color.black
            fillRect(0, 0, width, height)
        }
    }



    fun update() {
        world.update()
    }


    fun render() {
        bufferStrategy.drawGraphics.apply {
            clearScreen(this)
            /** render yours objects here **/
            world.render(this)

            /**----------------------------**/
            dispose()
            bufferStrategy.show()

        }
    }
}