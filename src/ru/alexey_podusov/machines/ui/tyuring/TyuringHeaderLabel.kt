package ru.alexey_podusov.machines.ui.tyuring

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.QLabel

class TyuringHeaderLabel: QLabel() {
    var isSelected: Boolean = false
    set(value) {
        when(value) {
            true -> setStyleSheet("color: green")
            false -> setStyleSheet("color: black")
        }
        field = value
    }

    init {
       setAlignment(Qt.AlignmentFlag.AlignCenter)
    }
}