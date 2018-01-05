package ru.alexey_podusov.machines.ui.custom_widgets.tab

import com.trolltech.qt.gui.QWidget
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.EngineTab
import ru.alexey_podusov.machines.ui.BaseCommands

class CommandTabWidget: EngineTabWidget() {
    companion object {
        val DEFAULT_COMMAND_TAB_NAME = "Команды"
    }

    override fun addWidgetsFromEngine() {
        clear()
        engine!!.commandTabs.forEach { addEngineTab(getNewWidget(it), it.name) }
    }

    override fun getNewWidget(tab: EngineTab): QWidget {
        return factory!!.createCommandsWidget(tab as CommandTab)
    }

    override fun addTab() {
        val tab = engine!!.addCommandTab(DEFAULT_COMMAND_TAB_NAME + count())
        addEngineTab(getNewWidget(tab), tab.name)
    }

    fun getCurrent(): BaseCommands {
        return currentWidget() as BaseCommands
    }
}