package ru.alexey_podusov.machines.ui.post

import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.MachineType
import ru.alexey_podusov.machines.MainWindow
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.EngineTab
import ru.alexey_podusov.machines.engines.post.PostCommandTab
import ru.alexey_podusov.machines.ui.TabDialog
import ru.alexey_podusov.machines.ui.markov.MarkovHistoryChangesDialog

class PostCommandsSchemeDialog(mainWindow: MainWindow, tab: EngineTab?) : TabDialog(mainWindow, tab) {
    private val mainLayout = QVBoxLayout()
    private val scene = PostGraphicsScene()
    private val graphicsView = QGraphicsView(scene)

    companion object {
        val TITLE = "Схема команд. Вкладка: "
    }

    init {
        initUI()
        onCommandsChanged()
    }

    override fun initUI() {
        super.initUI()

        tab!!.engine!!.commandsChangedSignal.connect(this, ::onCommandsChanged)

        mainLayout.addWidget(graphicsView)
        setLayout(mainLayout)

        setWindowTitle(TITLE + tab!!.name)

        setGeometry(mainWindow.x() + mainWindow.width()/2,
                mainWindow.y(), PostGraphicsScene.WIDTH_SCENE.toInt(), mainWindow.height())
    }

    private fun onCommandsChanged() {
        scene.draw((tab as PostCommandTab).commands)
    }

    override fun getTabList(): List<EngineTab> {
        return tab!!.engine!!.commandTabs
    }

    override fun getCurrentMachine(): MachineType {
        return MachineType.POST
    }
}