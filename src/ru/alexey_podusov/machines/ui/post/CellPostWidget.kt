package ru.alexey_podusov.machines.ui.post


import com.trolltech.qt.gui.QPushButton
import com.trolltech.qt.gui.QWidget
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.ui.CellBaseWidget

class CellPostWidget : CellBaseWidget() {

    var isMark: Boolean = false
        set(value) {
            if (value) (elementWidget as QPushButton).setText("V")
            else (elementWidget as QPushButton).setText("")
            field = value
        }

    init {
        (elementWidget as QPushButton).clicked.connect{ checked: Boolean -> onCellChanchedSignal.emit(number, !isMark) }
    }

    override fun createElementWidget(): QWidget {
        return QPushButton()
    }
}



