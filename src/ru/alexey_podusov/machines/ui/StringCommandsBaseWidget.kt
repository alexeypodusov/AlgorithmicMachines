package ru.alexey_podusov.machines.ui

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.QVBoxLayout
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.models.ModelBase
import ru.alexey_podusov.machines.models.ModelBase.StatusPlay.STOPPED

abstract class StringCommandsBaseWidget(model: ModelBase) : CommandsBaseWidget(model) {
    protected val stringWidgetList = ArrayList<StringBaseWidget>()
    protected val clickedCommandList = ArrayList<Int>()

    private val scrollAreaLayout = QVBoxLayout()
    protected val commandStringsLayout = QVBoxLayout()
    protected var currentCommandIndex: Int = 0
    protected var selectedCommand = 0
    protected var currentExecCommand = -1

    init {
        layoutsManipulation()
        updateCommands()
        updateSelectingCommand(selectedCommand)
        clickedCommandList.add(0)
    }

    abstract fun createStringCommand(): StringBaseWidget
    abstract fun bindCommands()

    private fun layoutsManipulation() {
        scrollArea.widget().setLayout(scrollAreaLayout)
        stringWidgetList.clear()
        scrollAreaLayout.addLayout(commandStringsLayout)
        scrollAreaWidget.setLayout(scrollAreaLayout)
        scrollArea.widget().setLayout(scrollAreaLayout)
        scrollAreaLayout.setAlignment(commandStringsLayout, Qt.AlignmentFlag.AlignTop)

    }

    protected fun updateCommands() {
        val commandSize = model.getCommandsSize()

        while (stringWidgetList.size > commandSize) {
            stringWidgetList.get(stringWidgetList.size - 1).hide()
            stringWidgetList.removeAt(stringWidgetList.size - 1)
        }

        while (stringWidgetList.size < commandSize) {
            val stringCommand = createStringCommand()
            stringCommand.onLinkStringSignal.connect(this, ::onLinkStringClicked)
            stringCommand.inFocusSignal.connect(this, ::onInFocusCommand)
            stringWidgetList.add(stringCommand)
            commandStringsLayout.addWidget(stringCommand)
        }

        bindCommands()
    }

    private fun onLinkStringClicked(transitionNum: Int, senderNum: Int) {
        if (transitionNum < stringWidgetList.size) {
            if (clickedCommandList.size > 1) {
                for (i in clickedCommandList.size - 1 downTo currentCommandIndex + 1) {
                    clickedCommandList.removeAt(i)
                }
            }
        }

        if (clickedCommandList.get(currentCommandIndex) != senderNum) {
            clickedCommandList.add(senderNum);
        }
        currentCommandIndex++;
        clickedCommandList.add(transitionNum);

        goToCommandByNumber(transitionNum);
    }

    protected open fun updateSelectingCommand(numCommand: Int) {
        if (stringWidgetList.size >= selectedCommand) {
            stringWidgetList.get(selectedCommand).isSelected = false
        }
        selectedCommand = numCommand;
        stringWidgetList.get(numCommand).isSelected = true
    }


    protected open fun onInFocusCommand(numCommand: Int) {
        updateSelectingCommand(numCommand)
    }

    protected open fun goToCommandByNumber(transitionNum: Int) {
        checkCurrentIndex()
        updateSelectingCommand(transitionNum)
        scrollArea.ensureWidgetVisible(stringWidgetList.get(transitionNum))
    }

    protected open fun deselectExecCommand() {
        if (currentExecCommand != -1) {
            stringWidgetList.get(currentExecCommand).hideExecBorder()
        }
    }

    override fun onSetExecCommand(numberCommand: Int, prevCommand: Int) {
        deselectExecCommand()
        stringWidgetList.get(numberCommand).setExecBorder(prevCommand)
        scrollArea.ensureWidgetVisible(stringWidgetList.get(numberCommand))
        currentExecCommand = numberCommand
    }

    override fun onBackCommandClicked() {
        if (currentCommandIndex > 0) {
            --currentCommandIndex
            goToCommandByNumber(clickedCommandList.get(currentCommandIndex))
        }
    }

    override fun onForwardCommandClicked() {
        if (currentCommandIndex < (clickedCommandList.size - 1)) {
            ++currentCommandIndex
            goToCommandByNumber(clickedCommandList.get(currentCommandIndex))
        }
    }

    override fun checkCurrentIndex() {
        val backEnable: Boolean = currentCommandIndex != 0
        val forwardEnable: Boolean = !(currentCommandIndex == (clickedCommandList.size - 1))
        enableCommandButtonsChange.emit(backEnable, forwardEnable)
    }

    override fun onChangedStatusPlay(statusPlay: ModelBase.StatusPlay) {
        if (statusPlay == STOPPED) {
            deselectExecCommand()
            currentExecCommand = -1
        }
    }
}