package ru.alexey_podusov.machines.factories

import ru.alexey_podusov.machines.models.BaseEngine
import ru.alexey_podusov.machines.ui.BaseCommands
import ru.alexey_podusov.machines.ui.BaseWorkarea

interface IFactory {
    fun createModel(): BaseEngine
    fun createWorkareaWidget(engine: BaseEngine): BaseWorkarea
    fun createCommandsBaseWidget(engine: BaseEngine): BaseCommands
}