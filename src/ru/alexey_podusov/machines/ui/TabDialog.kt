package ru.alexey_podusov.machines.ui

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.QDialog
import ru.alexey_podusov.machines.MachineType
import ru.alexey_podusov.machines.MainWindow
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.EngineTab

abstract class TabDialog(var mainWindow: MainWindow, var tab: EngineTab?) : QDialog(mainWindow) {
    protected open fun initUI() {
        val flags = windowFlags()
        flags.clear(Qt.WindowType.WindowContextHelpButtonHint)
        setWindowFlags(flags)

        mainWindow.currentMachineChanged.connect(this, ::onCurrentMachineChange)
        tab!!.engine!!.changedTabsSignal!!.connect(this, ::onTabsChanged)
    }

    private fun onTabsChanged() {
        if (tab != null && tab!!.engine != null) {
            if (getTabList().contains(tab!!)) {
                return
            }
        }
        close()
    }

    abstract fun getTabList(): List<EngineTab>
    abstract fun getCurrentMachine(): MachineType

    private fun onCurrentMachineChange() {
        if (mainWindow.currentMachine != getCurrentMachine()) {
            close()
        }
    }
}