package ru.alexey_podusov.machines.engines.markov

import com.google.gson.annotations.Expose
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.WorkareaTab
import ru.alexey_podusov.machines.utils.UserPreferences

class MarkovEngine : BaseEngine() {

    private var isReplaced = false

    data class MarkovCommand(@Expose var number: Int, @Expose var sample: String = "",
                             @Expose var replacement: String = "", @Expose var comment: String = "")

    init {
        addCommandTab("test")
        addWorkareaTab("test")
    }

    override fun createCommandTab(name: String): CommandTab {
        val tab = MarkovCommandTab(name)
        tab.setMainEngine(this)
        commandTabs.add(tab)
        return tab
    }

    override fun createWorkareaTab(name: String): WorkareaTab {
        val tab = MarkovWorkareaTab(name)
        tab.setMainEngine(this)
        workareaTabs.add(tab)
        return tab
    }

    override fun executeCommand(numberCommand: Int, currentCommandTab: Int, currentWorkareaTab: Int): Boolean {
        val comTab = commandTabs.get(currentCommandTab) as MarkovCommandTab
        val workTab = workareaTabs.get(currentWorkareaTab) as MarkovWorkareaTab

        val symbolEnd = UserPreferences.instance.finalSymbolMarkov

        val command = comTab.commands.get(numberCommand)

        var isFinish = false

        if (executeNumberCommandList.size <= 1) {
            workTab.historyString = ArrayList()
        }

        workTab.historyString.add(workTab.string)

        var replacement = command.replacement
        if (command.replacement.length >= symbolEnd.length &&
                command.replacement.takeLast(symbolEnd.length) == symbolEnd) {
            replacement = command.replacement.substring(0..(command.replacement.length - symbolEnd .length - 1))
            isFinish = true
        }

        val lastString = workTab.string
        if (!command.sample.isEmpty()) {
            workTab.string = workTab.string.replaceFirst(command.sample, replacement)
        } else {
            workTab.string = replacement + workTab.string
        }

        if (command.sample.isEmpty() && command.replacement.isEmpty()) {
            isReplaced = true
        }

        if (lastString != workTab.string) {
            isReplaced = true
        }

        executeNumberCommandList.add(numberCommand + 1)

        if (isFinish || (!isReplaced && (numberCommand + 1 >= comTab.commands.size))) {
            succesExecuted()
        }

        if (isReplaced) {
            executeNumberCommandList.removeAt(executeNumberCommandList.lastIndex)
            executeNumberCommandList.add(0)
            isReplaced = false
        } else {

        }

        return true
    }

    override fun reverseExecuteCommand(numberCommand: Int, currentCommandTab: Int, currentWorkareaTab: Int): Boolean {
        val workTab = workareaTabs.get(currentWorkareaTab) as MarkovWorkareaTab

        workTab.string = workTab.historyString.last()
        workTab.historyString.removeAt(workTab.historyString.size - 1)

        return true
    }

    override fun checkValidationCommand(numberCommand: Int, commandTab: CommandTab, workareaTab: WorkareaTab, isReverse: Boolean): Boolean {
        return true
    }

}