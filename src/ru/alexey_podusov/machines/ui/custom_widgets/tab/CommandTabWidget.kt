package ru.alexey_podusov.machines.ui.custom_widgets.tab

import com.trolltech.qt.gui.QWidget
import ru.alexey_podusov.machines.MainWindow
import ru.alexey_podusov.machines.connect
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

    fun connectCommands(widget: MainWindow) {
        for (i in 0 until count() - 1) {
            (widget(i) as BaseCommands).enableCommandButtonsChange.connect(widget, widget::changeEnableCommandButtons)
        }
    }

    override fun getNewWidget(tab: EngineTab): QWidget {
        return factory!!.createCommandsWidget(tab as CommandTab)
    }

    override fun addTab() {
        val tab = engine!!.addCommandTab(engine!!.getNewCommandTabName())
        addEngineTab(getNewWidget(tab), tab.name)
    }

    fun getCurrent(): BaseCommands {
        return currentWidget() as BaseCommands
    }

    override fun removeTabFromEngine(index: Int) {
        engine!!.removeCommandTab(index)
    }

    override fun renameTab(index: Int, text: String) {
        engine!!.renameCommandTab(index, text)
    }
}