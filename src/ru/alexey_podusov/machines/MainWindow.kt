package ru.alexey_podusov.machines

import com.trolltech.qt.core.QFileInfo
import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.factories.IFactory
import ru.alexey_podusov.machines.factories.PostFactory
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.BaseEngine.*
import ru.alexey_podusov.machines.engines.BaseEngine.StatusPlay.*
import ru.alexey_podusov.machines.forms.Ui_MainWindow
import ru.alexey_podusov.machines.ui.custom_widgets.tab.CommandTabWidget
import ru.alexey_podusov.machines.ui.custom_widgets.tab.WorkareaTabWidget
import ru.alexey_podusov.machines.utils.FileUtils
import java.util.Arrays.asList

class MainWindow : QMainWindow() {

    val keyReleaseSignal = Signal1<QKeyEvent>()
    val keyPressSignal = Signal1<QKeyEvent>()

    private val ui = Ui_MainWindow()

    var currentMachine: MachineType = MachineType.POST
    private var factory: IFactory = createFactory(currentMachine)
    private var engine: BaseEngine? = null

    private val commandTabWidget = CommandTabWidget()
    private val workareaTabWidget = WorkareaTabWidget()

    companion object {
        val ALGORITHMIC_MACHINES = "Алгоритмические машины"
        val ERROR_TITLE = "Ошибка"
        val WRITE_ERROR = "Ошибка сохранения файла"
        val READ_ERROR = "Ошибка чтения файла"
    }

    init {
        setupUi()

        connect()
        createNewFile()
    }

    private fun setupUi() {
        ui.setupUi(this)
        setNullMargins(ui.mainVerticalLayout)
        ui.buttonsVerticalLayout.setContentsMargins(0, 30, 0, 0)

        ui.commandLayout.addWidget(commandTabWidget)
        ui.workAreaLayout.addWidget(workareaTabWidget)
        changeEnableCommandButtons(false, false)
    }

    private fun initMachine() {
        engine!!.sendMessageSignal.connect(this, ::onReceiveMessage)
        engine!!.changedStatusPlaySignal.connect(this, ::onChangedStatusPlay)
        engine!!.workAreaChangedSignal.connect(this, ::onUpdatedWorkarea)
        engine!!.setExecCommandSignal.connect(this, ::onSetExecCommand)

        ui.taskTextEdit.setText(engine!!.task)

        workareaTabWidget.setEngine(engine!!, factory)
        commandTabWidget.setEngine(engine!!, factory)

        commandTabWidget.connectCommands(this)

        onChangedStatusPlay(STOPPED)
    }

    private fun connect() {
        ui.actionNew.triggered.connect(this, ::actionNewTriggered)
        ui.actionOpen.triggered.connect(this, ::actionOpenTriggered)
        ui.actionSave.triggered.connect(this, ::actionSaveTriggered)
        ui.actionSaveAs.triggered.connect(this, ::actionSaveAsTriggered)
        ui.actionExit.triggered.connect(this, ::actionExitTriggered)

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

        ui.taskTextEdit.textChanged.connect(this, ::onTaskEdited)
    }

    private fun onTaskEdited() {
        engine!!.task =  ui.taskTextEdit.toPlainText()
    }

    private fun onUpdatedWorkarea() = workareaTabWidget.getCurrent().updateWorkArea()

    private fun onSetExecCommand(numberCommand: Int, prevCommand: Int) {
        commandTabWidget.getCurrent().onSetExecCommand(numberCommand, prevCommand)
    }

    private fun actionNewTriggered(checked: Boolean) {

    }

    private fun actionOpenTriggered(checked: Boolean) {
        val filter = ALGORITHMIC_MACHINES + " (" + MachineType.values().joinToString(" ") { "*." + it.fileFormat } + ")"
        val filepath = QFileDialog.getOpenFileName(this, ui.actionSaveAs.text(), "", QFileDialog.Filter(filter))
        try {
            currentMachine = MachineType.getTypeByFileFormat(QFileInfo(filepath).suffix())
            val engineJson = FileUtils.read(filepath)
            factory = createFactory(currentMachine)
            engine = factory.readEngineFromJson(engineJson)
            initMachine()
        } catch (e: Exception) {
            QMessageBox.warning(this, ERROR_TITLE, READ_ERROR)
        }
    }

    private fun actionSaveTriggered(checked: Boolean) = {}
    private fun actionSaveAsTriggered(checked: Boolean) {
        val filter = currentMachine.nameMachine + " (*." + currentMachine.fileFormat + ")"
        val filepath = QFileDialog.getSaveFileName(this, ui.actionSaveAs.text(), "", QFileDialog.Filter(filter))
        try {
            FileUtils.writeObject(engine!!, filepath)
        } catch (e: Exception) {
            QMessageBox.warning(this, ERROR_TITLE, WRITE_ERROR)
        }
    }

    private fun actionExitTriggered(checked: Boolean) = {}

    private fun actionPlayTriggered(checked: Boolean) = engine!!.play(commandTabWidget.currentIndex(), workareaTabWidget.currentIndex())
    private fun actionNextStepTriggered(checked: Boolean) = engine!!.playStep(commandTabWidget.currentIndex(), workareaTabWidget.currentIndex())
    private fun actionReverseStepTriggered(checked: Boolean) = engine!!.playReverseStep(commandTabWidget.currentIndex(), workareaTabWidget.currentIndex())
    private fun actionPauseTriggered(checked: Boolean) {
        engine!!.statusPlay = ON_PAUSE
    }

    private fun createFactory(type: MachineType) : IFactory {
        when(type){
            MachineType.POST -> return PostFactory()
        }
        return PostFactory()
    }

    private fun createNewFile() {
        engine = factory.createEngine()
        initMachine()
    }

    private fun actionStopTriggered(checked: Boolean) {
        engine!!.statusPlay = STOPPED
    }

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

    fun changeEnableCommandButtons(backEnable: Boolean, forwardEnable: Boolean) {
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
                asList(commandTabWidget, workareaTabWidget).map { it.tabBar.isEnabled = false }
                ui.actionPlay.isEnabled = false
                ui.actionPause.isEnabled = true
                ui.actionStop.isEnabled = true
                ui.actionReverseStep.isEnabled = true
            }
            ON_PAUSE -> {
                asList(commandTabWidget, workareaTabWidget).map { it.tabBar.isEnabled = false }

                ui.actionPlay.isEnabled = true
                ui.actionPause.isEnabled = false
                ui.actionStop.isEnabled = true
                ui.actionReverseStep.isEnabled = true
            }
            STOPPED -> {
                asList(commandTabWidget, workareaTabWidget).map { it.tabBar.isEnabled = true }

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