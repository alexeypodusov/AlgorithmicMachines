package ru.alexey_podusov.machines.ui

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.QVBoxLayout
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.BaseEngine.StatusPlay.STOPPED

abstract class BaseLineCommands(model: BaseEngine) : BaseCommands(model) {
    protected val lineItemWidgets = ArrayList<BaseLineItem>()
    protected val clickedCommands = ArrayList<Int>()

    private val scrollAreaLayout = QVBoxLayout()
    protected val commandLinesLayout = QVBoxLayout()
    protected var currentCommandIndex: Int = 0
    protected var selectedCommand = 0
    protected var currentExecCommand = -1

    init {
        layoutsManipulation()
        updateCommands()
        clickedCommands.add(0)
    }

    abstract fun createStringCommand(): BaseLineItem
    abstract fun bindCommands()

    private fun layoutsManipulation() {
        scrollArea.widget().setLayout(scrollAreaLayout)
        lineItemWidgets.clear()
        scrollAreaLayout.addLayout(commandLinesLayout)
        scrollAreaWidget.setLayout(scrollAreaLayout)
        scrollArea.widget().setLayout(scrollAreaLayout)
        scrollAreaLayout.setAlignment(commandLinesLayout, Qt.AlignmentFlag.AlignTop)

    }

    protected fun updateCommands() {
        val commandSize = engine.getCommandsSize()

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
            if (clickedCommands.size > 1) {
                for (i in clickedCommands.size - 1 downTo currentCommandIndex + 1) {
                    clickedCommands.removeAt(i)
                }
            }
        }

        if (clickedCommands.get(currentCommandIndex) != senderNum) {
            clickedCommands.add(senderNum);
        }
        currentCommandIndex++;
        clickedCommands.add(transitionNum);

        goToCommandByNumber(transitionNum);
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
        updateSelectingCommand(numCommand)
    }

    protected open fun goToCommandByNumber(transitionNum: Int) {
        checkCurrentIndex()
        updateSelectingCommand(transitionNum)
        scrollArea.ensureWidgetVisible(lineItemWidgets.get(transitionNum))
    }

    protected open fun deselectExecCommand() {
        if (currentExecCommand != -1) {
            lineItemWidgets.get(currentExecCommand).hideExecBorder()
        }
    }

    override fun onSetExecCommand(numberCommand: Int, prevCommand: Int) {
        deselectExecCommand()
        lineItemWidgets.get(numberCommand).setExecBorder(prevCommand)
        scrollArea.ensureWidgetVisible(lineItemWidgets.get(numberCommand))
        currentExecCommand = numberCommand
    }

    override fun onBackCommandClicked() {
        if (currentCommandIndex > 0) {
            --currentCommandIndex
            goToCommandByNumber(clickedCommands.get(currentCommandIndex))
        }
    }

    override fun onForwardCommandClicked() {
        if (currentCommandIndex < (clickedCommands.size - 1)) {
            ++currentCommandIndex
            goToCommandByNumber(clickedCommands.get(currentCommandIndex))
        }
    }

    override fun checkCurrentIndex() {
        val backEnable: Boolean = currentCommandIndex != 0
        val forwardEnable: Boolean = !(currentCommandIndex == (clickedCommands.size - 1))
        enableCommandButtonsChange.emit(backEnable, forwardEnable)
    }

    override fun onChangedStatusPlay(statusPlay: BaseEngine.StatusPlay) {
        if (statusPlay == STOPPED) {
            deselectExecCommand()
            currentExecCommand = -1
        }
    }
}