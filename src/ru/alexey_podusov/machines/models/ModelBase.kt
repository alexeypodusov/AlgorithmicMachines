package ru.alexey_podusov.machines.models

import com.trolltech.qt.core.QObject
import com.trolltech.qt.core.QTimer
import ru.alexey_podusov.machines.connect
import kotlin.collections.ArrayList

abstract class ModelBase : QObject() {
    enum class StatusPlay {
        STOPPED,
        PLAYING,
        ON_PAUSE
    }

    enum class MessageType {
        MESSAGE_ERROR,
        MESSAGE_INFO;
    }

    val sendMessageSignal = Signal3<MessageType, String, String>()
    val setExecCommandSignal = Signal2<Int, Int>()
    val changedStatusPlaySignal = Signal1<StatusPlay>()

    var speedTimer = 500

    val workAreaChangedSignal = Signal0()
    protected var statusPlay: StatusPlay = StatusPlay.STOPPED
        set(value) {
            field = value
            changedStatusPlaySignal.emit(statusPlay)
        }
    protected val executeNumberCommandList = ArrayList<Int>()

    private val timer = QTimer()

    abstract fun getCommandsSize(): Int
    abstract fun executeCommand(numberCommand: Int): Boolean
    abstract fun reverseExecuteCommand(numberCommand: Int): Boolean
    abstract fun checkValidationCommand(numberCommand: Int): Boolean

    init {
        timer.timeout.connect { executeWithTimer() }
        timer.isSingleShot = true
    }

    fun play() {
        when (statusPlay) {
            StatusPlay.STOPPED -> {
                executeNumberCommandList.clear()
                executeNumberCommandList.add(0)
                statusPlay = StatusPlay.PLAYING
                emitSetExecCommand()
                timer.start(speedTimer);
            }
            StatusPlay.PLAYING -> {
                return
            }
            StatusPlay.ON_PAUSE -> {
                statusPlay = StatusPlay.PLAYING
            }
        }
    }

    private fun executeWithTimer() {
        if (statusPlay != StatusPlay.PLAYING) return

        emitSetExecCommand()

        if (executeCommand(executeNumberCommandList.last())) {
            timer.start(speedTimer)
            emitSetExecCommand()
        } else statusPlay = StatusPlay.STOPPED
    }

    private fun playStep() {
        when (statusPlay) {
            StatusPlay.STOPPED -> {
                executeNumberCommandList.clear()
                executeNumberCommandList.add(0)
                statusPlay = StatusPlay.ON_PAUSE
                emitSetExecCommand()
            }
            StatusPlay.PLAYING -> {
                return
            }
            else -> {
            }
        }

        if (!executeCommand(executeNumberCommandList.last())) {
            statusPlay = StatusPlay.STOPPED
            return
        }

        emitSetExecCommand()
    }

    private fun playReverseStep() {
        if (executeNumberCommandList.size > 1) {
            executeNumberCommandList.removeAt(executeNumberCommandList.size - 1)
            reverseExecuteCommand(executeNumberCommandList.last())
            emitSetExecCommand()
        } else {
            statusPlay = StatusPlay.STOPPED
        }
    }

    private fun emitSetExecCommand() {
        var indexPrevCommand = -1;
        if (executeNumberCommandList.size > 1) {
            //-3 нет времени объяснять, но так надо
            indexPrevCommand = executeNumberCommandList.size - 3;
        }

        setExecCommandSignal.emit(executeNumberCommandList.last(), indexPrevCommand)
    }
}