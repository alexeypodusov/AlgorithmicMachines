package ru.alexey_podusov.machines

import com.trolltech.qt.QSignalEmitter
import com.trolltech.qt.gui.QApplication

fun main(args: Array<String>) {
    QApplication.initialize(args)
    MainWindow().show()
    QApplication.execStatic()
}

fun <A> QSignalEmitter.Signal1<A>.connect(action: (A) -> Unit) {
    connect(action, "invoke(java.lang.Object)")
}

fun QSignalEmitter.Signal0.connect(action: () -> Unit) {
    connect(action, "invoke()")
}