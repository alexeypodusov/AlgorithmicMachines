package ru.alexey_podusov.machines.engines

import com.google.gson.annotations.Expose
import com.trolltech.qt.core.QObject
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.utils.UserPreferences

abstract class BaseEngine : QObject() {
    @Expose
    val commandTabs = ArrayList<CommandTab>()
    @Expose
    val workareaTabs = ArrayList<WorkareaTab>()
    @Expose
    var task: String = ""

    companion object {
        val SUCCES_TITLE = "Конец программы"
        val SUCCES_TEXT = "Конец программы.\nКоличество шагов: "

        val ERROR_TITLE = "Ошибка"
    }

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
    val changedTabsSignal = Signal0()
    val workAreaChangedSignal = Signal0()
    val commandsChangedSignal = Signal0()

    var statusPlay: StatusPlay = StatusPlay.STOPPED
        set(value) {
            field = value
            changedStatusPlaySignal.emit(statusPlay)
        }

    val executeNumberCommandList = ArrayList<Int>()

    private val timer = ExecuteTimer()

    abstract fun executeCommand(numberCommand: Int, currentCommandTab: Int, currentWorkareaTab: Int): Boolean
    abstract fun reverseExecuteCommand(numberCommand: Int, currentCommandTab: Int, currentWorkareaTab: Int): Boolean
    abstract fun checkValidationCommand(numberCommand: Int, commandTab: CommandTab, workareaTab: WorkareaTab, isReverse: Boolean): Boolean

    protected abstract fun getCommandTabBaseName(): String
    protected abstract fun getWorkareaTabBaseName(): String

    fun addCommandTab(name: String): CommandTab {
        changedTabsSignal.emit()
        return createCommandTab(name)
    }

    fun addWorkareaTab(name: String): WorkareaTab {
        changedTabsSignal.emit()
        return createWorkareaTab(name)
    }

    protected abstract fun createCommandTab(name: String): CommandTab
    protected abstract fun createWorkareaTab(name: String): WorkareaTab

    fun setMainEngineOnTabs() {
        workareaTabs.forEach { it.setMainEngine(this) }
        commandTabs.forEach { it.setMainEngine(this) }
    }

    fun removeCommandTab(index: Int) {
        commandTabs.removeAt(index)
        changedTabsSignal.emit()
    }

    fun removeWorkareTab(index: Int) {
        workareaTabs.removeAt(index)
        changedTabsSignal.emit()
    }

    fun renameWorkareaTab(index: Int, name: String) {
        workareaTabs.get(index).name = name
        changedTabsSignal.emit()
    }

    fun renameCommandTab(index: Int, name: String) {
        commandTabs.get(index).name = name
        changedTabsSignal.emit()
    }

    init {
        timer.executeTimeoutSignal.connect(this, ::executeWithTimer)
        timer.isSingleShot = true
    }

    internal fun onWorkareaChanged() {
        workAreaChangedSignal.emit()
    }

    protected open fun prepareExecuting(currentWorkareaTab: Int) {
        executeNumberCommandList.clear()
        executeNumberCommandList.add(0)
        workareaTabs.get(currentWorkareaTab).saveWorkarea()
    }

    fun getNewTabName(tabs: List<EngineTab>, name: String): String {
        var newTabName = name

        var i = 2
        while (isTabNameBusy(newTabName, tabs)) {
            newTabName = name + " $i"
            i++
        }

        return newTabName
    }

    fun getNewCommandTabName(): String {
        return getNewTabName(commandTabs as List<EngineTab>, getCommandTabBaseName())
    }

    fun getNewWorkareaTabName(): String {
        return getNewTabName(workareaTabs as List<EngineTab>, getWorkareaTabBaseName())
    }


    fun isTabNameBusy(name: String, tabs: List<EngineTab>): Boolean {
        for (tab in tabs) {
            if (name == tab.name) return true
        }

        return false
    }

    fun play(currentCommandTab: Int, currentWorkareaTab: Int) {
        when (statusPlay) {
            StatusPlay.STOPPED -> {
                prepareExecuting(currentWorkareaTab)
                statusPlay = StatusPlay.PLAYING
                emitSetExecCommand()
            }
            StatusPlay.PLAYING -> {
                return
            }
            StatusPlay.ON_PAUSE -> {
                statusPlay = StatusPlay.PLAYING
            }
        }
        timer.start(UserPreferences.instance.speed.milliseconds, currentCommandTab, currentWorkareaTab)
    }

    private fun executeWithTimer(currentCommandTab: Int, currentWorkareaTab: Int) {
        if (statusPlay != StatusPlay.PLAYING) return

        emitSetExecCommand()

        if (executeCommand(executeNumberCommandList.last(), currentCommandTab, currentWorkareaTab)) {
            timer.start(UserPreferences.instance.speed.milliseconds, currentCommandTab, currentWorkareaTab)
            emitSetExecCommand()
        } else statusPlay = StatusPlay.STOPPED
    }

    fun playStep(currentCommandTab: Int, currentWorkareaTab: Int) {
        when (statusPlay) {
            StatusPlay.STOPPED -> {
                prepareExecuting(currentWorkareaTab)
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
        if (statusPlay != StatusPlay.STOPPED) {
            var indexPrevCommand = -1
            if (executeNumberCommandList.size > 1) {
                indexPrevCommand = executeNumberCommandList.get(executeNumberCommandList.size - 2)
            }

            setExecCommandSignal.emit(executeNumberCommandList.last(), indexPrevCommand)
        }
    }

    protected fun succesExecuted() {
        sendMessageSignal.emit(MessageType.MESSAGE_INFO, SUCCES_TEXT + (executeNumberCommandList.size - 1), SUCCES_TITLE)
        statusPlay = StatusPlay.STOPPED
    }

    internal fun commandsChanged() {
        commandsChangedSignal.emit()
    }
}