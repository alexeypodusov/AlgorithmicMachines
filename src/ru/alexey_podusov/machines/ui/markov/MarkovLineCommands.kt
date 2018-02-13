package ru.alexey_podusov.machines.ui.markov

import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.markov.MarkovCommandTab
import ru.alexey_podusov.machines.engines.markov.MarkovEngine
import ru.alexey_podusov.machines.ui.BaseLineCommands
import ru.alexey_podusov.machines.ui.BaseLineItem

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
    }
}