package ru.alexey_podusov.machines.ui

import com.trolltech.qt.core.QEvent
import com.trolltech.qt.core.QObject
import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.connect

abstract class StringBaseWidget : QFrame() {
    val onLinkStringSignal = Signal2<Int, Int>()
    val inFocusSignal = Signal1<Int>()

    protected val mainLayout = QVBoxLayout()
    protected val stringLayout = QHBoxLayout()
    protected val numberStringLabel = QLabel()

    protected val previousStringWidget = QWidget()
    protected val previousStringText = QLabel(previousStringWidget)
    protected val previousNumberString = QLabel(previousStringWidget)
    protected val linkLayout = QHBoxLayout()

    var number: Int = 0
        set(value) {
            field = value
            numberStringLabel.setText("$value")
        }

    var isSelected: Boolean = false
        set(value) {
            when(value) {
                true -> numberStringLabel.setStyleSheet("color: green")
                false -> numberStringLabel.setStyleSheet("color: black")
            }
            field = value
        }

    companion object {
        val HEIGHT_STRING = 20
        val WIDTH_NUMBER_STRING = 20
        val LINK_LAYOUT_MARGIN = 20

        val PREVIOUS_STRING_TEXT = "Предыдущая строка: "

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

        previousStringText.setText(PREVIOUS_STRING_TEXT)
        linkLayout.addWidget(previousStringText)

        previousNumberString.linkActivated.connect(this, "onLinkActivated(String)")
        linkLayout.addWidget(previousNumberString)

        linkLayout.setContentsMargins(LINK_LAYOUT_MARGIN, 0, 0, 0)
        linkLayout.setAlignment(Qt.Alignment(Qt.AlignmentFlag.AlignLeft))

        previousStringWidget.setLayout(linkLayout)

        previousStringWidget.hide()
        mainLayout.setAlignment(Qt.Alignment(Qt.AlignmentFlag.AlignVCenter))
        mainLayout.addWidget(previousStringWidget)

        setLayout(mainLayout)

        setFixedHeight(HEIGHT_STRING)
        setStyleSheet(NOSELECT_STRING_CSS)
    }

    private fun onLinkActivated(link: String) {
        onLinkStringSignal.emit(link.toInt(), number)
    }

    override fun eventFilter(arg__1: QObject?, arg__2: QEvent?): Boolean {
        if (QEvent.Type.FocusIn.equals(arg__2!!.type())) {
            inFocusSignal.emit(number)
        }
        return super.eventFilter(arg__1, arg__2)
    }

    open fun setExecBorder(prevCommand: Int) {
        setStyleSheet(SELECT_STRING_CSS)
        setFixedHeight(3*HEIGHT_STRING)
        if (prevCommand != -1) {
            val link = "<a href='$prevCommand'>$prevCommand</a>"
            previousNumberString.setText(link)
            previousStringWidget.show()
        }
    }

    open fun hideExecBorder() {
        setStyleSheet(NOSELECT_STRING_CSS)
        setFixedHeight(HEIGHT_STRING)
        previousStringWidget.hide()
    }
}