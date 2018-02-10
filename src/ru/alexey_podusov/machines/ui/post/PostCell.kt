package ru.alexey_podusov.machines.ui.post


import com.trolltech.qt.gui.QPushButton
import com.trolltech.qt.gui.QWidget
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.ui.BaseCell

class PostCell : BaseCell() {

    var isMark: Boolean = false
        set(value) {
            if (value) (elementWidget as QPushButton).setText("V")
            else (elementWidget as QPushButton).setText("")
            field = value
        }

    init {
        (elementWidget as QPushButton).clicked.connect(this, ::onCellChanged)
    }

    private fun onCellChanged(clicked: Boolean) {
        onCellChanchedSignal.emit(number, !isMark)
    }

    override fun createElementWidget(): QWidget {
        return QPushButton()
    }
}



