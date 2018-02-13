package ru.alexey_podusov.machines.engines.tyuring

import com.google.gson.annotations.Expose
import ru.alexey_podusov.machines.engines.BaseEngine
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

    @Expose
    var alphabet = ""

    val alphabetChangedSignal = Signal1<Int>()

    init {
        addCommandTab("test")
        addWorkareaTab("test")
    }

    fun changeAlphabet(currentPositionCursor: Int, changedString: String) {
        var lastPositionCursor: Int? = null
        var newCursorPosition = 0
        if (changedString.length > alphabet.length) {
            lastPositionCursor = currentPositionCursor - (changedString.length - alphabet.length)
            var insertedString = changedString.substring(lastPositionCursor, currentPositionCursor)
            insertedString = deleteDublicateSymbols(insertedString)
            alphabet = getNewStringWithoutDublicateSymbols(alphabet, insertedString, lastPositionCursor)

            newCursorPosition = lastPositionCursor + insertedString.length

        } else if (changedString.length < alphabet.length) {
            lastPositionCursor = currentPositionCursor + (alphabet.length - changedString.length)
            val deletedString = alphabet.substring(currentPositionCursor, lastPositionCursor)
            alphabet = changedString

            newCursorPosition = lastPositionCursor - deletedString.length
        }
        alphabetChangedSignal.emit(newCursorPosition)
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

    private fun deleteDublicateSymbols(string: String) : String {
        return StringBuilder(
                StringBuilder(string)
                        .reverse().toString()
                        .replace("(.)(?=.*\\1)", "")
        ).reverse().toString()

    }


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