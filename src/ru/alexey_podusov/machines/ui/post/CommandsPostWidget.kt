package ru.alexey_podusov.machines.ui.post

import ru.alexey_podusov.machines.forms.post.Ui_PostCommandWidget
import ru.alexey_podusov.machines.models.ModelPost
import ru.alexey_podusov.machines.ui.CellBaseWidget
import ru.alexey_podusov.machines.ui.StringBaseWidget
import ru.alexey_podusov.machines.ui.StringCommandsBaseWidget

class CommandsPostWidget(model: ModelPost) : StringCommandsBaseWidget(model) {
    override fun createStringCommand(): StringBaseWidget {
        return StringPostWidget()
    }

    override fun bindCommands() {
        model as ModelPost
        for ((i, command) in model.commandsList.withIndex()) {
            val stringWidget = stringWidgetList.get(i) as StringPostWidget
            stringWidget.number = command.number
        }
    }

}