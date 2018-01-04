package ru.alexey_podusov.machines.ui.post

import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.models.PostEngine
import ru.alexey_podusov.machines.ui.BaseLineItem
import ru.alexey_podusov.machines.ui.BaseLineCommands

class PostLineCommands(model: PostEngine) : BaseLineCommands(model) {
    override fun createStringCommand(): BaseLineItem {
        return PostLineItem()
    }

    override fun bindCommands() {
        engine as PostEngine
        for ((i, command) in engine.commands.withIndex()) {
            val widget = lineItemWidgets.get(i) as PostLineItem
            widget.setCommand(command)

            widget.onEditedSignal.connect(this, ::onEditedString)
        }
    }

    private fun onEditedString(command: PostEngine.PostCommand) {
        (engine as PostEngine).changeCommand(command)
    }

    override fun onAddCommandClicked() {
        //TODO
        (engine as PostEngine).insertCommand(lineItemWidgets.size)
        updateCommands()
    }

    override fun onDeleteCommandClicked() {
        //TODO
        (engine as PostEngine).removeCommand(lineItemWidgets.size - 1)
        updateCommands()
    }
}