package ru.alexey_podusov.machines.ui

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.MachineType
import ru.alexey_podusov.machines.MainWindow
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.EngineTab
import ru.alexey_podusov.machines.ui.post.PostCommandsSchemeDialog
import ru.alexey_podusov.machines.ui.post.PostGraphicsScene

abstract class BaseTextEditorDialog(mainWindow: MainWindow, tab: EngineTab?) : TabDialog(mainWindow, tab) {
    protected val textEdit = QTextEdit()
    protected val mainLayout = QVBoxLayout()
    protected val buttonLayout = QHBoxLayout()
    protected val saveButton = QPushButton(SAVE_BUTTON_TEXT)

    companion object {
        val TITLE = "Текстовый редактор команд. Вкладка: "
        val SAVE_BUTTON_TEXT = "Применить"
    }

    abstract fun onSaveButtonClicked()

    override fun initUI() {
        super.initUI()
        mainLayout.addWidget(textEdit)
        setLayout(mainLayout)
        mainLayout.setContentsMargins(0,0,0,6)

        saveButton.clicked.connect(this, ::onSaveButtonClicked)
        buttonLayout.addWidget(saveButton)
        buttonLayout.setAlignment(saveButton, Qt.AlignmentFlag.AlignRight)
        mainLayout.addLayout(buttonLayout)

        setWindowTitle(TITLE + tab!!.name)

        setGeometry(mainWindow.x(),
                mainWindow.y() + 50, mainWindow.width() / 2, mainWindow.height() / 2)

        val textEditFont = QFont()
        textEditFont.setFamily("Courier new")
        textEditFont.setPointSize(12)
        textEdit.document().setDefaultFont(textEditFont)
    }
}