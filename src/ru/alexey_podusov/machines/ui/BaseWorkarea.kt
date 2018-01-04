package ru.alexey_podusov.machines.ui

import com.trolltech.qt.gui.QResizeEvent
import com.trolltech.qt.gui.QWidget
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.BaseEngine

abstract class BaseWorkarea(protected val engine: BaseEngine): QWidget() {
    abstract fun updateSizeWidget()
    abstract fun updateWorkArea()

    protected open fun connect() {
        engine.workAreaChangedSignal.connect(this, ::updateWorkArea)
    }

    override fun resizeEvent(arg__1: QResizeEvent?) {
        super.resizeEvent(arg__1)
        updateSizeWidget()
    }
}