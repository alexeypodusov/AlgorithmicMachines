package ru.alexey_podusov.machines.engines

import com.trolltech.qt.core.QObject
import com.trolltech.qt.core.QTimer
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.ui.BaseCommands

abstract class BaseEngine : QObject() {
    val commandTabs = ArrayList<CommandTab>()
    val workareaTabs = ArrayList<WorkareaTab>()

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
    val workAreaChangedSignal = Signal0()

    var speedTimer = 500

    var statusPlay: StatusPlay = StatusPlay.STOPPED
        set(value) {
            field = value
            changedStatusPlaySignal.emit(statusPlay)
        }

    protected val executeNumberCommandList = ArrayList<Int>()

    private val timer = ExecuteTimer()

    abstract fun executeCommand(numberCommand: Int, currentCommandTab: Int, currentWorkareaTab: Int): Boolean
    abstract fun reverseExecuteCommand(numberCommand: Int, currentCommandTab: Int, currentWorkareaTab: Int): Boolean
    abstract fun checkValidationCommand(numberCommand: Int, tab: CommandTab): Boolean

    abstract fun addCommandTab(name: String): CommandTab
    abstract fun addWorkareaTab(name: String): WorkareaTab

    fun removeCommandTab(index: Int) {
        commandTabs.removeAt(index)
    }

    fun removeWorkareTab(index: Int) {
        workareaTabs.removeAt(index)
    }

    init {
        timer.executeTimeoutSignal.connect(this, ::executeWithTimer)
        timer.isSingleShot = true
    }

    internal fun onWorkareaChanged()  {
        workAreaChangedSignal.emit()
    }

    fun play(currentCommandTab: Int, currentWorkareaTab: Int) {
        when (statusPlay) {
            StatusPlay.STOPPED -> {
                executeNumberCommandList.clear()
                executeNumberCommandList.add(0)
                statusPlay = StatusPlay.PLAYING
                emitSetExecCommand()
                timer.start(speedTimer)
            }
            StatusPlay.PLAYING -> {
                return
            }
            StatusPlay.ON_PAUSE -> {
                statusPlay = StatusPlay.PLAYING
            }
        }
    }

    private fun executeWithTimer(currentCommandTab: Int, currentWorkareaTab: Int) {
        if (statusPlay != StatusPlay.PLAYING) return

        emitSetExecCommand()

        if (executeCommand(executeNumberCommandList.last(), currentCommandTab, currentWorkareaTab)) {
            timer.start(speedTimer)
            emitSetExecCommand()
        } else statusPlay = StatusPlay.STOPPED
    }

    fun playStep(currentCommandTab: Int, currentWorkareaTab: Int) {
        when (statusPlay) {
            StatusPlay.STOPPED -> {
                executeNumberCommandList.clear()
                executeNumberCommandList.add(0)
                statusPlay = StatusPlay.ON_PAUSE
                emitSetExecCommand()
                return
            }
            StatusPlay.PLAYING -> {
                return
            }
            else -> {
            }
        }

        if (!executeCommand(executeNumberCommandList.last(), currentCommandTab, currentWorkareaTab)) {
            statusPlay = StatusPlay.STOPPED
            return
        }

        emitSetExecCommand()
    }

    fun playReverseStep(currentCommandTab: Int, currentWorkareaTab: Int) {
        if (executeNumberCommandList.size > 1) {
            executeNumberCommandList.removeAt(executeNumberCommandList.size - 1)
            reverseExecuteCommand(executeNumberCommandList.last(), currentCommandTab, currentWorkareaTab)
            emitSetExecCommand()
        } else {
            statusPlay = StatusPlay.STOPPED
        }
    }

    private fun emitSetExecCommand() {
        var indexPrevCommand = -1
        if (executeNumberCommandList.size > 1) {
            indexPrevCommand = executeNumberCommandList.get(executeNumberCommandList.size - 2)
        }

        setExecCommandSignal.emit(executeNumberCommandList.last(), indexPrevCommand)
    }
}