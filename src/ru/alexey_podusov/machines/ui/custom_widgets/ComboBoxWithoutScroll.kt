package ru.alexey_podusov.machines.ui.custom_widgets

import com.trolltech.qt.gui.QComboBox
import com.trolltech.qt.gui.QWheelEvent

class ComboBoxWithoutScroll : QComboBox() {
    override fun wheelEvent(e: QWheelEvent?) {
        return
    }
}