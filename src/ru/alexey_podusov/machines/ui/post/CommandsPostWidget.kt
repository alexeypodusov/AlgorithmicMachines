package ru.alexey_podusov.machines.ui.post

import ru.alexey_podusov.machines.forms.post.Ui_PostCommandWidget
import ru.alexey_podusov.machines.models.ModelBase
import ru.alexey_podusov.machines.ui.CommandsBaseWidget

class CommandsPostWidget(model: ModelBase) : CommandsBaseWidget(model) {
    val ui = Ui_PostCommandWidget()
    init {
        ui.setupUi(this)
    }
}