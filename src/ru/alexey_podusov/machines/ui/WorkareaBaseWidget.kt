package ru.alexey_podusov.machines.ui

import com.trolltech.qt.gui.QResizeEvent
import com.trolltech.qt.gui.QWidget
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.models.ModelBase

abstract class WorkareaBaseWidget(protected val model: ModelBase): QWidget() {
    abstract fun updateSizeWidget()
    abstract fun updateWorkArea()

    protected open fun connect() {
        model.workAreaChangedSignal.connect{ updateWorkArea() }
    }

    override fun resizeEvent(arg__1: QResizeEvent?) {
        super.resizeEvent(arg__1)
        updateSizeWidget()
    }
}