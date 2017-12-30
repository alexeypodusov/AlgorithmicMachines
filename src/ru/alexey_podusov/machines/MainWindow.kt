package ru.alexey_podusov.machines

import com.trolltech.qt.gui.QMainWindow
import ru.alexey_podusov.machines.forms.Ui_MainWindow

class MainWindow : QMainWindow() {
    val ui = Ui_MainWindow()
    init {
        ui.setupUi(this)
        initHardcode()
    }

    //пока хардкод
    fun initHardcode() {

    }
}