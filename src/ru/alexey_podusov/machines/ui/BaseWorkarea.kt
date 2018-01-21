package ru.alexey_podusov.machines.ui

import com.trolltech.qt.gui.QResizeEvent
import com.trolltech.qt.gui.QWidget
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.WorkareaTab

abstract class BaseWorkarea(protected val tab: WorkareaTab): QWidget() {
    abstract fun updateSizeWidget()
    abstract fun updateWorkArea()
    abstract fun connect()


    override fun resizeEvent(arg__1: QResizeEvent?) {
        super.resizeEvent(arg__1)
        updateSizeWidget()
    }
}