package ru.alexey_podusov.machines.engines.tyuring

import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.WorkareaTab

class TyuringEngine : BaseEngine() {
    override fun executeCommand(numberCommand: Int, currentCommandTab: Int, currentWorkareaTab: Int): Boolean {
        return false
    }

    override fun reverseExecuteCommand(numberCommand: Int, currentCommandTab: Int, currentWorkareaTab: Int): Boolean {
        return false
    }

    override fun checkValidationCommand(numberCommand: Int, tab: CommandTab): Boolean {
        return false
    }

    override fun createCommandTab(name: String): CommandTab {
        val tab = TyuringCommandTab(name)
        tab.setMainEngine(this)
        commandTabs.add(tab)
        return tab
    }

    override fun createWorkareaTab(name: String): WorkareaTab {
        val tab = TyuringWorkareaTab(name)
        tab.setMainEngine(this)
        workareaTabs.add(tab)
        return tab
    }
}