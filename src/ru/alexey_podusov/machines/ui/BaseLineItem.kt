package ru.alexey_podusov.machines.ui

import com.trolltech.qt.core.QEvent
import com.trolltech.qt.core.QObject
import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.connect

abstract class BaseLineItem : QFrame() {
    val onLinkStringSignal = Signal2<Int, Int>()
    val inFocusSignal = Signal1<Int>()

    protected val mainLayout = QVBoxLayout()
    protected val stringLayout = QHBoxLayout()
    protected val numberLineLabel = QLabel()

    var number: Int = 0
        set(value) {
            field = value
            numberLineLabel.setText("$value")
        }

    var scaleFactor = 3

    var isSelected: Boolean = false
        set(value) {
            when(value) {
                true -> numberLineLabel.setStyleSheet("color: green")
                false -> numberLineLabel.setStyleSheet("color: black")
            }
            field = value
        }

    companion object {
        val HEIGHT_WIDGET = 26
        val HEIGHT_STRING = 20
        val WIDTH_NUMBER_STRING = 20

        val PREVIOUS_STRING_TEXT = "Предыдущая строка: "

        val SELECT_STRING_CSS = """#commandString{
                                        padding-right: 5px;
                                        background-color: #99db88
                                        }"""

        val NOSELECT_STRING_CSS = """#commandString{
                                        padding-right: 5px;
                                        }"""
    }

    init {
        setObjectName("commandString")

        numberLineLabel.setMinimumWidth(WIDTH_NUMBER_STRING)
        numberLineLabel.setFixedHeight(HEIGHT_STRING)
        numberLineLabel.setAlignment(Qt.AlignmentFlag.AlignCenter)
        stringLayout.addWidget(numberLineLabel)

        mainLayout.setContentsMargins(0, 0, 0, 0)
        mainLayout.addLayout(stringLayout)

        mainLayout.setAlignment(Qt.Alignment(Qt.AlignmentFlag.AlignVCenter))

        setLayout(mainLayout)

        setFixedHeight(HEIGHT_WIDGET)
        setStyleSheet(NOSELECT_STRING_CSS)
    }

    override fun eventFilter(obj: QObject?, event: QEvent?): Boolean {
        if (QEvent.Type.FocusIn.equals(event!!.type())) {
            inFocusSignal.emit(number)
        }
        return super.eventFilter(obj, event)
    }

    open fun setExecBorder(prevCommand: Int) {
        setStyleSheet(SELECT_STRING_CSS)
    }

    open fun hideExecBorder() {
        setStyleSheet(NOSELECT_STRING_CSS)
    }
}