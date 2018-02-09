package ru.alexey_podusov.machines.engines.markov

import com.google.gson.annotations.Expose
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.WorkareaTab
import ru.alexey_podusov.machines.engines.post.PostCommandTab
import ru.alexey_podusov.machines.engines.post.PostWorkareaTab


class MarkovEngine : BaseEngine() {

    private var isReplaced = false

    data class MarkovCommand(@Expose var number: Int, @Expose var sample: String = "",
                             @Expose var replacement: String = "", @Expose var comment: String = "")

    companion object {
        val SYMBOL_END = ".!"
    }

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

        val command = comTab.commands.get(numberCommand)

        var isFinish = false

        var replacement = command.replacement
        if (command.replacement.length >= SYMBOL_END.length &&
                command.replacement.takeLast(SYMBOL_END.length) == SYMBOL_END) {
            replacement = command.replacement.substring(0..(command.replacement.length - SYMBOL_END.length - 1))
            isFinish = true
        }

        val lastString = workTab.string
        if (!command.sample.isEmpty()) {
            workTab.string = workTab.string.replace(command.sample, replacement)
        } else {
            workTab.string = replacement + workTab.string
        }

        if (command.sample.isEmpty() && command.replacement.isEmpty()) {
            isReplaced = true
        }

        if (lastString != workTab.string) {
            isReplaced = true
        }

        if (isFinish || (!isReplaced && (numberCommand + 1 >= comTab.commands.size))) {
            sendMessageSignal.emit(MessageType.MESSAGE_INFO, SUCCES_TEXT, SUCCES_TITLE)
            statusPlay = StatusPlay.STOPPED
        }

        if (isReplaced) {
            executeNumberCommandList.add(0)
            isReplaced = false
        }


        return true
    }

    override fun reverseExecuteCommand(numberCommand: Int, currentCommandTab: Int, currentWorkareaTab: Int): Boolean {
        return false
    }

    override fun checkValidationCommand(numberCommand: Int, tab: CommandTab): Boolean {
        return true
    }

}