package ru.alexey_podusov.machines.factories

import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.WorkareaTab
import ru.alexey_podusov.machines.ui.BaseCommands
import ru.alexey_podusov.machines.ui.BaseWorkarea

interface IFactory {
    fun createModel(): BaseEngine
    fun createWorkWidget(tab: WorkareaTab): BaseWorkarea
    fun createCommandsWidget(tab: CommandTab): BaseCommands
}