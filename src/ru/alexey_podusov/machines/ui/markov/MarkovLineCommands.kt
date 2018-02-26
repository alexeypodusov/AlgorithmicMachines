package ru.alexey_podusov.machines.ui.markov

import ru.alexey_podusov.machines.MainWindow
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.markov.MarkovCommandTab
import ru.alexey_podusov.machines.engines.markov.MarkovEngine
import ru.alexey_podusov.machines.ui.BaseLineCommands
import ru.alexey_podusov.machines.ui.BaseLineItem
import ru.alexey_podusov.machines.ui.BaseTextEditorDialog
import ru.alexey_podusov.machines.ui.post.PostTextEditorDialog

class MarkovLineCommands(tab: MarkovCommandTab) : BaseLineCommands(tab) {
    override fun createStringCommand(): BaseLineItem {
        return MarkovLineItem()
    }

    override fun bindCommands() {
        tab as MarkovCommandTab
        for ((i, command) in tab.commands.withIndex()) {
            val widget = lineItemWidgets.get(i) as MarkovLineItem
            widget.setCommand(command)

            widget.onEditedSignal.disconnect(this)
            widget.onEditedSignal.connect(this, ::onEditedString)
        }
    }

    private fun onEditedString(command: MarkovEngine.MarkovCommand) {
        (tab as MarkovCommandTab).changeCommand(command)
        if (command.number == tab.getCommandsSize() - 1) {
            tab.insertCommand(command.number + 1)
        }
    }

    override fun createTextEditorDialog(): BaseTextEditorDialog {
        return MarkovTextEditorDialog(MainWindow.getMainWindow(), tab)
    }
}