package ru.alexey_podusov.machines.models

class ModelPost : ModelBase() {
    enum class PostCommandType(var text: String) {
        NULL_COMMAND(""),
        ADD_MARK("V добавить метку"),
        DELETE_MARK("X удалить метку"),
        LEFT_STEP("<- шаг влево"),
        RIGHT_STEP(". шаг вправо"),
        CHECK_MARK("? проверить"),
        STOP("! стоп")
    }

    data class PostCommand(var number: Int, var commandType: PostCommandType = PostCommandType.NULL_COMMAND,
                           var transition: Int = -1, var secondTransition: Int = -1, var comment: String = "")

    var currentCarriage: Int = 0 //not index! fact number cell
        set(value) {
            if (isInTape(value)) {
                field = value
                workAreaChangedSignal.emit()
            }
        }

    var cellsList = ArrayList<Boolean>()
    var commandsList = ArrayList<PostCommand>()

    companion object {
        val COUNT_CELLS = 2000
        fun isInTape(number: Int): Boolean = number in -((COUNT_CELLS / 2) - 1)..((COUNT_CELLS / 2) - 1)

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

    fun changeCommand(command: PostCommand) {
        commandsList.set(command.number, command)

    }

    init {
        for (i in 0..COUNT_CELLS) {
            cellsList.add(false)
        }
        commandsList.add(PostCommand(number = 0))

    }

    fun getCell(numCell: Int): Boolean {
        if (isInTape(numCell)) {
            val cellIndex = getIndexByNum(numCell)
            return cellsList.get(cellIndex)
        }

        return false
    }

    fun changeValueCell(numCell: Int, cellValue: Boolean) {
        if (isInTape(numCell)) {
            val cellIndex = getIndexByNum(numCell)
            cellsList.set(cellIndex, cellValue)
            workAreaChangedSignal.emit()
        }
    }

    fun insertCommand(number: Int) {
        //TODO
        commandsList.add(number, PostCommand(number = number))
    }

    fun removeCommand(number: Int) {
        //TODO
        commandsList.removeAt(number)
    }

    private fun isIndexInTape(index: Int): Boolean {
        return index in 0..COUNT_CELLS
    }

    private fun getIndexByNum(num: Int): Int = num + ((COUNT_CELLS / 2) - 1)

    override fun getCommandsSize(): Int {
        return commandsList.size
    }

    private fun checkTransitionNumber(numberTransition: Int): Boolean {
        if (numberTransition == -1) {
            sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_TRANSITION_NULL, ERROR_TITLE)
            return false
        }
        if (numberTransition >= commandsList.size) {
            sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_TRANSITION_NOT_EXIST, ERROR_TITLE)
            return false
        }
        return true
    }

    override fun executeCommand(numberCommand: Int): Boolean {
        executeNumberCommandList.add(commandsList.get(numberCommand).transition)

        if (!checkValidationCommand(numberCommand)) return false

        when (commandsList.get(numberCommand).commandType) {
            PostCommandType.ADD_MARK -> {
                if (!cellsList.get(getIndexByNum(currentCarriage))) {
                    changeValueCell(currentCarriage, true)
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_MARK_TRUE, ERROR_TITLE)
                    return false
                }
            }

            PostCommandType.DELETE_MARK -> {
                if (cellsList.get(getIndexByNum(currentCarriage))) {
                    changeValueCell(currentCarriage, false)
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_MARK_FALSE, ERROR_TITLE)
                    return false
                }
            }

            PostCommandType.LEFT_STEP -> {
                if (isInTape(currentCarriage - 1)) {
                    currentCarriage--
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_BORDER, ERROR_TITLE)
                    return false
                }
            }

            PostCommandType.RIGHT_STEP -> {
                if (isInTape(currentCarriage + 1)) {
                    currentCarriage++
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_BORDER, ERROR_TITLE)
                    return false
                }
            }

            PostCommandType.CHECK_MARK -> {
                if (!cellsList.get(getIndexByNum(currentCarriage))) {
                    executeNumberCommandList.removeAt(executeNumberCommandList.size - 1)
                    executeNumberCommandList.add(commandsList.get(numberCommand).secondTransition)
                }
            }

            PostCommandType.STOP -> {
                sendMessageSignal.emit(MessageType.MESSAGE_INFO, SUCCES_TEXT, SUCCES_TITLE)
            }

            else -> {
                return false
            }
        }
        return true
    }

    override fun reverseExecuteCommand(numberCommand: Int): Boolean {
        if (!checkValidationCommand(numberCommand)) {
            return false
        }

        when(commandsList.get(numberCommand).commandType)
        {
            PostCommandType.ADD_MARK -> {
                if (cellsList.get(getIndexByNum(currentCarriage))) {
                    changeValueCell(currentCarriage, false)
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_MARK_FALSE, ERROR_TITLE)
                    return false
                }
            }

            PostCommandType.DELETE_MARK -> {
                if (!cellsList.get(getIndexByNum(currentCarriage))) {
                    changeValueCell(currentCarriage, true)
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_MARK_TRUE, ERROR_TITLE)
                    return false
                }
            }

            PostCommandType.LEFT_STEP -> {
                if (isInTape(currentCarriage + 1)) {
                    currentCarriage++
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_BORDER, ERROR_TITLE)
                    return false
                }
            }

            PostCommandType.RIGHT_STEP -> {
                if (isInTape(currentCarriage - 1)) {
                    currentCarriage--
                } else {
                    sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_BORDER, ERROR_TITLE)
                    return false
                }
            }
        }
        return true
    }

    override fun checkValidationCommand(numberCommand: Int): Boolean {
        if (commandsList.get(numberCommand).commandType == PostCommandType.NULL_COMMAND) {
            sendMessageSignal.emit(MessageType.MESSAGE_ERROR, ERROR_NULL_TYPE, ERROR_TITLE)
            return false
        }

        if (commandsList.get(numberCommand).commandType != PostCommandType.STOP) {
            if (!checkTransitionNumber(commandsList.get(numberCommand).transition)) {
                return false
            }

            if (commandsList.get(numberCommand).commandType == PostCommandType.CHECK_MARK) {
                if (!checkTransitionNumber(commandsList.get(numberCommand).secondTransition)) {
                    return false
                }
            }
        }
        return true
    }
}
