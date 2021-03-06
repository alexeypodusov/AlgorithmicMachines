package ru.alexey_podusov.machines.ui.post

import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.engines.post.PostEngine.PostCommand
import ru.alexey_podusov.machines.engines.post.PostEngine.PostCommandType.CHECK_MARK

/**
 * Добро пожаловать в треш
 */
class PostGraphicsScene : QGraphicsScene() {
    enum class ColorPen(var pen: QPen) {
        RED(QPen(QColor.fromRgb(255, 75, 75))),
        LIGHT_GREEN(QPen(QColor.fromRgb(0, 176, 0))),
        PINK(QPen(QColor.fromRgb(255, 15, 135))),
        LIGHT_BLUE(QPen(QColor.fromRgb(36, 146, 255))),
        DARK_ORANGE(QPen(QColor.fromRgb(255, 83, 0))),
        CYAN(QPen(QColor.fromRgb(64, 128, 128))),
        DARK_RED(QPen(QColor.fromRgb(136, 0, 21))),
        DARK_GREEN(QPen(QColor.fromRgb(0, 94, 0))),
        DARK_BLUE(QPen(QColor.fromRgb(30, 5, 245))),
        PURPLE(QPen(QColor.fromRgb(138, 21, 255))),
        LIGHT_ORANGE(QPen(QColor.fromRgb(243, 148, 52)));

        fun getNext(): ColorPen {
            if (ordinal + 1 < values().size) {
                return values().get(ordinal + 1)
            } else {
                return values().get(0)
            }
        }
    }

    var currentColorPen = ColorPen.values().get(0)
    val mainPen = QPen(QColor.black)


    var currentExecCommand = -1

    val busyLeftXList = ArrayList<busyXItem>()
    val busyRightXList = ArrayList<busyXItem>()

    val commandShapeItems = ArrayList<QAbstractGraphicsShapeItem >()

    data class busyXItem(var x: Double, var startNumberCommand: Int, var endNumberCommand: Int)

    companion object {
        val WIDTH_SCENE = 300.0
        val START_X_RECT = 75.0
        val START_Y_RECT = 25.0
        val WIDTH_RECT = 150.0
        val HEIGHT_RECT = 35.0
        val SPACE = 50.0

        val PEN_EXEC_WIDTH = 3
    }

    fun draw(commands: ArrayList<PostCommand>) {
        clear()

        commandShapeItems.clear()

        busyLeftXList.clear()
        busyRightXList.clear()

        var currentY = START_Y_RECT
        currentColorPen = ColorPen.values().get(0)
        for (command in commands) {

            drawCommand(command, currentY)
            drawRelationLine(command, currentY, commands.size)

            currentY += (HEIGHT_RECT + SPACE)

            currentColorPen = currentColorPen.getNext()
        }

        val sceneX = 0.0
        val sceneY = 0.0
        setSceneRect(sceneX, sceneY, WIDTH_SCENE, currentY)
    }

    private fun isIntervalIntersectsInterval(firstStart: Int, firstEnd: Int, secondStart: Int, secondEnd: Int): Boolean {
        if (firstStart in secondStart..secondEnd) return true
        if (firstEnd in secondStart..secondEnd) return true
        if (firstStart < secondStart && firstEnd > secondEnd) return true

        return false
    }

    private fun isFreeX(x: Double, startNumberCommand: Int, endNumberCommand: Int, busyXList: ArrayList<busyXItem>): Boolean {
        val count = busyXList.filter {
            isIntervalIntersectsInterval(startNumberCommand,
                    endNumberCommand,
                    it.startNumberCommand,
                    it.endNumberCommand)
        }
                .filter { x == it.x }.count()

        if (count == 0) return true
        return false
    }

    private fun getLeftFreeX(startNumberCommand: Int, endNumberCommand: Int): Double {

        var x = START_X_RECT - 20.0
        while (x >= 5.0) {
            if (isFreeX(x, startNumberCommand, endNumberCommand, busyLeftXList)) return x
            x -= 10.0
        }

        x = START_X_RECT - 20.0
        while (x >= 5.0) {
            if (isFreeX(x, startNumberCommand, endNumberCommand, busyLeftXList)) return x
            x -= 5.0
        }

        return START_X_RECT - 20.0
    }

