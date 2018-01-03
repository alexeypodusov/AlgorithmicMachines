package ru.alexey_podusov.machines.ui.custom_widgets

import com.trolltech.qt.core.QEvent
import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.MainWindow
import ru.alexey_podusov.machines.connect

class LinkLineEdit : QLineEdit() {
    val clickedLinkSignal = Signal1<String>()

    companion object {
        val SELECTED_LINK_CSS = """#linkLineEdit{
                                        text-decoration: underline;
                                        color: MidnightBlue;
                                        font-weight: bold;
                                        }"""
        val DESELECTED_LINK_CSS = """#linkLineEdit{
                                        text-decoration: none;
                                        color: black;
                                        font-weight: normal;
                                        }"""
    }

    var isSelected: Boolean = false
        set(value) {
            val cursor = QCursor()
            when (value) {
                true -> {
                    cursor.setShape(Qt.CursorShape.PointingHandCursor)
                    setStyleSheet(SELECTED_LINK_CSS)
                }
                false -> {
                    cursor.setShape(Qt.CursorShape.ArrowCursor)
                    setStyleSheet(DESELECTED_LINK_CSS)
                }
            }
            setCursor(cursor)
            field = value
        }

    var isPressCtrl: Boolean = false

    init {
        setObjectName("linkLineEdit")
        val mainWindow: MainWindow = getMainWindow()
        mainWindow.keyPressSignal.connect(this,::onKeyPressed)
        mainWindow.keyReleaseSignal.connect(this, ::onKeyReleased)
    }

    private fun onKeyPressed(event: QKeyEvent) {
        if (event.key() == Qt.Key.Key_Control.value()) {
            isPressCtrl = true
        }
    }

    private fun onKeyReleased(event: QKeyEvent) {
        if (event.key() == Qt.Key.Key_Control.value()) {
            isPressCtrl = false
            isSelected = false
        }
    }


    private fun getMainWindow(): MainWindow {
        return QApplication.topLevelWidgets().first { it is MainWindow } as MainWindow
    }

    override fun mouseMoveEvent(arg__1: QMouseEvent?) {
        super.mouseMoveEvent(arg__1)
        if (isPressCtrl == true && !text().isEmpty()) isSelected = true
    }

    override fun mousePressEvent(arg__1: QMouseEvent?) {
        super.mousePressEvent(arg__1)
        if (isSelected) clickedLinkSignal.emit(text())
    }

    override fun leaveEvent(arg__1: QEvent?) {
        super.leaveEvent(arg__1)
        isSelected = false
    }
}