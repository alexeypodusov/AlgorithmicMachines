package ru.alexey_podusov.machines.ui.post

import com.sun.org.apache.xpath.internal.operations.Mod
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.models.ModelPost
import ru.alexey_podusov.machines.ui.StringBaseWidget
import ru.alexey_podusov.machines.ui.StringCommandsBaseWidget

class CommandsPostWidget(model: ModelPost) : StringCommandsBaseWidget(model) {
    init {
        bindCommands()
    }
    override fun createStringCommand(): StringBaseWidget {
        return StringPostWidget()
    }

    override fun bindCommands() {
        model as ModelPost
        for ((i, command) in model.commandsList.withIndex()) {
            val widget = stringWidgetList.get(i) as StringPostWidget
            widget.setCommand(command)
            //widget.onEditedSignal.connect(this, "onEditedString(PostCommand)")
        }
    }

    private fun onEditedString(command: ModelPost.PostCommand) {
        (model as ModelPost).changeCommand(command)
    }

    override fun onAddCommandClicked() {
        //TODO
        (model as ModelPost).insertCommand(stringWidgetList.size)
        updateCommands()
    }

    override fun onDeleteCommandClicked() {
        //TODO
        (model as ModelPost).insertCommand(stringWidgetList.size - 1)
        updateCommands()
    }
}