    private fun getRightFreeX(startNumberCommand: Int, endNumberCommand: Int): Double {

        var x = START_X_RECT + WIDTH_RECT + 20.0
        while (x <= WIDTH_SCENE - 5.0) {
            if (isFreeX(x, startNumberCommand, endNumberCommand, busyRightXList)) return x
            x += 10.0
        }

        x = START_X_RECT + WIDTH_RECT + 20.0
        while (x <= WIDTH_SCENE - 5.0) {
            if (isFreeX(x, startNumberCommand, endNumberCommand, busyRightXList)) return x
            x += 5.0
        }

        return START_X_RECT + WIDTH_RECT + 20.0
    }


    private fun drawCommand(command: PostCommand, currentY: Double) {
        if (command.commandType != CHECK_MARK) {
            commandShapeItems.add(addRect(START_X_RECT, currentY, WIDTH_RECT, HEIGHT_RECT))
        } else {
            val polygonF = QPolygonF()
            polygonF.add(START_X_RECT, currentY + HEIGHT_RECT / 2)
            polygonF.add(START_X_RECT + WIDTH_RECT / 2, currentY)
            polygonF.add(START_X_RECT + WIDTH_RECT, currentY + HEIGHT_RECT / 2)
            polygonF.add(START_X_RECT + WIDTH_RECT / 2, currentY + HEIGHT_RECT)
            polygonF.add(START_X_RECT, currentY + HEIGHT_RECT / 2)
            commandShapeItems.add(addPolygon(polygonF))
        }

        var text = command.number.toString() + " " +
                command.commandType.shortText

        if (command.transition != -1) {
            text += (" " + command.transition)
        }

        if (command.commandType == CHECK_MARK && command.secondTransition != -1) {
            text += (" : " + command.secondTransition)
        }

        val textItem = addText(text)

        textItem.setPos(START_X_RECT + ((WIDTH_RECT / 2) - text.length * 3), currentY + HEIGHT_RECT / 6)
    }

