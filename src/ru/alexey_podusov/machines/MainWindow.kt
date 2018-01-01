package ru.alexey_podusov.machines

import com.trolltech.qt.gui.QBoxLayout
import com.trolltech.qt.gui.QMainWindow
import ru.alexey_podusov.machines.factories.IFactory
import ru.alexey_podusov.machines.factories.PostFactory
import ru.alexey_podusov.machines.forms.Ui_MainWindow
import ru.alexey_podusov.machines.models.ModelBase
import ru.alexey_podusov.machines.ui.CommandsBaseWidget
import ru.alexey_podusov.machines.ui.WorkareaBaseWidget

class MainWindow : QMainWindow() {
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
        commandsWidgetList = ArrayList<CommandsBaseWidget>()

        commandsWidgetList.clear()
        workareaWidgetList.clear()

        commandsWidgetList.add(factory.createCommandsBaseWidget(model!!))
        workareaWidgetList.add(factory.createWorkareaWidget(model!!))

        ui.tabWorkAreaWidget.addTab(workareaWidgetList.get(0), "test")
        ui.tabCommandWidget.addTab(commandsWidgetList.get(0), "test")

    }
}