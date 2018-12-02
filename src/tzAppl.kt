import java.applet.Applet
import java.awt.*
import java.awt.event.*
import java.io.*
import java.util.ArrayList

import ColoredRect
import DrawableRect
import Rectangle

class tzAppl : Applet(), ActionListener, MouseListener, MouseMotionListener {
    internal var rectButton = Button("Прямоугольник 1")
    internal var drawableRectButton = Button("Прямоугольник 2")
    internal var coloredRectButton = Button("Прямоугольник 3")
    internal var saveToFileButton = Button("Save to file")
    internal var readFromFileButton = Button("Read from file")
    internal var rects: MutableList<Rectangle> = ArrayList()
    internal var selectedRect: Rectangle? = null
    internal var lastCursorPoint: Point? = null
    internal var rectsFile = File("D:\\serializedRects.txt")

    override fun init() {
        rectButton.name = "rect"
        rectButton.addActionListener(this)
        this.add(rectButton)
        this.add(drawableRectButton)
        drawableRectButton.name = "drawableRect"
        drawableRectButton.addActionListener(this)
        this.add(coloredRectButton)
        coloredRectButton.name = "coloredRect"
        coloredRectButton.addActionListener(this)
        this.addMouseListener(this)
        this.addMouseMotionListener(this)
        saveToFileButton.name = "saveToFile"
        saveToFileButton.addActionListener(this)
        readFromFileButton.name = "readFromFile"
        readFromFileButton.addActionListener(this)
        this.add(saveToFileButton)
        this.add(readFromFileButton)

        if (rectsFile.exists() && rectsFile.length() != 0L) {
            loadFromFile()
        }
    }

    override fun paint(g: Graphics?) {
        println("out")
        repaintRects()
    }

    override fun mousePressed(e: MouseEvent) {
        val x = e.x
        val y = e.y
        selectedRect = getSelectedRect(x, y)
        lastCursorPoint = Point(x, y)
    }

    fun getSelectedRect(x: Int, y: Int): Rectangle? {
        for (r in rects) {
            if (r.x1 <= x && r.x2 >= x && r.y1 <= y && r.y2 >= y) {
                return r
            }
        }
        return null
    }

    override fun mouseReleased(e: MouseEvent) {
        if (selectedRect != null) {
            val rectIndex = rects.indexOf(selectedRect!!)
            rects.removeAt(rectIndex)
            rects.add(0, selectedRect!!)
        }
        selectedRect = null
        lastCursorPoint = null
    }

    override fun mouseDragged(e: MouseEvent) {
        if (selectedRect == null) {
            return
        }
        val currentX = e.x
        val currentY = e.y
        val dx = currentX - lastCursorPoint!!.x
        val dy = currentY - lastCursorPoint!!.y
        selectedRect!!.move(dx, dy)
        repaintRects()
        lastCursorPoint = Point(currentX, currentY)
    }

    override fun mouseMoved(e: MouseEvent) {}
    override fun mouseClicked(e: MouseEvent) {}
    override fun mouseEntered(e: MouseEvent) {}
    override fun mouseExited(e: MouseEvent) {}

    override fun actionPerformed(e: ActionEvent) {
        var r = Rectangle(200, 200)
        when ((e.source as Button).name) {
            "drawableRect" -> {
                r = DrawableRect(200, 200, Color.CYAN)
            }
            "coloredRect" -> {
                r = ColoredRect(200, 200, Color.CYAN, Color.BLUE)
            }
            "rect" -> r = Rectangle(200, 200)
            "saveToFile" -> serializeRects()
            "readFromFile" -> loadFromFile()
        }
        r.Draw(this.graphics)
        rects.add(0, r)
    }


    internal fun repaintRects() {
        val g = this.graphics
        val d = this.size
        g.color = Color.white
        g.fillRect(0, 0, d.width, d.height)
        for (r in this.rects) {
            r.Draw(g)
        }
    }

    internal fun serializeRects() {
        val fs: FileOutputStream
        if (!rectsFile.exists()) {
            try {
                rectsFile.createNewFile()
            } catch (e: IOException) {
                println("Shit has happened")
                println(e.message)
            }

        }
        try {
            fs = FileOutputStream(rectsFile)
            val oos = ObjectOutputStream(fs)
            oos.writeObject(rects)
            fs.close()
        } catch (e: IOException) {
            println("Writing error")
            e.printStackTrace()
        }

    }

    internal fun loadFromFile() {
        val fs: FileInputStream
        try {
            fs = FileInputStream(rectsFile)
            val ois = ObjectInputStream(fs)
            val loadedRects = ois.readObject() as List<Rectangle>
            println(loadedRects.size)
            rects.addAll(0, loadedRects)
            fs.close()
        } catch (e: IOException) {
            println(e.message)
        } catch (e: ClassNotFoundException) {
            println(e.message)
        }

        repaintRects()
    }
}