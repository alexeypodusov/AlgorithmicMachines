package ru.alexey_podusov.machines.ui

import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CommandTab

abstract class BaseCommands(protected val tab: CommandTab) : QWidget() {
    protected val mainLayout = QVBoxLayout()
    protected val scrollArea = QScrollArea()
    protected val scrollAreaWidget = QWidget()
    protected var currentTransCommandIndex: Int = 0

    val enableCommandButtonsChange = Signal2<Boolean, Boolean>()

    init {
        scrollArea.setWidget(scrollAreaWidget)
        scrollArea.setWidgetResizable(true)
        mainLayout.addWidget(scrollArea)
        setLayout(mainLayout)
    }

    abstract fun onChangedStatusPlay(statusPlay: BaseEngine.StatusPlay)
    abstract fun onInsertAfterClicked()
    abstract fun onInsertBeforeClicked()
    abstract fun onDeleteCommandClicked()
    abstract fun onSetExecCommand(numberCommand: Int, prevCommand: Int)
    abstract fun updateCommands()
    abstract fun goToCommandByTransCommandIndex(commandIndexTransition: Int): Boolean
    abstract fun getTransCommandsSize(): Int


    fun onBackCommandClicked() {
        if (currentTransCommandIndex > 0) {
            --currentTransCommandIndex
            if (!goToCommandByTransCommandIndex(currentTransCommandIndex)) {
                checkCurrentIndex()
                onBackCommandClicked()
            }
        } else {
            checkCurrentIndex()
        }

    }

    fun onForwardCommandClicked() {
        if (currentTransCommandIndex < (getTransCommandsSize() - 1)) {
            ++currentTransCommandIndex
            if (!goToCommandByTransCommandIndex(currentTransCommandIndex)) {
                checkCurrentIndex()
                onForwardCommandClicked()
            }
        } else {
            checkCurrentIndex()
        }
    }

    fun checkCurrentIndex() {
        val backEnable: Boolean = currentTransCommandIndex != 0
        val forwardEnable: Boolean = !(currentTransCommandIndex >= (getTransCommandsSize() - 1))
        enableCommandButtonsChange.emit(backEnable, forwardEnable)
    }

}