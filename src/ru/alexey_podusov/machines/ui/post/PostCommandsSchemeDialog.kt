package ru.alexey_podusov.machines.ui.post

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.MachineType
import ru.alexey_podusov.machines.MainWindow
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.EngineTab
import ru.alexey_podusov.machines.engines.post.PostCommandTab
import ru.alexey_podusov.machines.ui.TabDialog

class PostCommandsSchemeDialog(mainWindow: MainWindow, tab: EngineTab?, var commandWidget: PostLineCommands?) : TabDialog(mainWindow, tab) {
    private val mainLayout = QVBoxLayout()
    private val scene = PostGraphicsScene()
    private val graphicsView = PostGraphicsView(scene)
    private val buttonLayout = QHBoxLayout()
    private val saveButton = QPushButton(SAVE_BUTTON_TEXT)

    companion object {
        val TITLE = "Схема команд. Вкладка: "
        val SAVE_BUTTON_TEXT = "Сохранить изображение"
        val SAVE_BUTTON_WIDTH = 180
        val SAVE_ERROR_TEXT = "Произошла ошибка при сохранении файла"
        val SAVE_ERROR_TITLE = "Ошибка"
    }

    init {
        initUI()
        onCommandsChanged()
    }

    override fun initUI() {
        super.initUI()

        tab!!.engine!!.commandsChangedSignal.connect(this, ::onCommandsChanged)

        mainLayout.setContentsMargins(0,0,0,6)
        mainLayout.addWidget(graphicsView)

        saveButton.setFixedWidth(SAVE_BUTTON_WIDTH)
        saveButton.clicked.connect(this, ::onSaveButtonClicked)
        buttonLayout.addWidget(saveButton)
        buttonLayout.setAlignment(saveButton, Qt.AlignmentFlag.AlignRight)
        mainLayout.addLayout(buttonLayout)

        setLayout(mainLayout)

        setWindowTitle(TITLE + tab!!.name)

        setGeometry(mainWindow.x() + mainWindow.width()/2,
                mainWindow.y() + 50, PostGraphicsScene.WIDTH_SCENE.toInt() + 10, mainWindow.height())
    }

    private fun onSaveButtonClicked() {
        val filter ="Portable network graphics(*.png)"
        val filepath = QFileDialog.getSaveFileName(this, SAVE_BUTTON_TEXT, "", QFileDialog.Filter(filter))
        if (filepath.isEmpty()) return

        try {
            val image = QImage(scene.sceneRect().size().toSize(), QImage.Format.Format_ARGB32)
            image.fill(Qt.GlobalColor.white)
            val painter = QPainter(image)
            painter.setRenderHint(QPainter.RenderHint.Antialiasing)
            scene.render(painter)
            image.save(filepath)
            painter.end()
        } catch (e: Exception) {
            QMessageBox.warning(this, SAVE_ERROR_TITLE, SAVE_ERROR_TEXT)
        }
    }

    private fun onCommandsChanged() {
        scene.draw((tab as PostCommandTab).commands)
        updateExecCommand()
    }

    override fun getTabList(): List<EngineTab> {
        return tab!!.engine!!.commandTabs
    }

    override fun getCurrentMachine(): MachineType {
        return MachineType.POST
    }

    fun updateExecCommand() {
        scene.setExecCommand(commandWidget?.currentExecCommand!!)
    }


}