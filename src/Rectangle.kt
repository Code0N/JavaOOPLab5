import java.awt.*

open class Rectangle {
    protected var x1: Int = 0
    protected var y1: Int = 0
    protected var x2: Int = 0
    protected var y2: Int = 0

    fun getx1(): Int {
        return x1
    }

    fun gety1(): Int {
        return y1
    }

    fun getx2(): Int {
        return x2
    }

    fun gety2(): Int {
        return y2
    }

    constructor(xx1: Int, yy1: Int, xx2: Int, yy2: Int) { //first constructor
        x1 = xx1
        y1 = yy1
        x2 = xx2
        y2 = yy2
    }

    constructor(width: Int, height: Int) { //second constructor
        x1 = 0
        x2 = 0
        x2 = width
        y2 = height
    }

    constructor() { //third constructor
        x1 = 0
        x2 = 0
        y1 = 0
        y2 = 0
    }

    fun rect_print() {
        println("X1 = $x1")
        println("Y1 = $y1")
        println("X2 = $x2")
        println("Y2 = $y2")
    }

    fun move(dx: Int, dy: Int) {
        x1 += dx
        x2 += dx
        y1 += dy
        y2 += dy
    }

    fun union(rect: Rectangle): Rectangle? {
        return if (x1 < rect.getx1() && x2 > rect.getx1() && y1 > rect.gety1() && y1 < rect.gety2()) {
            Rectangle(rect.x1, y1, x2, rect.gety2())
        } else if (x1 > rect.getx1() && x1 < rect.getx2() && y1 > rect.gety1() && y1 < rect.gety2()) {
            Rectangle(x1, y1, rect.getx2(), rect.gety2())
        } else if (x1 < rect.getx1() && x2 > rect.getx1() && y2 > rect.gety1() && y2 < rect.gety2()) {
            Rectangle(rect.getx1(), rect.gety1(), x2, y2)
        } else if (x1 > rect.getx1() && x1 < rect.getx2() && y2 > rect.gety1() && y2 < rect.gety2()) {
            Rectangle(x1, rect.gety1(), rect.getx2(), y2)
        } else {
            null
        }
    }

    open fun Draw(g: Graphics) {
        g.color = Color.black
        g.drawRect(x1, y1, x2, y2)
    }
}
