package ru.alexey_podusov.machines.ui

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.*

abstract class StringBaseWidget : QFrame() {
    protected val mainLayout = QVBoxLayout()
    protected val stringLayout = QHBoxLayout()
    protected val numberStringLabel = QLabel()

    var number: Int = 0
        set(value) {
            field = value
            numberStringLabel.setText("$value")
        }

    companion object {
        val HEIGHT_STRING = 20
        val WIDTH_NUMBER_STRING = 20
        val LINK_LAYOUT_MARGIN = 20

        val SELECT_STRING_CSS = """#commandString{
                                        padding-right: 5px;
                                        border:1px solid green;
                                        }"""

        val NOSELECT_STRING_CSS = """#commandString{
                                        padding: 0px;
                                        border:0px solid green;
                                        }"""
    }

    init {
        setObjectName("commandString")

        numberStringLabel.setMinimumWidth(WIDTH_NUMBER_STRING)
        numberStringLabel.setFixedHeight(HEIGHT_STRING)
        numberStringLabel.setAlignment(Qt.AlignmentFlag.AlignCenter)
        stringLayout.addWidget(numberStringLabel)

        mainLayout.setContentsMargins(0, 0, 0, 0)
        mainLayout.addLayout(stringLayout)

        setLayout(mainLayout)

        setFixedHeight(HEIGHT_STRING)
        setStyleSheet(NOSELECT_STRING_CSS)
    }
}