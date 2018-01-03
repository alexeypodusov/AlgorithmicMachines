package ru.alexey_podusov.machines

import com.trolltech.qt.gui.QBoxLayout
import com.trolltech.qt.gui.QKeyEvent
import com.trolltech.qt.gui.QMainWindow
import com.trolltech.qt.gui.QMessageBox
import ru.alexey_podusov.machines.factories.IFactory
import ru.alexey_podusov.machines.factories.PostFactory
import ru.alexey_podusov.machines.forms.Ui_MainWindow
import ru.alexey_podusov.machines.models.ModelBase
import ru.alexey_podusov.machines.models.ModelBase.*
import ru.alexey_podusov.machines.models.ModelBase.StatusPlay.*
import ru.alexey_podusov.machines.ui.CommandsBaseWidget
import ru.alexey_podusov.machines.ui.WorkareaBaseWidget
import java.util.*

class MainWindow : QMainWindow() {

    val keyReleaseSignal = Signal1<QKeyEvent>()
    val keyPressSignal = Signal1<QKeyEvent>()

    private val ui = Ui_MainWindow()
    private val qVBoxLayoutClassName = "com.trolltech.qt.gui.QVBoxLayout"
    private val qHBoxLayoutClassName = "com.trolltech.qt.gui.QHBoxLayout"

    private var factory: IFactory = PostFactory()
    private var model: ModelBase? = null
    private var commandsWidgetList = ArrayList<CommandsBaseWidget>()
    private var workareaWidgetList = ArrayList<WorkareaBaseWidget>()

    init {
        ui.setupUi(this)
        setNullMargins(ui.mainVerticalLayout)

        connect()
        initHardcode()
    }

    //пока хардкод
    private fun initHardcode() {
        model = factory.createModel()
        model!!.sendMessageSignal.connect(this, ::onReceiveMessage)
        model!!.changedStatusPlaySignal.connect(this, ::onChangedStatusPlay)
        commandsWidgetList = ArrayList()

        commandsWidgetList.clear()
        workareaWidgetList.clear()

        val command = factory.createCommandsBaseWidget(model!!)
        command.enableCommandButtonsChange.connect(this, ::changeEnableCommandButtons)
        commandsWidgetList.add(command)

        workareaWidgetList.add(factory.createWorkareaWidget(model!!))

        ui.tabWorkAreaWidget.addTab(workareaWidgetList.get(0), "test")
        ui.tabCommandWidget.addTab(commandsWidgetList.get(0), "test")
    }

    private fun connect() {
        ui.actionPlay.triggered.connect(this, ::actionPlayTriggered)
        ui.actionNextStep.triggered.connect(this, ::actionNextStepTriggered)
        ui.actionReverseStep.triggered.connect(this, ::actionReverseStepTriggered)
        ui.actionPause.triggered.connect(this, ::actionPauseTriggered)
        ui.actionStop.triggered.connect(this, ::actionStopTriggered)

        ui.backCommandButton.clicked.connect(this, ::onBackCommandClicked)
        ui.forwardCommandButton.clicked.connect(this, ::onForwardCommandClicked)

        ui.pushButtonAddString.clicked.connect(this, ::onAddCommandsClicked)
        ui.pushButtonDeleteString.clicked.connect(this, ::onDeleteCommandClicked)

    }

    private fun actionPlayTriggered(checked: Boolean) = model!!.play()
    private fun actionNextStepTriggered(checked: Boolean) = model!!.playStep()
    private fun actionReverseStepTriggered(checked: Boolean) = model!!.playReverseStep()
    private fun actionPauseTriggered(checked: Boolean) = {model!!.statusPlay = ON_PAUSE}
    private fun actionStopTriggered(checked: Boolean) = {model!!.statusPlay = STOPPED}

    private fun onBackCommandClicked(checked: Boolean) {
        commandsWidgetList.get(ui.tabCommandWidget.currentIndex()).onBackCommandClicked()
    }

    private fun onForwardCommandClicked(checked: Boolean) {
        commandsWidgetList.get(ui.tabCommandWidget.currentIndex()).onForwardCommandClicked()
    }

    private fun onAddCommandsClicked(checked: Boolean) {
        commandsWidgetList.get(ui.tabCommandWidget.currentIndex()).onAddCommandClicked()
    }

    private fun onDeleteCommandClicked(checked: Boolean) {
        commandsWidgetList.get(ui.tabCommandWidget.currentIndex()).onDeleteCommandClicked()
    }

    private fun changeEnableCommandButtons(backEnable: Boolean, forwardEnable: Boolean) {
        ui.backCommandButton.isEnabled = backEnable
        ui.forwardCommandButton.isEnabled = forwardEnable
    }

    private fun setNullMargins(layout: QBoxLayout) {
        layout.setMargin(0)
        layout.children()
                .filter { qVBoxLayoutClassName == it.javaClass.name || qHBoxLayoutClassName == it.javaClass.name }
                .forEach { setNullMargins(it as QBoxLayout) }
    }

    private fun onReceiveMessage(messageType: ModelBase.MessageType, text: String, title: String) {
        when (messageType) {
            MessageType.MESSAGE_ERROR -> QMessageBox.warning(this, title, text)
            MessageType.MESSAGE_INFO -> QMessageBox.information(this, title, text)
        }
    }

    private fun onChangedStatusPlay(statusPlay: ModelBase.StatusPlay) {
        commandsWidgetList.get(ui.tabCommandWidget.currentIndex()).onChangedStatusPlay(statusPlay)
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