package ru.alexey_podusov.machines

import com.trolltech.qt.core.QFileInfo
import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.BaseEngine.MessageType
import ru.alexey_podusov.machines.engines.BaseEngine.StatusPlay.*
import ru.alexey_podusov.machines.engines.tyuring.TyuringEngine
import ru.alexey_podusov.machines.factories.IFactory
import ru.alexey_podusov.machines.factories.MarkovFactory
import ru.alexey_podusov.machines.factories.PostFactory
import ru.alexey_podusov.machines.factories.TyuringFactory
import ru.alexey_podusov.machines.forms.Ui_MainWindow
import ru.alexey_podusov.machines.ui.PreferencesDialog
import ru.alexey_podusov.machines.ui.custom_widgets.tab.CommandTabWidget
import ru.alexey_podusov.machines.ui.custom_widgets.tab.WorkareaTabWidget
import ru.alexey_podusov.machines.ui.tyuring.TyuringAlphabetWidget
import ru.alexey_podusov.machines.utils.FileUtils
import java.util.Arrays.asList
import java.util.prefs.Preferences

class MainWindow : QMainWindow() {

    val keyReleaseSignal = Signal1<QKeyEvent>()
    val keyPressSignal = Signal1<QKeyEvent>()

    private val ui = Ui_MainWindow()
    private val alphabetContainerWidget = QWidget()
    private val alphabetContainerLayout = QHBoxLayout()

    private var tyuringAlphabetWidget = TyuringAlphabetWidget()

    var currentMachine: MachineType = MachineType.POST

    private var factory: IFactory = createFactory(currentMachine)
    private var engine: BaseEngine? = null

    private val commandTabWidget = CommandTabWidget()
    val workareaTabWidget = WorkareaTabWidget()
    private var savedFilePath = ""


    private var isSavedChanges = true
        set(value) {
            field = value
            if (value == false && !savedFilePath.isEmpty()) {
                ui.actionSave.setDisabled(false)
            } else ui.actionSave.setDisabled(true)
        }

    companion object {
        val ALGORITHMIC_MACHINES = "Алгоритмические машины"
        val ERROR_TITLE = "Ошибка"
        val WRITE_ERROR = "Ошибка сохранения файла"
        val READ_ERROR = "Ошибка чтения файла"
        val CHANGED_NOT_SAVED = "Изменения не были сохранены, продолжить?"
        val SAVE_TITLE = "Сохранение"
        val CANCEL_BUTTON_TEXT = "Отмена"
        val CONTINUE_BUTTON_TEXT = "Продолжить"


        fun getMainWindow(): MainWindow {
            return QApplication.topLevelWidgets().first { it is MainWindow } as MainWindow
        }

    }

    init {
        setupUi()

        connect()
        createNewFile()
    }

    private fun setupUi() {
        ui.setupUi(this)

        ui.buttonsVerticalLayout.setContentsMargins(0, 30, 0, 0)
        alphabetContainerWidget.setLayout(alphabetContainerLayout)
        alphabetContainerLayout.addWidget(tyuringAlphabetWidget)
        ui.buttonHorizontalLayout.addWidget(alphabetContainerWidget)
        alphabetContainerLayout.setMargin(0)

        setNullMargins(ui.mainVerticalLayout)

        ui.commandLayout.addWidget(commandTabWidget)
        ui.workAreaLayout.addWidget(workareaTabWidget)
        changeEnableCommandButtons(false, false)
    }

    private fun initMachine() {
        engine!!.sendMessageSignal.connect(this, ::onReceiveMessage)
        engine!!.changedStatusPlaySignal.connect(this, ::onChangedStatusPlay)
        engine!!.workAreaChangedSignal.connect(this, ::onUpdatedWorkarea)
        engine!!.commandsChangedSignal.connect(this, ::updatedCommands)
        engine!!.changedTabsSignal.connect(this, ::updatedTabs)
        engine!!.setExecCommandSignal.connect(this, ::onSetExecCommand)

        isSavedChanges = true

        ui.taskTextEdit.setText(engine!!.task)

        workareaTabWidget.setEngine(engine!!, factory)

        tyuringAlphabetWidget.hide()
        alphabetContainerLayout.removeWidget(tyuringAlphabetWidget)

        if (currentMachine == MachineType.TYURING) {
            tyuringAlphabetWidget = TyuringAlphabetWidget()
            alphabetContainerLayout.addWidget(tyuringAlphabetWidget)
            tyuringAlphabetWidget.engine = engine as TyuringEngine
        }

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

        ui.actionPost.triggered.connect(this, ::actionPostTriggered)
        ui.actionMarkov.triggered.connect(this, ::actionMarkovTriggered)
        ui.actionTyuring.triggered.connect(this, ::actionTyuringTriggered)

        ui.actionPreferences.triggered.connect(this, ::actioPreferencesTriggered)
    }

    private fun actioPreferencesTriggered(checked: Boolean) {
        PreferencesDialog(this).show()
    }

    private fun actionPostTriggered(checked: Boolean) {
        currentMachine = MachineType.POST
        newFile()
    }

    private fun actionMarkovTriggered(checked: Boolean) {
        currentMachine = MachineType.MARKOV
        newFile()
    }

    private fun actionTyuringTriggered(checked: Boolean) {
        currentMachine = MachineType.TYURING
        newFile()
    }

    private fun onTaskEdited() {
        engine!!.task = ui.taskTextEdit.toPlainText()
    }

