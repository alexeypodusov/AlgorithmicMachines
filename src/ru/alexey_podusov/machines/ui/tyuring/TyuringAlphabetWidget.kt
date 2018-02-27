package ru.alexey_podusov.machines.ui.tyuring

import com.trolltech.qt.gui.QHBoxLayout
import com.trolltech.qt.gui.QLabel
import com.trolltech.qt.gui.QLineEdit
import com.trolltech.qt.gui.QWidget
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.tyuring.TyuringEngine

class TyuringAlphabetWidget : QWidget() {
    companion object {
        val ALPHABET_TEXT = "Алфавит: "
        val UNKNOWN_POSITION_CURSOR = -1
    }

    var engine: TyuringEngine? = null
        set(value) {
            field = value
            value?.alphabetChangedSignal!!.connect(this, ::updateLineEdit)
            updateLineEdit(UNKNOWN_POSITION_CURSOR)
        }

    private val mainLayout = QHBoxLayout()
    private val lineEdit = QLineEdit()

    init {
        mainLayout.addWidget(QLabel(ALPHABET_TEXT))
        mainLayout.addWidget(lineEdit)
        mainLayout.setMargin(0)
        lineEdit.selectionChanged.connect(this, ::onSelectionChanged)
        lineEdit.textEdited.connect(this, ::onTextEdited)
        setLayout(mainLayout)
    }

    private fun onSelectionChanged() {
        lineEdit.deselect()
    }

    private fun onTextEdited(editedValue: String) {
        val lastValue = engine?.alphabet
        val cursorPosition = lineEdit.cursorPosition()
        lineEdit.setText(lastValue)
        engine?.changeAlphabet(cursorPosition, editedValue)
    }

    private fun updateLineEdit(cursorPosition: Int) {
        val text = engine?.alphabet ?: ""
        lineEdit.setText(text)

        if (cursorPosition == UNKNOWN_POSITION_CURSOR) {
            lineEdit.setCursorPosition(text.length)
        } else {
            lineEdit.setCursorPosition(cursorPosition)
        }

    }


}