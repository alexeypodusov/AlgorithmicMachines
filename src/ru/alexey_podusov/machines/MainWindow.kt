package ru.alexey_podusov.machines

import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.factories.IFactory
import ru.alexey_podusov.machines.factories.PostFactory
import ru.alexey_podusov.machines.forms.Ui_MainWindow
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.BaseEngine.*
import ru.alexey_podusov.machines.engines.BaseEngine.StatusPlay.*
import ru.alexey_podusov.machines.ui.BaseCommands
import ru.alexey_podusov.machines.ui.BaseWorkarea
import java.util.*

class MainWindow : QMainWindow() {

    val keyReleaseSignal = Signal1<QKeyEvent>()
    val keyPressSignal = Signal1<QKeyEvent>()

    private val ui = Ui_MainWindow()

    private var factory: IFactory = PostFactory()
    private var engine: BaseEngine? = null
    private var commandsWidgets = ArrayList<BaseCommands>()
    private var workareaWidgets = ArrayList<BaseWorkarea>()

    init {
        setupUi()

        connect()
        initHardcode()
    }

    private fun setupUi() {
        ui.setupUi(this)
        setNullMargins(ui.mainVerticalLayout)
        ui.buttonsVerticalLayout.setContentsMargins(0,30,0,0)
    }
    //пока хардкод
    private fun initHardcode() {
        engine = factory.createModel()
        engine!!.sendMessageSignal.connect(this, ::onReceiveMessage)
        engine!!.changedStatusPlaySignal.connect(this, ::onChangedStatusPlay)
        commandsWidgets = ArrayList()

        commandsWidgets.clear()
        workareaWidgets.clear()

        val command = factory.createCommandsBaseWidget(engine!!)
        command.enableCommandButtonsChange.connect(this, ::changeEnableCommandButtons)
        commandsWidgets.add(command)

        workareaWidgets.add(factory.createWorkareaWidget(engine!!))

        ui.tabWorkAreaWidget.addTab(workareaWidgets.get(0), "test")
        ui.tabCommandWidget.addTab(commandsWidgets.get(0), "test")


    }

    private fun connect() {
        ui.actionPlay.triggered.connect(this, ::actionPlayTriggered)
        ui.actionNextStep.triggered.connect(this, ::actionNextStepTriggered)
        ui.actionReverseStep.triggered.connect(this, ::actionReverseStepTriggered)
        ui.actionPause.triggered.connect(this, ::actionPauseTriggered)
        ui.actionStop.triggered.connect(this, ::actionStopTriggered)

        ui.backCommandButton.clicked.connect(this, ::onBackCommandClicked)
        ui.forwardCommandButton.clicked.connect(this, ::onForwardCommandClicked)

        ui.insertAfterButton.clicked.connect(this, ::onInsertAfterClicked)
        ui.insertBeforeButton.clicked.connect(this, ::onInsertBeforeClicked)
        ui.deleteCommand.clicked.connect(this, ::onDeleteCommandClicked)

        changeEnableCommandButtons(false, false)
    }

    private fun actionPlayTriggered(checked: Boolean) = engine!!.play()
    private fun actionNextStepTriggered(checked: Boolean) = engine!!.playStep()
    private fun actionReverseStepTriggered(checked: Boolean) = engine!!.playReverseStep()
    private fun actionPauseTriggered(checked: Boolean) = { engine!!.statusPlay = ON_PAUSE}
    private fun actionStopTriggered(checked: Boolean) = { engine!!.statusPlay = STOPPED}

    private fun onBackCommandClicked(checked: Boolean) {
        commandsWidgets.get(ui.tabCommandWidget.currentIndex()).onBackCommandClicked()
    }

    private fun onForwardCommandClicked(checked: Boolean) {
        commandsWidgets.get(ui.tabCommandWidget.currentIndex()).onForwardCommandClicked()
    }

    private fun onInsertAfterClicked(checked: Boolean) {
        commandsWidgets.get(ui.tabCommandWidget.currentIndex()).onInsertAfterClicked()
    }

    private fun onInsertBeforeClicked(checked: Boolean) {
        commandsWidgets.get(ui.tabCommandWidget.currentIndex()).onInsertBeforeClicked()
    }

    private fun onDeleteCommandClicked(checked: Boolean) {
        commandsWidgets.get(ui.tabCommandWidget.currentIndex()).onDeleteCommandClicked()
    }

    private fun changeEnableCommandButtons(backEnable: Boolean, forwardEnable: Boolean) {
        ui.backCommandButton.isEnabled = backEnable
        ui.forwardCommandButton.isEnabled = forwardEnable
    }

    private fun setNullMargins(layout: QBoxLayout) {
        layout.setMargin(0)
        layout.children()
                .filter { it is QVBoxLayout || it is QHBoxLayout }
                .forEach { setNullMargins(it as QBoxLayout) }
    }

    private fun onReceiveMessage(messageType: BaseEngine.MessageType, text: String, title: String) {
        when (messageType) {
            MessageType.MESSAGE_ERROR -> QMessageBox.warning(this, title, text)
            MessageType.MESSAGE_INFO -> QMessageBox.information(this, title, text)
        }
    }

    private fun onChangedStatusPlay(statusPlay: BaseEngine.StatusPlay) {
        commandsWidgets.get(ui.tabCommandWidget.currentIndex()).onChangedStatusPlay(statusPlay)
        when (statusPlay) {
            PLAYING -> {
                ui.actionPlay.isEnabled = false
                ui.actionPause.isEnabled = true
                ui.actionStop.isEnabled = true
                ui.actionReverseStep.isEnabled = true
            }
            ON_PAUSE -> {
                ui.actionPlay.isEnabled = true
                ui.actionPause.isEnabled = false
                ui.actionStop.isEnabled = true
                ui.actionReverseStep.isEnabled = true
            }
            STOPPED -> {
                ui.actionPlay.isEnabled = true
                ui.actionPause.isEnabled = false
                ui.actionStop.isEnabled = false
                ui.actionReverseStep.isEnabled = false
            }
        }

    }

    override fun keyPressEvent(arg__1: QKeyEvent?) {
        super.keyPressEvent(arg__1)
        keyPressSignal.emit(arg__1)
    }

    override fun keyReleaseEvent(arg__1: QKeyEvent?) {
        super.keyReleaseEvent(arg__1)
        keyReleaseSignal.emit(arg__1)
    }

}