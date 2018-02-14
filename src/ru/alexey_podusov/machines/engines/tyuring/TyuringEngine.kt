package ru.alexey_podusov.machines.engines.tyuring

import com.google.gson.annotations.Expose
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CellsWorkareaTab.Companion.isInTape
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.WorkareaTab
import ru.alexey_podusov.machines.engines.tyuring.TyuringEngine.TyuringCommandType.*

class TyuringEngine : BaseEngine() {
    enum class TyuringCommandType(var text: String) {
        NULL_COMMAND(""),
        LEFT_STEP("<-"),
        RIGHT_STEP("->"),
        STAY(".")
    }

    data class TyuringCommand(@Expose var numberColumn: Int, @Expose var numberRow: Int, @Expose var replace: String = "",
                              @Expose var commandType: TyuringCommandType = NULL_COMMAND, @Expose var newState: Int = -1)

    companion object {
        val ERROR_NULL_TYPE = "Не указана команда!"
        val ERROR_NEW_STATE_NULL = "Не указан номер состояния для перехода!"
        val ERROR_STATE_NOT_EXIST = "Состояния с таким номером не существует!"
        val ERROR_SYMBOL_NOT_INCLUDER = "Символа нет в алфавите!"

        val ERROR_BORDER = "Каретка вышла за пределы ленты!"
    }

    @Expose
    var alphabet = ""

    private val executedRowList = ArrayList<Int>()

    val alphabetChangedSignal = Signal1<Int>()

    init {
        addCommandTab("test")
        addWorkareaTab("test")
    }

    fun changeAlphabet(currentPositionCursor: Int, changedString: String) {
        var lastPositionCursor: Int? = null
        var newCursorPosition = 0
        val oldAlphabet = alphabet
        if (changedString.length > alphabet.length) {
            lastPositionCursor = currentPositionCursor - (changedString.length - alphabet.length)
            var insertedString = changedString.substring(lastPositionCursor, currentPositionCursor)
            insertedString = deleteDublicateSymbols(insertedString)
            alphabet = getNewStringWithoutDublicateSymbols(alphabet, insertedString, lastPositionCursor)

            val insertedLenght = alphabet.length - oldAlphabet.length
            newCursorPosition = lastPositionCursor + insertedLenght

            insertRowsInAllTabs(lastPositionCursor, insertedLenght)

        } else if (changedString.length < alphabet.length) {
            lastPositionCursor = currentPositionCursor + (alphabet.length - changedString.length)
            val deletedString = alphabet.substring(currentPositionCursor, lastPositionCursor)
            alphabet = changedString

            newCursorPosition = lastPositionCursor - deletedString.length

            deleteFromWorkareaTabs(deletedString)
            deleleteRowsInAllTabs(lastPositionCursor - 1, deletedString)
        }
        alphabetChangedSignal.emit(newCursorPosition)
    }

    private fun insertRowsInAllTabs(startPosition: Int, length: Int) {
        for (commandTab in commandTabs) {
            commandTab as TyuringCommandTab
            for (i in 0 until length) {
                commandTab.insertRow(startPosition + i)
            }
        }
    }

    private fun deleteFromWorkareaTabs(deletedString: String) {
        if (!deletedString.isEmpty()) {
            for (workareaTab in workareaTabs) {
                (workareaTab as TyuringWorkareaTab).deleteSymbolFromCells(deletedString)
            }
            onWorkareaChanged()
        }
    }

    private fun deleleteRowsInAllTabs(startPosition: Int, deletedString: String) {
        for (commandTab in commandTabs) {
            commandTab as TyuringCommandTab
            for (i in 0 until deletedString.length) {
                commandTab.deleteRow(startPosition - i, deletedString)
            }
            commandsChanged()
        }
    }

    private fun getNewStringWithoutDublicateSymbols(oldString: String, changedString: String, startedPosition: Int): String {
        var oldStringBuffer = StringBuffer(oldString)
        var insertPosition = startedPosition
        for (c in changedString) {
            if (!oldStringBuffer.contains(c)) {
                oldStringBuffer.insert(insertPosition++, c)
            }
        }
        return oldStringBuffer.toString()
    }

    private fun deleteDublicateSymbols(string: String): String {
        return StringBuilder(
                StringBuilder(string)
                        .reverse().toString()
                        .replace("(.)(?=.*\\1)", "")
        ).reverse().toString()
    }


