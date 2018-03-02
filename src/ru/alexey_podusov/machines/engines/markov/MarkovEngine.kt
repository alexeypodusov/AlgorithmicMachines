package ru.alexey_podusov.machines.engines.markov

import com.google.gson.annotations.Expose
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.WorkareaTab
import ru.alexey_podusov.machines.engines.markov.MarkovWorkareaTab.*
import ru.alexey_podusov.machines.utils.UserPreferences

class MarkovEngine : BaseEngine() {

    private var isReplaced = false

    data class MarkovCommand(@Expose var number: Int, @Expose var sample: String = "",
                             @Expose var replacement: String = "", @Expose var comment: String = "")

    companion object {
        val WORKAREA_TAB_BASE_NAME = "Строка"
        val COMMAND_TAB_BASE_NAME = "Команды"
        val COUNT_REPLACEMENT_TEXT = "Количество замен: "
    }

    init {
        addCommandTab(COMMAND_TAB_BASE_NAME)
        addWorkareaTab(WORKAREA_TAB_BASE_NAME)
    }

    override fun getCommandTabBaseName(): String {
        return COMMAND_TAB_BASE_NAME
    }

    override fun getWorkareaTabBaseName(): String {
        return WORKAREA_TAB_BASE_NAME
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

        var isFinish = false //последняя выполняемая команда

        if (!command.sample.isEmpty() || !command.replacement.isEmpty()) {
            if (executeNumberCommandList.size <= 1) {
                workTab.historyString = ArrayList()
            }

            workTab.historyString!!.add(workTab.string)

            var replacement = command.replacement

            //если в команде присутствует символ завершения
            if (command.replacement.length >= symbolEnd.length &&
                    command.replacement.takeLast(symbolEnd.length) == symbolEnd) {
                replacement = command.replacement.substring(0..(command.replacement.length - symbolEnd.length - 1))
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

                var startPosition = 0
                if (!command.sample.isEmpty()) {
                    startPosition = lastString.indexOf(command.sample)
                }

                val historyItem = HistoryChangesItem(
                        numberCommand,
                        command.sample,
                        command.replacement,
                        lastString,
                        workTab.string,
                        startPosition)

                workTab.detailedHistoryReplacement!!.add(historyItem)

                isReplaced = true
                onWorkareaChanged()
            }
        } else {
            executeNumberCommandList.removeAt(executeNumberCommandList.last())
        }
        
        val nextCommandNumber = findNextCommandNumber(comTab, numberCommand + 1)
        executeNumberCommandList.add(nextCommandNumber)


        if (isFinish || (!isReplaced && (nextCommandNumber >= comTab.commands.size))) {
            sendMessageSignal.emit(MessageType.MESSAGE_INFO, SUCCES_TEXT + (executeNumberCommandList.size - 1) + '\n'
                    + COUNT_REPLACEMENT_TEXT + workTab.detailedHistoryReplacement!!.size,
                    SUCCES_TITLE)
            statusPlay = StatusPlay.STOPPED
        }

        if (isReplaced) {
            executeNumberCommandList.removeAt(executeNumberCommandList.lastIndex)
            val nextAfterReplace = findNextCommandNumber(comTab, 0)
            if (nextAfterReplace < comTab.commands.size) {
                executeNumberCommandList.add(nextAfterReplace)
            } else {
                executeNumberCommandList.add(0)
            }
            isReplaced = false
        }

        return true
    }

    private fun findNextCommandNumber(comTab: MarkovCommandTab, startPosition: Int): Int {
        var nextCommandNumber = startPosition
        var nextCommand: MarkovCommand

        while (nextCommandNumber < comTab.commands.size) {
            nextCommand = comTab.commands.get(nextCommandNumber)

            if (nextCommand.replacement != "" || nextCommand.sample != "") {
                break
            }

            nextCommandNumber++
        }
        return nextCommandNumber
    }

    override fun prepareExecuting(currentWorkareaTab: Int) {
        super.prepareExecuting(currentWorkareaTab)
        (workareaTabs.get(currentWorkareaTab) as MarkovWorkareaTab).detailedHistoryReplacement!!.clear()
        onWorkareaChanged()
    }

    override fun reverseExecuteCommand(numberCommand: Int, currentCommandTab: Int, currentWorkareaTab: Int): Boolean {
        val workTab = workareaTabs.get(currentWorkareaTab) as MarkovWorkareaTab

        if (workTab.string != workTab.historyString!!.last()) {
            workTab.detailedHistoryReplacement!!.removeAt(workTab.detailedHistoryReplacement!!.lastIndex)
        }

        workTab.string = workTab.historyString!!.last()
        workTab.historyString!!.removeAt(workTab.historyString!!.size - 1)

        return true
    }

    fun createHistoryListForAllTabs() {
        for (workareaTab in workareaTabs) {
            workareaTab as MarkovWorkareaTab
            if (workareaTab.detailedHistoryReplacement == null) {
                workareaTab.detailedHistoryReplacement = ArrayList()
            }

            if (workareaTab.historyString == null) {
                workareaTab.historyString = ArrayList()
            }
        }
    }

    override fun checkValidationCommand(numberCommand: Int, commandTab: CommandTab, workareaTab: WorkareaTab, isReverse: Boolean): Boolean {
        return true
    }

}