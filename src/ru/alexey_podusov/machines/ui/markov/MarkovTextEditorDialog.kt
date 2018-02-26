package ru.alexey_podusov.machines.ui.markov

import ru.alexey_podusov.machines.MachineType
import ru.alexey_podusov.machines.MainWindow
import ru.alexey_podusov.machines.engines.EngineTab
import ru.alexey_podusov.machines.ui.BaseTextEditorDialog

class MarkovTextEditorDialog(mainWindow: MainWindow, tab: EngineTab?) : BaseTextEditorDialog(mainWindow, tab) {
    override fun getTabList(): List<EngineTab> {
        return tab!!.engine!!.commandTabs
    }

    override fun getCurrentMachine(): MachineType {
        return MachineType.MARKOV
    }

    override fun onSaveButtonClicked() {

    }
}