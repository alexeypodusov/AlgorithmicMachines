package ru.alexey_podusov.machines.ui

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.*

abstract class BaseCell : QWidget() {
    companion object {
        val WIDTH_CELL = 22
        val HEIGHT_ELEMENT = 30
        val HEIGHT_LABEL = 15

        val CSS_COLOR_CURRENT = "color: rgb(156,  45, 7)"
        val CSS_BORDER_CURRENT = "border:1px solid ;"
    }

    protected val mainLayout = QVBoxLayout(this)
    protected val label = QLabel()
    protected val elementWidget = createElementWidget()

    val onCellChanchedSignal = Signal2<Int, Any>()

    var isActive: Boolean = true
        set(value) {
            when(value) {
                true -> label.show()
                false -> label.hide()
            }
            label.isVisible = value
            elementWidget.isEnabled = value
        }

    var number: Int = 0
        set(value) {
            field = value
            label.setText(value.toString())
        }

    init {
        elementWidget.setObjectName("cell")
        elementWidget.setFixedSize(WIDTH_CELL, HEIGHT_ELEMENT)

        label.setFixedSize(WIDTH_CELL, HEIGHT_LABEL)
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        mainLayout.addWidget(label)
        mainLayout.addWidget(elementWidget)
        mainLayout.setContentsMargins(0, 0, 0, 0)
    }

    abstract fun createElementWidget(): QWidget

    open fun setCurrent() {
        label.setStyleSheet(CSS_COLOR_CURRENT)
        elementWidget.setStyleSheet(CSS_BORDER_CURRENT)
    }

}
