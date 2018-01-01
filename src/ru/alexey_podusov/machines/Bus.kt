package ru.alexey_podusov.machines

import com.trolltech.qt.QSignalEmitter
import com.trolltech.qt.QSignalEmitter.Signal1
import com.trolltech.qt.core.QObject

class Bus private constructor() : QObject(){
    private val subject = QObject()
    private val signal = Signal1<Any>()
    companion object {
        val instance: Bus by lazy { Bus() }
    }

    fun <T>subscribe(action: (T) -> Unit) {
        signal.connect(action, "invoke(java.lang.Object)")
    }

    fun post(event: Any) {
        signal.emit(event)
    }
}