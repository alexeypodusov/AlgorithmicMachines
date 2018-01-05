package ru.alexey_podusov.machines

import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.factories.IFactory
import ru.alexey_podusov.machines.factories.PostFactory
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.BaseEngine.*
import ru.alexey_podusov.machines.engines.BaseEngine.StatusPlay.*
import ru.alexey_podusov.machines.forms.Ui_MainWindow
import ru.alexey_podusov.machines.ui.custom_widgets.tab.CommandTabWidget
import ru.alexey_podusov.machines.ui.custom_widgets.tab.WorkareaTabWidget

class MainWindow : QMainWindow() {

    val keyReleaseSignal = Signal1<QKeyEvent>()
    val keyPressSignal = Signal1<QKeyEvent>()

    private val ui = Ui_MainWindow()

    private var factory: IFactory = PostFactory()
    private var engine: BaseEngine? = null

    private val commandTabWidget = CommandTabWidget()
    private val workareaTabWidget = WorkareaTabWidget()

    init {
        setupUi()

        connect()
        initHardcode()
    }

    private fun setupUi() {
        ui.setupUi(this)
        setNullMargins(ui.mainVerticalLayout)
        ui.buttonsVerticalLayout.setContentsMargins(0,30,0,0)

        ui.commandLayout.addWidget(commandTabWidget)
        ui.workAreaLayout.addWidget(workareaTabWidget)
        changeEnableCommandButtons(false, false)
    }
    //пока хардкод
    private fun initHardcode() {
        engine = factory.createModel()
        engine!!.sendMessageSignal.connect(this, ::onReceiveMessage)
        engine!!.changedStatusPlaySignal.connect(this, ::onChangedStatusPlay)
        engine!!.workAreaChangedSignal.connect(this, ::onUpdatedWorkarea)
        engine!!.setExecCommandSignal.connect(this, ::onSetExecCommand)

        engine!!.addCommandTab("test")
        engine!!.addWorkareaTab("test")

        //command.enableCommandButtonsChange.connect(this, ::changeEnableCommandButtons)

        workareaTabWidget.setEngine(engine!!, factory)
        commandTabWidget.setEngine(engine!!, factory)
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

    }

    private fun onUpdatedWorkarea() = workareaTabWidget.getCurrent().updateWorkArea()
    private fun onSetExecCommand(numberCommand: Int, prevCommand: Int) {
        commandTabWidget.getCurrent().onSetExecCommand(numberCommand, prevCommand)
    }

    private fun actionPlayTriggered(checked: Boolean) = engine!!.play(commandTabWidget.currentIndex(), workareaTabWidget.currentIndex())
    private fun actionNextStepTriggered(checked: Boolean) = engine!!.playStep(commandTabWidget.currentIndex(), workareaTabWidget.currentIndex())
    private fun actionReverseStepTriggered(checked: Boolean) = engine!!.playReverseStep(commandTabWidget.currentIndex(), workareaTabWidget.currentIndex())
    private fun actionPauseTriggered(checked: Boolean) = { engine!!.statusPlay = ON_PAUSE}
    private fun actionStopTriggered(checked: Boolean) = { engine!!.statusPlay = STOPPED}

    private fun onBackCommandClicked(checked: Boolean) {
        commandTabWidget.getCurrent().onBackCommandClicked()
    }

    private fun onForwardCommandClicked(checked: Boolean) {
        commandTabWidget.getCurrent().onForwardCommandClicked()
    }

    private fun onInsertAfterClicked(checked: Boolean) {
        commandTabWidget.getCurrent().onInsertAfterClicked()
    }

    private fun onInsertBeforeClicked(checked: Boolean) {
        commandTabWidget.getCurrent().onInsertBeforeClicked()
    }

    private fun onDeleteCommandClicked(checked: Boolean) {
        commandTabWidget.getCurrent().onDeleteCommandClicked()
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
        commandTabWidget.getCurrent().onChangedStatusPlay(statusPlay)
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

    override fun mousePressEvent(arg__1: QMouseEvent?) {
        centralWidget().setFocus()
        super.mousePressEvent(arg__1)
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