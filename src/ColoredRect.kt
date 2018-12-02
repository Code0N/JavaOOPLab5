import java.awt.*

class ColoredRect : DrawableRect {
    protected var inColor: Color

    constructor(xx1: Int, yy1: Int, xx2: Int, yy2: Int, rCol: Color, inCol: Color) : super(xx1, yy1, xx2, yy2, rCol) {
        inColor = inCol
    }

    constructor(width: Int, height: Int, rCol: Color, inCol: Color) : super(width, height, rCol) {
        inColor = inCol
    }

    constructor() : super() {
        inColor = Color.blue
    }

    override fun Draw(g: Graphics) {
        g.color = super.outColor
        g.fillRect(x1, y1, x2, y2)
        g.color = inColor
        g.drawRect(x1, y1, x2, y2)
    }
}
