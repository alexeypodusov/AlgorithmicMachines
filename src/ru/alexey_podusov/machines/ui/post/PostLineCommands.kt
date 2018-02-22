package ru.alexey_podusov.machines.ui.post

import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.post.PostCommandTab
import ru.alexey_podusov.machines.engines.post.PostEngine
import ru.alexey_podusov.machines.ui.BaseLineItem
import ru.alexey_podusov.machines.ui.BaseLineCommands

class PostLineCommands(tab: PostCommandTab) : BaseLineCommands(tab) {
    override fun createStringCommand(): BaseLineItem {
        return PostLineItem()
    }

    override fun bindCommands() {
        tab as PostCommandTab
        for ((i, command) in tab.commands.withIndex()) {
            val widget = lineItemWidgets.get(i) as PostLineItem
            widget.setCommand(command)
            widget.onEditedSignal.disconnect(this)
            widget.onEditedSignal.connect(this, ::onEditedString)
        }
    }

    private fun onEditedString(command: PostEngine.PostCommand) {
        (tab as PostCommandTab).changeCommand(command)
        if (command.number == tab.getCommandsSize() - 1) {
            tab.insertCommand(command.number + 1)
        }
    }
}