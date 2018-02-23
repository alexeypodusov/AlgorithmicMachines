package ru.alexey_podusov.machines.ui

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.QDialog
import com.trolltech.qt.gui.QRadioButton
import com.trolltech.qt.gui.QWidget
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.forms.Ui_Dialog
import ru.alexey_podusov.machines.utils.UserPreferences
import ru.alexey_podusov.machines.utils.UserPreferences.Speed.*

class PreferencesDialog(parent: QWidget) : QDialog(parent) {
    val ui = Ui_Dialog()
    val preferences = UserPreferences.instance

    init {
        ui.setupUi(this)
        val flags = windowFlags()
        flags.clear(Qt.WindowType.WindowContextHelpButtonHint)
        setWindowFlags(flags)

        setFixedSize(size())

        bindView()

        ui.cancelButton.clicked.connect(this, ::cancelButtonClicked)
        ui.okButton.clicked.connect(this, ::okButtonClicked)
        ui.finishRoleMarkovEdit.textEdited.connect(this, ::finishEditTextEdited)
    }

    private fun finishEditTextEdited(string: String) {
        ui.okButton.isEnabled = !string.isEmpty()
    }

    private fun okButtonClicked() {
        preferences.autoDeleteEmptyCommands = ui.autoDeleteCheckbox.isChecked
        preferences.finalSymbolMarkov = ui.finishRoleMarkovEdit.text()

        val checkedWidget  = ui.groupBoxSpeed
                .children()
                .filter { it is QRadioButton && it.isChecked }
                .first()
        when(checkedWidget) {
            ui.veryHightRadioButton -> preferences.speed = VERY_HIGHT
            ui.hightRadioButton -> preferences.speed = HIGHT
            ui.midleRadioButton -> preferences.speed = MIDLE
            ui.lowRadioButton -> preferences.speed = LOW
            ui.veryLowRadioButton -> preferences.speed = VERY_LOW
        }
        preferences.save()
        close()
    }

    private fun cancelButtonClicked() {
        close()
    }

    private fun bindView() {
        ui.finishRoleMarkovEdit.setText(preferences.finalSymbolMarkov)
        ui.autoDeleteCheckbox.isChecked = preferences.autoDeleteEmptyCommands

        when (preferences.speed) {
            VERY_HIGHT -> ui.veryHightRadioButton.isChecked = true
            UserPreferences.Speed.HIGHT -> ui.hightRadioButton.isChecked = true
            MIDLE -> ui.midleRadioButton.isChecked = true
            LOW -> ui.lowRadioButton.isChecked = true
            VERY_LOW -> ui.lowRadioButton.isChecked = true
        }
    }
}