package ru.alexey_podusov.machines.ui.post

import com.trolltech.qt.gui.QComboBox
import com.trolltech.qt.gui.QIntValidator
import com.trolltech.qt.gui.QLineEdit
import ru.alexey_podusov.machines.ui.StringBaseWidget

class StringPostWidget : StringBaseWidget() {
    val commandComboBox = QComboBox()
    val transitionLineEdit = QLineEdit()
    val secondTransitionLineEdit = QLineEdit()
    val commentLineEdit = QLineEdit()

    companion object {
        val WIDTH_COMMAND_STRING = 80
        val WIDTH_TRANSITION_STRING = 70
        val WIDTH_COMMENT_STRING = 50
    }

    init {
        commandComboBox.setMinimumWidth(WIDTH_COMMAND_STRING)
        commandComboBox.setFixedHeight(HEIGHT_STRING)
        commandComboBox.addItem("")
        commandComboBox.addItem("V добавить метку")
        commandComboBox.addItem("X удалить метку")
        commandComboBox.addItem("<- шаг влево")
        commandComboBox.addItem(". шаг вправо")
        commandComboBox.addItem("? проверить")
        commandComboBox.addItem("! стоп")
        stringLayout.addWidget(commandComboBox)

        transitionLineEdit.setFixedWidth(WIDTH_TRANSITION_STRING)
        transitionLineEdit.setFixedHeight(HEIGHT_STRING)
        transitionLineEdit.setMaximumWidth(3)
        transitionLineEdit.setValidator( QIntValidator(0, 999))
        stringLayout.addWidget(transitionLineEdit)
        

        secondTransitionLineEdit.setFixedWidth(WIDTH_TRANSITION_STRING)
        secondTransitionLineEdit.setFixedHeight(HEIGHT_STRING)
        secondTransitionLineEdit.setMaximumWidth(3)
        secondTransitionLineEdit.setValidator(QIntValidator(0, 999))
        secondTransitionLineEdit.hide()

        commentLineEdit.setMinimumWidth(WIDTH_COMMENT_STRING)
        commentLineEdit.setFixedHeight(HEIGHT_STRING)
        commentLineEdit.setMaxLength(255)
        stringLayout.addWidget(commentLineEdit)
        commentLineEdit.installEventFilter(this)
    }
}