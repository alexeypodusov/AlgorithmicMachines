package ru.alexey_podusov.machines.ui

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.QVBoxLayout
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.BaseEngine.StatusPlay.STOPPED
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.utils.UserPreferences

abstract class BaseLineCommands(tab: CommandTab) : BaseCommands(tab) {
    protected val lineItemWidgets = ArrayList<BaseLineItem>()
    protected val transCommands = ArrayList<Int>()

    private val scrollAreaLayout = QVBoxLayout()
    protected val commandLinesLayout = QVBoxLayout()
    protected var selectedCommand = 0
    protected var currentExecCommand = -1

    init {
        initUI()
        updateCommands()
        transCommands.add(0)
    }

    abstract fun createStringCommand(): BaseLineItem
    abstract fun bindCommands()

    private fun initUI() {
        scrollArea.widget().setLayout(scrollAreaLayout)
        lineItemWidgets.clear()
        scrollAreaLayout.addLayout(commandLinesLayout)
        scrollAreaWidget.setLayout(scrollAreaLayout)
        scrollArea.widget().setLayout(scrollAreaLayout)
        scrollAreaLayout.setAlignment(commandLinesLayout, Qt.AlignmentFlag.AlignTop)

    }

    override fun updateCommands() {
        val commandSize = tab.getCommandsSize()

        while (lineItemWidgets.size > commandSize) {
            lineItemWidgets.get(lineItemWidgets.size - 1).hide()
            lineItemWidgets.removeAt(lineItemWidgets.size - 1)
        }

        while (lineItemWidgets.size < commandSize) {
            val lineCommand = createStringCommand()
            lineCommand.onLinkStringSignal.connect(this, ::onLinkStringClicked)
            lineCommand.inFocusSignal.connect(this, ::onInFocusCommand)
            lineItemWidgets.add(lineCommand)
            commandLinesLayout.addWidget(lineCommand)
        }

        bindCommands()
        updateSelectingCommand(selectedCommand)
    }

    private fun onLinkStringClicked(transitionNum: Int, senderNum: Int) {
        if (transitionNum < lineItemWidgets.size) {
            if (transCommands.size > 1) {
                for (i in transCommands.size - 1 downTo currentTransCommandIndex + 1) {
                    transCommands.removeAt(i)
                }
            }

            if (transCommands.get(currentTransCommandIndex) != senderNum) {
                transCommands.add(senderNum)
            }
            currentTransCommandIndex++
            transCommands.add(transitionNum)

            goToCommandByTransCommandIndex(currentTransCommandIndex)
        }
    }

    protected open fun updateSelectingCommand(numCommand: Int) {
        //убираем старое выделение
        if (selectedCommand < lineItemWidgets.size) {
            lineItemWidgets.get(selectedCommand).isSelected = false
        }

        if (numCommand < lineItemWidgets.size) selectedCommand = numCommand
        else selectedCommand = lineItemWidgets.size - 1

        lineItemWidgets.get(selectedCommand).isSelected = true
    }


    protected open fun onInFocusCommand(numCommand: Int) {
        val isAutoDeleting = UserPreferences.instance.autoDeleteEmptyCommands
        if (isAutoDeleting && selectedCommand < lineItemWidgets.size && selectedCommand != numCommand) {
            if (lineItemWidgets.size != 1 && tab.isEmptyCommand(selectedCommand)) {
                tab.removeCommand(selectedCommand)
            }
        }
        updateSelectingCommand(numCommand)
    }

    override fun goToCommandByTransCommandIndex(commandIndexTransition: Int): Boolean {
        val transitionNum = transCommands.get(commandIndexTransition)

        if (transitionNum >= lineItemWidgets.size) {
            transCommands.removeAt(commandIndexTransition)
            return false
        }

        checkCurrentIndex()
        updateSelectingCommand(transitionNum)

        scrollArea.ensureWidgetVisible(lineItemWidgets.get(transitionNum))

        return true
    }

    override fun getTransCommandsSize(): Int {
        return transCommands.size
    }

    protected open fun deselectExecCommand() {
        if (currentExecCommand != -1) {
            lineItemWidgets.get(currentExecCommand).hideExecBorder()
        }
    }


    override fun onInsertBeforeClicked() {
        tab.insertCommand(selectedCommand)
        updateSelectingCommand(selectedCommand + 1)
    }

    override fun onInsertAfterClicked() {
        tab.insertCommand(selectedCommand + 1)
    }

    override fun onDeleteCommandClicked() {
        tab.removeCommand(selectedCommand)
    }

    override fun onSetExecCommand(numberCommand: Int, prevCommand: Int) {
        deselectExecCommand()
        lineItemWidgets.get(numberCommand).setExecBorder(prevCommand)
        scrollArea.ensureWidgetVisible(lineItemWidgets.get(numberCommand))
        currentExecCommand = numberCommand
    }

    override fun onChangedStatusPlay(statusPlay: BaseEngine.StatusPlay) {
        if (statusPlay == STOPPED) {
            deselectExecCommand()
            currentExecCommand = -1
        }
    }
}