    override fun executeCommand(numberCommand: Int, currentCommandTab: Int, currentWorkareaTab: Int): Boolean {
        val comTab = commandTabs.get(currentCommandTab) as TyuringCommandTab
        val workTab = workareaTabs.get(currentWorkareaTab) as TyuringWorkareaTab

        if (!checkValidationCommand(numberCommand, comTab, workTab, false)) return false
        val rowNum = getRowNumber(workTab, comTab)


        val command = comTab.commands.get(rowNum).get(numberCommand)
        workTab.changeValueCell(workTab.currentCarriage, command.replace)

        executedRowList.add(rowNum)

        when (command.commandType) {
            LEFT_STEP -> {
                if (isInTape(workTab.currentCarriage - 1)) {
                    workTab.currentCarriage--
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_BORDER, ERROR_TITLE)
                    return false
                }
            }

            RIGHT_STEP -> {
                if (isInTape(workTab.currentCarriage + 1)) {
                    workTab.currentCarriage++
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_BORDER, ERROR_TITLE)
                    return false
                }
            }
        }

        if (command.newState == 0) {
            sendMessageSignal.emit(MessageType.MESSAGE_INFO, SUCCES_TEXT, SUCCES_TITLE)
            statusPlay = StatusPlay.STOPPED
        }

        executeNumberCommandList.add(command.newState - 1)

        return true
    }

    override fun reverseExecuteCommand(numberCommand: Int, currentCommandTab: Int, currentWorkareaTab: Int): Boolean {

        val comTab = commandTabs.get(currentCommandTab) as TyuringCommandTab
        val workTab = workareaTabs.get(currentWorkareaTab) as TyuringWorkareaTab

        if (!checkValidationCommand(numberCommand, comTab, workTab, true)) return false
        val command = comTab.commands.get(executedRowList.last()).get(numberCommand)

        var replaceString: String = ""
        if (executedRowList.last() != comTab.commands.size - 1) {
            replaceString = Character.toString(alphabet[executedRowList.last()])
        }

        when (command.commandType) {
            LEFT_STEP -> {
                if (isInTape(workTab.currentCarriage + 1)) {
                    workTab.currentCarriage++
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_BORDER, ERROR_TITLE)
                    return false
                }
            }

            RIGHT_STEP -> {
                if (isInTape(workTab.currentCarriage - 1)) {
                    workTab.currentCarriage--
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_BORDER, ERROR_TITLE)
                    return false
                }
            }
        }

        workTab.changeValueCell(workTab.currentCarriage, replaceString)
        
        executedRowList.removeAt(executedRowList.lastIndex)
        return true
    }

    fun getRowNumber(workareaTab: TyuringWorkareaTab, commandTab: TyuringCommandTab): Int {

        val cellValue = workareaTab.getCell(workareaTab.currentCarriage)

        return if (cellValue == "") {
            //пустой символ(пробел) всегда последний в таблице
            commandTab.commands.size - 1
        } else {
            alphabet.indexOf(cellValue[0])
        }
    }

    override fun checkValidationCommand(numberCommand: Int, commandTab: CommandTab, workareaTab: WorkareaTab, isReverse: Boolean): Boolean {
        commandTab as TyuringCommandTab
        workareaTab as TyuringWorkareaTab

        var rowNumber = 0
        if (!isReverse) {
            rowNumber = getRowNumber(workareaTab, commandTab)
        } else {
            rowNumber = executedRowList.last()
        }

        if (rowNumber == -1) {
            sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_SYMBOL_NOT_INCLUDER, ERROR_TITLE)
            return false
        }

        val command = commandTab.commands.get(rowNumber).get(numberCommand)

        if (!command.replace.isEmpty() && alphabet.indexOf(command.replace[0]) == -1) {
            sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_SYMBOL_NOT_INCLUDER, ERROR_TITLE)
            return false
        }

        if (command.commandType == NULL_COMMAND) {
            sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_NULL_TYPE, ERROR_TITLE)
            return false
        }

        if (command.newState == -1) {
            sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_NEW_STATE_NULL, ERROR_TITLE)
            return false
        }

        if (command.newState > commandTab.commands.get(rowNumber).size) {
            sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_STATE_NOT_EXIST, ERROR_TITLE)
            return false
        }

        return true
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