package ru.alexey_podusov.machines.ui.markov

import com.trolltech.qt.gui.QLineEdit
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.markov.MarkovEngine.MarkovCommand
import ru.alexey_podusov.machines.ui.BaseLineItem

class MarkovLineItem : BaseLineItem() {
    private val sampleLineEdit = QLineEdit()
    private val replacementLineEdit = QLineEdit()
    private val commentLineEdit = QLineEdit()

    val onEditedSignal = Signal1<MarkovCommand>()

    companion object {
        val MAX_WIDTH_EDIT_STRING = 200
        val WIDTH_COMMENT_STRING = 50
    }

    var sample = ""
        set(value) {
            field = value
            sampleLineEdit.setText(value)
        }
    var replacement = ""
        set(value) {
            field = value
            replacementLineEdit.setText(value)
        }
    var comment = ""
        set(value) {
            field = value
            commentLineEdit.setText(value)
        }

    fun setCommand(command: MarkovCommand) {
        number = command.number
        sample = command.sample
        replacement = command.replacement
        comment = command.comment
    }

    init {
        scaleFactor = 2

        sampleLineEdit.setMaximumWidth(MAX_WIDTH_EDIT_STRING)
        sampleLineEdit.setFixedHeight(HEIGHT_STRING)
        sampleLineEdit.editingFinished.connect(this, ::onSampleEditingFinished)
        stringLayout.addWidget(sampleLineEdit)
        sampleLineEdit.installEventFilter(this)

        replacementLineEdit.setMaximumWidth(MAX_WIDTH_EDIT_STRING)
        replacementLineEdit.setFixedHeight(HEIGHT_STRING)
        replacementLineEdit.editingFinished.connect(this, ::onReplacementEditingFinished)
        stringLayout.addWidget(replacementLineEdit)
        replacementLineEdit.installEventFilter(this)

        commentLineEdit.setMinimumWidth(WIDTH_COMMENT_STRING)
        commentLineEdit.setFixedHeight(HEIGHT_STRING)
        commentLineEdit.setMaxLength(255)
        commentLineEdit.editingFinished.connect(this, ::onCommentEditingFinished)
        stringLayout.addWidget(commentLineEdit)
        commentLineEdit.installEventFilter(this)
    }

    private fun onSampleEditingFinished() {
        sample = sampleLineEdit.text()
        onEditedSignal.emit(MarkovCommand(number, sample, replacement, comment))
    }

    private fun onReplacementEditingFinished() {
        replacement = replacementLineEdit.text()
        onEditedSignal.emit(MarkovCommand(number, sample, replacement, comment))
    }

    private fun onCommentEditingFinished() {
        comment = commentLineEdit.text()
        onEditedSignal.emit(MarkovCommand(number, sample, replacement, comment))
    }
}