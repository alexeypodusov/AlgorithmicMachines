package ru.alexey_podusov.machines.ui.post

import com.trolltech.qt.gui.QComboBox
import com.trolltech.qt.gui.QIntValidator
import com.trolltech.qt.gui.QLineEdit
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.post.PostCommandTab.Companion.MAX_COMMANDS
import ru.alexey_podusov.machines.engines.post.PostEngine.*
import ru.alexey_podusov.machines.engines.post.PostEngine.PostCommandType.CHECK_MARK
import ru.alexey_podusov.machines.engines.post.PostEngine.PostCommandType.NULL_COMMAND
import ru.alexey_podusov.machines.ui.BaseLineItem
import ru.alexey_podusov.machines.ui.custom_widgets.LinkLineEdit

class PostLineItem : BaseLineItem() {
    private val commandComboBox = QComboBox()
    private val transitionLineEdit = LinkLineEdit()
    private val secondTransitionLineEdit = LinkLineEdit()
    private val commentLineEdit = QLineEdit()

    val onEditedSignal = Signal1<PostCommand>()

    companion object {
        val WIDTH_COMMAND_STRING = 80
        val WIDTH_TRANSITION_STRING = 70
        val WIDTH_COMMENT_STRING = 50
    }

    var postCommandType: PostCommandType = NULL_COMMAND
        set(value) {
            field = value
            commandComboBox.blockSignals(true)
            commandComboBox.setCurrentIndex(value.ordinal)
            commandComboBox.blockSignals(false)
            if (CHECK_MARK.equals(value)) secondTransitionLineEdit.show()
            else secondTransitionLineEdit.hide()
        }

    var transition: Int = -1
        set(value) {
            field = value
            if (value != -1) transitionLineEdit.setText(value.toString())
            else transitionLineEdit.setText("")
        }

    var secondTransition: Int = -1
        set(value) {
            field = value
            if (value != -1) secondTransitionLineEdit.setText(value.toString())
            else secondTransitionLineEdit.setText("")
        }

    var comment: String = ""
        set(value) {
            field = value
            commentLineEdit.setText(value)
        }

    fun setCommand(command: PostCommand) {
        number = command.number
        postCommandType = command.commandType
        transition = command.transition
        secondTransition = command.secondTransition
        comment = command.comment
    }

    init {
        scaleFactor = 3

        commandComboBox.setMinimumWidth(WIDTH_COMMAND_STRING)
        commandComboBox.setFixedHeight(HEIGHT_STRING)
        commandComboBox.currentIndexChanged.connect(this, ::onComboBoxIndexChanged)
        stringLayout.addWidget(commandComboBox)
        PostCommandType.values().forEach { commandComboBox.addItem(it.text) }

        transitionLineEdit.setFixedSize(WIDTH_TRANSITION_STRING, HEIGHT_STRING)
        transitionLineEdit.setMaximumWidth(3)
        transitionLineEdit.setValidator(QIntValidator(0, MAX_COMMANDS))
        transitionLineEdit.textEdited.connect(this, ::onTransitionTextEdited)
        stringLayout.addWidget(transitionLineEdit)
        transitionLineEdit.installEventFilter(this)


        secondTransitionLineEdit.setFixedSize(WIDTH_TRANSITION_STRING, HEIGHT_STRING)
        secondTransitionLineEdit.setMaximumWidth(3)
        secondTransitionLineEdit.setValidator(QIntValidator(0, MAX_COMMANDS))
        secondTransitionLineEdit.textEdited.connect(this, ::onSecondTransitionTextEdited)
        stringLayout.addWidget(secondTransitionLineEdit)
        secondTransitionLineEdit.hide()
        secondTransitionLineEdit.installEventFilter(this)

        commentLineEdit.setMinimumWidth(WIDTH_COMMENT_STRING)
        commentLineEdit.setFixedHeight(HEIGHT_STRING)
        commentLineEdit.setMaxLength(255)
        stringLayout.addWidget(commentLineEdit)
        commentLineEdit.textEdited.connect(this, ::onCommentLineTextEdited)
        commentLineEdit.installEventFilter(this)
    }

    private fun onComboBoxIndexChanged(index: Int) {
        postCommandType = PostCommandType.values()[index]
        onEditedSignal.emit(PostCommand(number, postCommandType,
                transition, secondTransition, comment))
        inFocusSignal.emit(number)
    }

    private fun onTransitionTextEdited(text: String) {
        try {
            transition = text.toInt()
        } catch (e: NumberFormatException) {
            transition = -1
        }
        onEditedSignal.emit(PostCommand(number, postCommandType,
                transition, secondTransition, comment))
    }

    private fun onSecondTransitionTextEdited(text: String) {
        try {
            secondTransition = text.toInt()
        } catch (e: NumberFormatException) {
            secondTransition = -1
        }
        onEditedSignal.emit(PostCommand(number, postCommandType,
                transition, secondTransition, comment))
    }

    private fun onCommentLineTextEdited(text: String) {
        comment = text
        onEditedSignal.emit(PostCommand(number, postCommandType,
                transition, secondTransition, comment))
    }

    override fun setExecBorder(prevCommand: Int) {
        super.setExecBorder(prevCommand)
        if (prevCommand != -1) {
            val link = "<a href='$prevCommand'>$prevCommand</a>"
            previousNumberString.setText(link)
            previousStringWidget.show()
        }
    }
}