package ru.alexey_podusov.machines.ui.tyuring

import com.trolltech.qt.gui.QLineEdit
import com.trolltech.qt.gui.QWidget
import ru.alexey_podusov.machines.ui.BaseCell

class TyuringCell : BaseCell() {
    override fun createElementWidget(): QWidget {
        return QLineEdit()
    }
}