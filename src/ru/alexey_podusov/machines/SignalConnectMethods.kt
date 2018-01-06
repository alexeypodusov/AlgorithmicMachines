package ru.alexey_podusov.machines

import com.trolltech.qt.QSignalEmitter
import com.trolltech.qt.core.QObject
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaType

fun QSignalEmitter.AbstractSignal.connect(receiver: Any, method: KFunction<Any>) {
    val methodName: String = method.name + "(" + method.parameters.joinToString { (it.type.javaType as Class<*>).name } + ")"
    connect(receiver, methodName)
}



