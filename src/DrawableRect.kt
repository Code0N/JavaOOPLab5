import java.awt.*

open class DrawableRect : Rectangle {
    var outColor: Color

    constructor(xx1: Int, yy1: Int, xx2: Int, yy2: Int, rCol: Color) : super(xx1, yy1, xx2, yy2) {
        outColor = rCol
    }

    constructor(width: Int, height: Int, rCol: Color) : super(width, height) {
        outColor = rCol
    }

    constructor() : super() {
        outColor = Color.green
    }

    override fun Draw(g: Graphics) {
        g.color = outColor
        g.drawRect(x1, y1, x2, y2)
    }
}
