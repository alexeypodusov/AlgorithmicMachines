package ru.alexey_podusov.machines.ui.post

import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.PostEngine
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

    override fun onInsertBeforeClicked() {
        (engine as PostEngine).insertCommand(selectedCommand)
        updateCommands()
    }

    override fun onInsertAfterClicked() {
        (engine as PostEngine).insertCommand(selectedCommand + 1)
        updateCommands()
    }

    override fun onDeleteCommandClicked() {
        (engine as PostEngine).removeCommand(selectedCommand)
        updateCommands()
    }
}