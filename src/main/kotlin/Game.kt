import GameConstants.Companion.gameDimension
import java.awt.Canvas
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import javax.swing.JFrame

class Game : Canvas(), Runnable {
    private val frame: JFrame = JFrame()
    private lateinit var thread: Thread
    private var isRunning = true

    private var image: BufferedImage

    init {
        initFrame()
        image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    }

    private fun initFrame() {
        frame.title = "JHones"
        frame.preferredSize = gameDimension
        frame.add(this)
        frame.isResizable = false
        frame.setLocationRelativeTo(null)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.isVisible = true
        frame.pack()

    }

    @Synchronized
    fun start() {
        thread = Thread(this)
        isRunning = true
        thread.start()
    }

    @Synchronized
    fun stop() {
        isRunning = false
        thread.join()
    }

    private fun update() {

    }

    private fun clearScreen(graphics: Graphics) {
        graphics.color = Color.black
        graphics.fillRect(0, 0, width, height)
    }

    private fun createBufferStrategy(): BufferStrategy {
        if (this.bufferStrategy == null) {
            this.createBufferStrategy(3)
        }
        return bufferStrategy

    }

    private fun initializeGraphics(): Graphics {
        return image.graphics
    }

    private fun finishGraphics(g: Graphics, bs: BufferStrategy) {

        g.drawImage(image, 0, 0, width, height, null)
        bs.show()
    }

    private fun render() {
        val bs = createBufferStrategy()
        var g = initializeGraphics()
        clearScreen(g)
        //render yours objects here
        g.color = Color.white
        g.font = Font("Arial",Font.BOLD,100)
        g.drawString("Olá Mundo", 100,100)


        g = bs.drawGraphics
        finishGraphics(g, bs)
    }

    override fun run() {
        var lastTime = System.nanoTime()
        val amountOfTicks = 60.0
        val ns = 1000000000 / amountOfTicks
        var delta = 0.0
        var frames = 0
        var timer = System.currentTimeMillis()

        while (isRunning) {
            val now = System.nanoTime()
            delta += (now - lastTime) / ns
            lastTime = now

            if (delta >= 1) {
                update()
                render()
                frames++
                delta--
            }
            if (System.currentTimeMillis() - timer >= 1000) {
                println("FPS: $frames")
                frames = 0
                timer = System.currentTimeMillis()
            }
        }

        stop()
    }

}