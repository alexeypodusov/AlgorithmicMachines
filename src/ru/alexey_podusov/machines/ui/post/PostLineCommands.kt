package ru.alexey_podusov.machines.ui.post

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.QPushButton
import ru.alexey_podusov.machines.MainWindow
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.BaseEngine.StatusPlay.STOPPED
import ru.alexey_podusov.machines.engines.post.PostCommandTab
import ru.alexey_podusov.machines.engines.post.PostEngine
import ru.alexey_podusov.machines.ui.BaseLineItem
import ru.alexey_podusov.machines.ui.BaseLineCommands
import ru.alexey_podusov.machines.ui.BaseTextEditorDialog

class PostLineCommands(tab: PostCommandTab) : BaseLineCommands(tab) {
    private val showSchemeButton = QPushButton()

    private var schemeDialog: PostCommandsSchemeDialog? = null

    companion object {
        val SHOW_SCHEME_BUTTON_TEXT = "Открыть схему"
        val WIDTH_SHOW_SCHEME_BUTTON = 200
    }

    init {
        initUI()
    }

    override fun createTextEditorDialog(): BaseTextEditorDialog {
        return PostTextEditorDialog(MainWindow.getMainWindow(), tab)
    }

    private fun initUI() {
        showSchemeButton.setText(SHOW_SCHEME_BUTTON_TEXT)
        showSchemeButton.setFixedWidth(WIDTH_SHOW_SCHEME_BUTTON)
        showSchemeButton.clicked.connect(this, ::onShowSchemeButtonClicked)
        buttonsLayout.addWidget(showSchemeButton)
        buttonsLayout.setAlignment(showSchemeButton, Qt.AlignmentFlag.AlignRight)
    }

    private fun onShowSchemeButtonClicked() {
        if (schemeDialog != null && schemeDialog!!.isVisible) {
            schemeDialog!!.close()
        }

        schemeDialog = PostCommandsSchemeDialog(MainWindow.getMainWindow(), tab, this)
        schemeDialog!!.show()

    }

    override fun createStringCommand(): BaseLineItem {
        return PostLineItem()
    }

    override fun bindCommands() {
        tab as PostCommandTab
        for ((i, command) in tab.commands.withIndex()) {
            val widget = lineItemWidgets.get(i) as PostLineItem
            widget.setCommand(command)
            widget.onEditedSignal.disconnect(this)
            widget.onEditedSignal.connect(this, ::onEditedString)
        }
    }

    private fun onEditedString(command: PostEngine.PostCommand) {
        (tab as PostCommandTab).changeCommand(command)
        if (command.number == tab.getCommandsSize() - 1) {
            tab.insertCommand(command.number + 1)
        }
    }

    override fun onSetExecCommand(numberCommand: Int, prevCommand: Int) {
        super.onSetExecCommand(numberCommand, prevCommand)
        if (schemeDialog != null && schemeDialog!!.isVisible) {
            schemeDialog?.updateExecCommand()
        }
    }

    override fun onChangedStatusPlay(statusPlay: BaseEngine.StatusPlay) {
        super.onChangedStatusPlay(statusPlay)

        if (statusPlay == STOPPED) {
            if (schemeDialog != null && schemeDialog!!.isVisible) {
                schemeDialog?.updateExecCommand()
            }
        }
    }
}