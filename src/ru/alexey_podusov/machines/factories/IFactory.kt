package ru.alexey_podusov.machines.factories

import ru.alexey_podusov.machines.models.ModelBase
import ru.alexey_podusov.machines.ui.CommandsBaseWidget
import ru.alexey_podusov.machines.ui.WorkareaBaseWidget

interface IFactory {
    fun createModel(): ModelBase
    fun createWorkareaWidget(model: ModelBase): WorkareaBaseWidget
    fun createCommandsBaseWidget(model: ModelBase): CommandsBaseWidget
}