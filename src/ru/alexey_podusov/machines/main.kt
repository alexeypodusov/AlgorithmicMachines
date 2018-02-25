package ru.alexey_podusov.machines

import com.trolltech.qt.gui.QApplication
import com.trolltech.qt.gui.QFont

fun main(args: Array<String>) {
    QApplication.initialize(args)

    val defaultFont = QApplication.font()
    defaultFont.setPointSize(8)
    defaultFont.setFamily("Tahoma")
    QApplication.setFont(defaultFont)

    MainWindow().show()
    QApplication.execStatic()
}
