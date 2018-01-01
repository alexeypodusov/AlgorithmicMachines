package ru.alexey_podusov.machines.models

import com.trolltech.qt.QSignalEmitter
import com.trolltech.qt.core.QObject

abstract class ModelBase : QObject() {
    val workAreaChangedSignal = Signal0()
}