    private fun updatedTabs() {
        isSavedChanges = false
    }

    private fun onUpdatedWorkarea() {
        workareaTabWidget.getCurrent().updateWorkArea()
        isSavedChanges = false
    }

    private fun updatedCommands() {
        if(commandTabWidget.currentIndex() != commandTabWidget.count() - 1) {
            commandTabWidget.getCurrent().updateCommands()
        }
        isSavedChanges = false
    }

    private fun onSetExecCommand(numberCommand: Int, prevCommand: Int) {
        commandTabWidget.getCurrent().onSetExecCommand(numberCommand, prevCommand)
    }

    private fun actionNewTriggered(checked: Boolean) {
        newFile()
    }

    private fun actionOpenTriggered(checked: Boolean) {
        if (checkCloseWithoutSave()) {
            val filter = ALGORITHMIC_MACHINES + " (" + MachineType.values().joinToString(" ") { "*." + it.fileFormat } + ")"
            val filepath = QFileDialog.getOpenFileName(this, ui.actionSaveAs.text(), "", QFileDialog.Filter(filter))
            if (filepath.isEmpty()) return
            try {
                currentMachine = MachineType.getTypeByFileFormat(QFileInfo(filepath).suffix())
                val engineJson = FileUtils.read(filepath)
                factory = createFactory(currentMachine)
                engine = factory.readEngineFromJson(engineJson)
                savedFilePath = filepath
                initMachine()
            } catch (e: Exception) {
                QMessageBox.warning(this, ERROR_TITLE, READ_ERROR)
            }
        }
    }

    private fun actionSaveTriggered(checked: Boolean) {
        saveToFixedPath()
    }

    private fun actionSaveAsTriggered(checked: Boolean) {
        saveAsEngine()
    }

    private fun actionExitTriggered(checked: Boolean) {
        close()
    }

    private fun actionPlayTriggered(checked: Boolean) = engine!!.play(commandTabWidget.currentIndex(), workareaTabWidget.currentIndex())
    private fun actionNextStepTriggered(checked: Boolean) = engine!!.playStep(commandTabWidget.currentIndex(), workareaTabWidget.currentIndex())
    private fun actionReverseStepTriggered(checked: Boolean) = engine!!.playReverseStep(commandTabWidget.currentIndex(), workareaTabWidget.currentIndex())
    private fun actionPauseTriggered(checked: Boolean) {
        engine!!.statusPlay = ON_PAUSE
    }

    private fun newFile() {
        if (checkCloseWithoutSave()) {
            factory = createFactory(currentMachine)
            engine = factory.createEngine()
            initMachine()
        }
    }

    private fun saveToFixedPath(): Boolean {
        if (!savedFilePath.isEmpty()) {
            try {
                saveFileEngine(savedFilePath)
                return true
            } catch (e: Exception) {
                QMessageBox.warning(this, ERROR_TITLE, WRITE_ERROR)
                return false
            }
        } else return false
    }

    private fun saveFileEngine(filepath: String) {
        savedFilePath = filepath
        FileUtils.writeObject(engine!!, filepath)
        isSavedChanges = true
    }

    private fun saveAsEngine(): Boolean {
        val filter = currentMachine.nameMachine + " (*." + currentMachine.fileFormat + ")"
        val filepath = QFileDialog.getSaveFileName(this, ui.actionSaveAs.text(), "", QFileDialog.Filter(filter))

        if (filepath.isEmpty()) return false
        try {
            saveFileEngine(filepath)
        } catch (e: Exception) {
            QMessageBox.warning(this, ERROR_TITLE, WRITE_ERROR)
            return false
        }
        return true
    }

    private fun createFactory(type: MachineType): IFactory {
        engine?.blockSignals(true)
        return when (type) {
            MachineType.POST -> PostFactory()
            MachineType.MARKOV -> MarkovFactory()
            MachineType.TYURING -> TyuringFactory()
        }
    }

    private fun createNewFile() {
        if (checkCloseWithoutSave()) {
            engine = factory.createEngine()
            initMachine()
        }
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

    override fun closeEvent(event: QCloseEvent?) {
        if (checkCloseWithoutSave()) {
            event!!.accept()
        } else {
            event!!.ignore()
        }
    }

    fun checkCloseWithoutSave(): Boolean {
        if (!isSavedChanges) {
            val msgBox = QMessageBox(this)
            msgBox.setText(CHANGED_NOT_SAVED)
            msgBox.setWindowTitle(SAVE_TITLE)
            val saveAsButton = msgBox.addButton(ui.actionSaveAs.text(), QMessageBox.ButtonRole.AcceptRole)
            val saveButton = msgBox.addButton(ui.actionSave.text(), QMessageBox.ButtonRole.AcceptRole)
            val cancelButton = msgBox.addButton(CANCEL_BUTTON_TEXT, QMessageBox.ButtonRole.AcceptRole)
            val continueButton = msgBox.addButton(CONTINUE_BUTTON_TEXT, QMessageBox.ButtonRole.AcceptRole)

            saveButton.isEnabled = ui.actionSave.isEnabled

            msgBox.exec()
            if (msgBox.clickedButton() == saveAsButton) {
                return saveAsEngine()
            }
            if (msgBox.clickedButton() == saveButton) {
                actionSaveTriggered(false)
                return saveToFixedPath()

            }
            if (msgBox.clickedButton() == cancelButton) {
                return false
            }
            if (msgBox.clickedButton() == continueButton) {
                return true
            }
        }
        return true
    }


}