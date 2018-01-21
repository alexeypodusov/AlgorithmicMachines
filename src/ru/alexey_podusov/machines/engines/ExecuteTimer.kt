package ru.alexey_podusov.machines.engines

import com.trolltech.qt.core.QTimer
import ru.alexey_podusov.machines.connect

class ExecuteTimer: QTimer() {
    val executeTimeoutSignal = Signal2<Int, Int>()

    var currentCommandTab = 0
    var currentWorkareaTab = 0

    init {
        timeout.connect(this, ::onTimeouted)
    }

    fun start(time: Int, currentCommandTab: Int, currentWorkAreaTab: Int) {
        this.currentCommandTab = currentCommandTab
        this.currentWorkareaTab = currentWorkAreaTab
        start(time)
    }

    private fun onTimeouted() {
        executeTimeoutSignal.emit(currentCommandTab, currentWorkareaTab)
    }
}