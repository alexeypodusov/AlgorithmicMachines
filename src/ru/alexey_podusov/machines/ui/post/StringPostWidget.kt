package ru.alexey_podusov.machines.ui.post

import com.trolltech.qt.gui.QComboBox
import com.trolltech.qt.gui.QIntValidator
import com.trolltech.qt.gui.QLineEdit
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.models.ModelPost
import ru.alexey_podusov.machines.models.ModelPost.*
import ru.alexey_podusov.machines.models.ModelPost.PostCommandType.CHECK_MARK
import ru.alexey_podusov.machines.models.ModelPost.PostCommandType.NULL_COMMAND
import ru.alexey_podusov.machines.ui.StringBaseWidget
import ru.alexey_podusov.machines.ui.custom_widgets.LinkLineEdit

class StringPostWidget : StringBaseWidget() {
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

    var postCommandType: ModelPost.PostCommandType = NULL_COMMAND
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
            if (value != -1) transitionLineEdit.setText(value.toString())
            else transitionLineEdit.setText("")
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
        commandComboBox.setMinimumWidth(WIDTH_COMMAND_STRING)
        commandComboBox.setFixedHeight(HEIGHT_STRING)
        commandComboBox.currentIndexChanged.connect(this, "onComboBoxIndexChanched(int)")
        stringLayout.addWidget(commandComboBox)
        PostCommandType.values().forEach { commandComboBox.addItem(it.text) }

        transitionLineEdit.setFixedSize(WIDTH_TRANSITION_STRING, HEIGHT_STRING)
        transitionLineEdit.setMaximumWidth(3)
        transitionLineEdit.setValidator(QIntValidator(0, 999))
        transitionLineEdit.editingFinished.connect(this, "onTransitionEditingFinished()")
        stringLayout.addWidget(transitionLineEdit)

        secondTransitionLineEdit.setFixedSize(WIDTH_TRANSITION_STRING, HEIGHT_STRING)
        secondTransitionLineEdit.setMaximumWidth(3)
        secondTransitionLineEdit.setValidator(QIntValidator(0, 999))
        secondTransitionLineEdit.editingFinished.connect(this, "onSecondTransitionEditingFinished()")
        stringLayout.addWidget(secondTransitionLineEdit)
        secondTransitionLineEdit.hide()

        commentLineEdit.setMinimumWidth(WIDTH_COMMENT_STRING)
        commentLineEdit.setFixedHeight(HEIGHT_STRING)
        commentLineEdit.setMaxLength(255)
        stringLayout.addWidget(commentLineEdit)
        commentLineEdit.editingFinished.connect(this, "onCommentLineEditEditinFinished()")
        commentLineEdit.installEventFilter(this)
    }

    fun onComboBoxIndexChanched(index: Int) {
        postCommandType = PostCommandType.values()[index]
        onEditedSignal.emit(PostCommand(number, postCommandType,
                transition, secondTransition, comment))
    }

    fun onTransitionEditingFinished() {
        try {
            transition = transitionLineEdit.text().toInt()
        } catch (e: NumberFormatException) {
            transition = -1
        }
        onEditedSignal.emit(PostCommand(number, postCommandType,
                transition, secondTransition, comment))
    }

    fun onSecondTransitionEditingFinished() {
        try {
            secondTransition = secondTransitionLineEdit.text().toInt()
        } catch (e: NumberFormatException) {
            secondTransition = -1
        }
        onEditedSignal.emit(PostCommand(number, postCommandType,
                transition, secondTransition, comment))
    }

    fun onCommentLineEditEditinFinished() {
        comment = commentLineEdit.text()
        onEditedSignal.emit(PostCommand(number, postCommandType,
                transition, secondTransition, comment))
    }
}