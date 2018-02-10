package ru.alexey_podusov.machines.ui.tyuring

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.QLineEdit
import com.trolltech.qt.gui.QWidget
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.ui.BaseCell

class TyuringCell : BaseCell() {
    companion object {
        val CURRENT_TYURING_CELL = """#cell{
                                        background-color: #ffeaea;
                                        border:1px solid ;
                                        }"""
    }

    var cellValue: String = ""
    set(value) {
        (elementWidget as QLineEdit).setText(value)
        field = value
    }

    init {
        (elementWidget as QLineEdit).textEdited.connect(this, ::onTextEdited)
    }

    override fun createElementWidget(): QWidget {
        val lineEdit = QLineEdit()
        //lineEdit.setMaxLength(1)
        lineEdit.setAlignment(Qt.AlignmentFlag.AlignCenter)
        return lineEdit
    }

    override fun setCurrent() {
        super.setCurrent()
        elementWidget.setStyleSheet(CURRENT_TYURING_CELL)
    }

    private fun onTextEdited(text: String) {
        var newValue = text
        if (text.length > 1) {
            val cursorPosition = (elementWidget as QLineEdit).cursorPosition()
            newValue = Character.toString(text.get(cursorPosition - 1))
        }

        if (newValue == " ") newValue = ""
        (elementWidget as QLineEdit).setText(cellValue)
        onCellChanchedSignal.emit(number, newValue)
    }
}