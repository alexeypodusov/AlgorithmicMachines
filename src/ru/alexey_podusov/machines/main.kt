package ru.alexey_podusov.machines

import com.trolltech.qt.gui.QApplication

fun main(args: Array<String>) {
    QApplication.initialize(args)
    MainWindow().show()
    QApplication.execStatic()
}