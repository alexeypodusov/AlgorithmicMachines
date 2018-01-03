package ru.alexey_podusov.machines

import com.trolltech.qt.QSignalEmitter
import com.trolltech.qt.core.Qt
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

        initHardcode()

        ui.actionPlay.triggered.connect { checked: Boolean -> model!!.play() }
        ui.actionNextStep.triggered.connect { checked: Boolean -> model!!.playStep() }
        ui.actionReverseStep.triggered.connect { checked: Boolean -> model!!.playReverseStep() }
        ui.actionPause.triggered.connect { checked: Boolean -> model!!.statusPlay = ON_PAUSE }
        ui.actionStop.triggered.connect { checked: Boolean -> model!!.statusPlay = STOPPED }
    }


    private fun setNullMargins(layout: QBoxLayout) {
        layout.setMargin(0)
        layout.children()
                .filter { qVBoxLayoutClassName == it.javaClass.name || qHBoxLayoutClassName == it.javaClass.name }
                .forEach { setNullMargins(it as QBoxLayout) }
    }

    //пока хардкод
    private fun initHardcode() {
        model = factory.createModel()
        //model!!.sendMessageSignal.connect (this, "receiveMessage(MessageType,String,String)")
        //model!!.changedStatusPlaySignal.connect(this,  "onChangedStatusPlay(StatusPlay)")
        commandsWidgetList = ArrayList()

        commandsWidgetList.clear()
        workareaWidgetList.clear()

        commandsWidgetList.add(factory.createCommandsBaseWidget(model!!))
        workareaWidgetList.add(factory.createWorkareaWidget(model!!))

        ui.tabWorkAreaWidget.addTab(workareaWidgetList.get(0), "test")
        ui.tabCommandWidget.addTab(commandsWidgetList.get(0), "test")
    }

    private fun receiveMessage(messageType: ModelBase.MessageType, text: String, title: String) {
        when (messageType) {
            MessageType.MESSAGE_ERROR -> QMessageBox.warning(this, title, text)
            MessageType.MESSAGE_INFO -> QMessageBox.information(this, title, text)
        }
    }

    private fun onChangedStatusPlay(statusPlay: ModelBase.StatusPlay) {
        //TODO
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