    private fun drawRelationLine(command: PostCommand, currentY: Double, commandsSize: Int) {
        if (command.transition != -1 && command.transition < commandsSize) {
            if (command.commandType != CHECK_MARK && command.transition == command.number + 1) {
                val startXLine = START_X_RECT + WIDTH_RECT / 2
                val endXLine = startXLine
                val startYLine = currentY + HEIGHT_RECT
                val endYLine = startYLine + SPACE
                addLine(startXLine, startYLine, endXLine, endYLine)
                drawDownArrow(endXLine, endYLine)
                return
            }

            var startCommand = command.number
            var endCommand = command.transition
            if (endCommand < startCommand) {
                startCommand = command.transition
                endCommand = command.number
            }

            val freeX = getLeftFreeX(startCommand, endCommand)
            busyLeftXList.add(busyXItem(freeX, startCommand, endCommand))

            if (command.commandType != CHECK_MARK) {
                var startXLine = START_X_RECT + WIDTH_RECT / 2
                var endXLine = startXLine
                var startYLine = currentY + HEIGHT_RECT
                var endYLine = startYLine + SPACE / 3
                addLine(startXLine, startYLine, endXLine, endYLine, currentColorPen.pen)

                startXLine = START_X_RECT + WIDTH_RECT / 2
                endXLine = freeX
                startYLine = startYLine + SPACE / 3
                endYLine = startYLine
                addLine(startXLine, startYLine, endXLine, endYLine, currentColorPen.pen)

                startXLine = freeX
                endXLine = startXLine
                startYLine = currentY + HEIGHT_RECT + SPACE / 3
                endYLine = START_Y_RECT + command.transition * (HEIGHT_RECT + SPACE) - SPACE / 3
                addLine(startXLine, startYLine, endXLine, endYLine, currentColorPen.pen)
            } else {
                var startXLine = START_X_RECT
                var endXLine = freeX
                var startYLine = currentY + HEIGHT_RECT / 2
                var endYLine = startYLine
                addLine(startXLine, startYLine, endXLine, endYLine, currentColorPen.pen)

                startXLine = freeX
                endXLine = startXLine
                startYLine = currentY + HEIGHT_RECT / 2
                endYLine = START_Y_RECT + command.transition * (HEIGHT_RECT + SPACE) - SPACE / 3
                addLine(startXLine, startYLine, endXLine, endYLine, currentColorPen.pen)
            }

            var startXLine = freeX
            var endXLine = START_X_RECT + WIDTH_RECT / 2
            var startYLine = START_Y_RECT + command.transition * (HEIGHT_RECT + SPACE) - SPACE / 3
            var endYLine = startYLine
            addLine(startXLine, startYLine, endXLine, endYLine, currentColorPen.pen)

            startXLine = START_X_RECT + WIDTH_RECT / 2
            endXLine = startXLine
            startYLine = START_Y_RECT + command.transition * (HEIGHT_RECT + SPACE) - SPACE / 3
            endYLine = START_Y_RECT + command.transition * (HEIGHT_RECT + SPACE)
            addLine(startXLine, startYLine, endXLine, endYLine, currentColorPen.pen)
            drawDownArrow(endXLine, endYLine, currentColorPen.pen)
        }

        if (command.commandType == CHECK_MARK && command.secondTransition != -1 && command.secondTransition < commandsSize) {
            currentColorPen = currentColorPen.getNext()

            var startCommand = command.number
            var endCommand = command.secondTransition
            if (endCommand < startCommand) {
                startCommand = command.secondTransition
                endCommand = command.number
            }

            val freeX = getRightFreeX(startCommand, endCommand)
            busyRightXList.add(busyXItem(freeX, startCommand, endCommand))

            var startXLine = START_X_RECT + WIDTH_RECT
            var endXLine = freeX
            var startYLine = currentY + HEIGHT_RECT / 2
            var endYLine = startYLine
            addLine(startXLine, startYLine, endXLine, endYLine, currentColorPen.pen)

            startXLine = freeX
            endXLine = startXLine
            startYLine = currentY + HEIGHT_RECT / 2
            endYLine = START_Y_RECT + command.secondTransition * (HEIGHT_RECT + SPACE) - SPACE / 3
            addLine(startXLine, startYLine, endXLine, endYLine, currentColorPen.pen)

            startXLine = freeX
            endXLine = START_X_RECT + WIDTH_RECT / 2
            startYLine = START_Y_RECT + command.secondTransition * (HEIGHT_RECT + SPACE) - SPACE / 3
            endYLine = startYLine
            addLine(startXLine, startYLine, endXLine, endYLine, currentColorPen.pen)

            startXLine = START_X_RECT + WIDTH_RECT / 2
            endXLine = startXLine
            startYLine = START_Y_RECT + command.secondTransition * (HEIGHT_RECT + SPACE) - SPACE / 3
            endYLine = START_Y_RECT + command.secondTransition * (HEIGHT_RECT + SPACE)
            addLine(startXLine, startYLine, endXLine, endYLine, currentColorPen.pen)
            drawDownArrow(endXLine, endYLine, currentColorPen.pen)

        }
    }

    fun setExecCommand(numberCommand: Int) {
        if (currentExecCommand != -1 && currentExecCommand < commandShapeItems.size - 1 ) {
            commandShapeItems.get(currentExecCommand).setPen(mainPen)
        }

        if (numberCommand != -1 && numberCommand < commandShapeItems.size - 1) {
            val pen = QPen()
            pen.setWidth(PEN_EXEC_WIDTH)
            pen.setColor(QColor.darkGreen)
            commandShapeItems.get(numberCommand).setPen(pen)

            views().get(0).ensureVisible(commandShapeItems.get(numberCommand))
            currentExecCommand = numberCommand
        }
    }

    private fun drawDownArrow(x: Double, y: Double, pen: QPen = QPen(QColor.black)) {
        addLine(x, y, x + 5, y - 10, pen)
        addLine(x, y, x - 5, y - 10, pen)
    }

}