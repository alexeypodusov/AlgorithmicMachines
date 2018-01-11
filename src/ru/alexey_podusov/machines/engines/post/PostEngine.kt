package ru.alexey_podusov.machines.engines.post

import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.WorkareaTab
import ru.alexey_podusov.machines.engines.post.PostEngine.PostCommandType.*
import ru.alexey_podusov.machines.engines.post.PostWorkareaTab.Companion.isInTape

class PostEngine : BaseEngine() {
    enum class PostCommandType(var text: String) {
        NULL_COMMAND(""),
        ADD_MARK("V добавить метку"),
        DELETE_MARK("X удалить метку"),
        LEFT_STEP("<- шаг влево"),
        RIGHT_STEP("-> шаг вправо"),
        CHECK_MARK("? проверить"),
        STOP("! стоп")
    }

    data class PostCommand(var number: Int, var commandType: PostCommandType = NULL_COMMAND,
                           var transition: Int = -1, var secondTransition: Int = -1, var comment: String = "")


    companion object {
        val SUCCES_TITLE = "Конец программы"
        val SUCCES_TEXT = "Конец программы"

        val ERROR_NULL_TYPE = "Не указана команда!"
        val ERROR_TRANSITION_NULL = "Не указан номер строки для перехода!"
        val ERROR_TRANSITION_NOT_EXIST = "Строки с таким номером не существует!"

        val ERROR_TITLE = "Ошибка"
        val ERROR_BORDER = "Каретка вышла за пределы ленты!"
        val ERROR_MARK_TRUE = "Метка уже есть!"
        val ERROR_MARK_FALSE = "Метка отсутствует!"
    }


    override fun addCommandTab(name: String): CommandTab {
        val tab = PostCommandTab(name)
        tab.setMainEngine(this)
        commandTabs.add(tab)
        return tab
    }

    override fun addWorkareaTab(name: String): WorkareaTab {
        val tab = PostWorkareaTab(name)
        tab.setMainEngine(this)
        workareaTabs.add(tab)
        return tab
    }

    private fun checkTransitionNumber(numberTransition: Int, sizeCommand: Int): Boolean {
        if (numberTransition == -1) {
            sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_TRANSITION_NULL, ERROR_TITLE)
            return false
        }
        if (numberTransition >= sizeCommand) {
            sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_TRANSITION_NOT_EXIST, ERROR_TITLE)
            return false
        }
        return true
    }

    override fun executeCommand(numberCommand: Int, currentCommandTab: Int, currentWorkareaTab: Int): Boolean {
        val comTab = commandTabs.get(currentCommandTab) as PostCommandTab
        val workTab = workareaTabs.get(currentWorkareaTab) as PostWorkareaTab

        executeNumberCommandList.add(comTab.commands.get(numberCommand).transition)

        if (!checkValidationCommand(numberCommand, comTab)) return false

        when (comTab.commands.get(numberCommand).commandType) {
            ADD_MARK -> {
                if (!workTab.cells.get(workTab.getIndexByNum(workTab.currentCarriage))) {
                    workTab.changeValueCell(workTab.currentCarriage, true)
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_MARK_TRUE, ERROR_TITLE)
                    return false
                }
            }

            DELETE_MARK -> {
                if (workTab.cells.get(workTab.getIndexByNum(workTab.currentCarriage))) {
                    workTab.changeValueCell(workTab.currentCarriage, false)
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_MARK_FALSE, ERROR_TITLE)
                    return false
                }
            }

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

            CHECK_MARK -> {
                if (!workTab.cells.get(workTab.getIndexByNum(workTab.currentCarriage))) {
                    executeNumberCommandList.removeAt(executeNumberCommandList.size - 1)
                    executeNumberCommandList.add(comTab.commands.get(numberCommand).secondTransition)
                }
            }

            STOP -> {
                sendMessageSignal.emit(MessageType.MESSAGE_INFO, SUCCES_TEXT, SUCCES_TITLE)
            }

            else -> {
                return false
            }
        }
        return true
    }

    override fun reverseExecuteCommand(numberCommand: Int, currentCommandTab: Int, currentWorkareaTab: Int): Boolean {
        val comTab = commandTabs.get(currentCommandTab) as PostCommandTab
        val workTab = workareaTabs.get(currentWorkareaTab) as PostWorkareaTab

        if (!checkValidationCommand(numberCommand, comTab)) {
            return false
        }

        when (comTab.commands.get(numberCommand).commandType) {
            ADD_MARK -> {
                if (workTab.cells.get(workTab.getIndexByNum(workTab.currentCarriage))) {
                    workTab.changeValueCell(workTab.currentCarriage, false)
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_MARK_FALSE, ERROR_TITLE)
                    return false
                }
            }

            DELETE_MARK -> {
                if (!workTab.cells.get(workTab.getIndexByNum(workTab.currentCarriage))) {
                    workTab.changeValueCell(workTab.currentCarriage, true)
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_MARK_TRUE, ERROR_TITLE)
                    return false
                }
            }

            LEFT_STEP -> {
                if (isInTape(workTab.currentCarriage + 1)) {
                    workTab.currentCarriage++
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_BORDER, ERROR_TITLE)
                    return false
                }
            }

            RIGHT_STEP -> {
                if (isInTape( workTab.currentCarriage - 1)) {
                    workTab.currentCarriage--
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_BORDER, ERROR_TITLE)
                    return false
                }
            }
        }
        return true
    }

    override fun checkValidationCommand(numberCommand: Int, tab: CommandTab): Boolean {
        tab as PostCommandTab
        if (tab.commands.get(numberCommand).commandType == NULL_COMMAND) {
            sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_NULL_TYPE, ERROR_TITLE)
            return false
        }

        if (tab.commands.get(numberCommand).commandType != STOP) {
            if (!checkTransitionNumber(tab.commands.get(numberCommand).transition, tab.getCommandsSize())) {
                return false
            }

            if (tab.commands.get(numberCommand).commandType == CHECK_MARK) {
                if (!checkTransitionNumber(tab.commands.get(numberCommand).secondTransition, tab.getCommandsSize())) {
                    return false
                }
            }
        }
        return true
    }
}
