package ru.alexey_podusov.machines

import com.trolltech.qt.QSignalEmitter
import com.trolltech.qt.core.QObject
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaType

fun getNameMethod(action :Any): String {
    return "invoke(" + action.javaClass.methods.get(1).parameterTypes.joinToString { it.canonicalName } + ")"
}

fun QSignalEmitter.AbstractSignal.connect(receiver: QObject, method: KFunction<Any>) {
    val methodName: String = method.name + "(" + method.parameters.joinToString { (it.type.javaType as Class<*>).name } + ")"
    connect(receiver, methodName)
}

fun QSignalEmitter.Signal0.connect(action: () -> Unit) {
    connect(action, "invoke()")
}

fun <A> QSignalEmitter.Signal1<A>.connect(action: (A) -> Unit) {
    connect(action, getNameMethod(action))
}

fun <A, B> QSignalEmitter.Signal2<A, B>.connect(action: (A, B) -> Unit) {
    connect(action, getNameMethod(action))
}

fun <A, B, C> QSignalEmitter.Signal3<A, B, C>.connect(action: (A, B, C) -> Unit) {
    connect(action, getNameMethod(action))
}

fun <A, B, C, D> QSignalEmitter.Signal4<A, B, C, D>.connnect(action: (A, B, C, D) -> Unit) {
    connect(action, getNameMethod(action))
}

fun <A, B, C, D, F> QSignalEmitter.Signal5<A, B, C, D, F>.connect(action: (A, B, C, D, F) -> Unit) {
    connect(action, getNameMethod(action))
}

