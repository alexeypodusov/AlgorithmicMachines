package ru.alexey_podusov.machines

import com.trolltech.qt.QSignalEmitter
import java.lang.reflect.Method

fun getNameMethod(action :Any): String {
    var method: Method? = action.javaClass.methods.get(1)
    var nameWithParams = action.javaClass.methods.get(1).name + ("(")
    method!!.parameterTypes.forEach { nameWithParams += it.canonicalName + ","}
    nameWithParams = nameWithParams.substring(0, nameWithParams.length - 1)
    nameWithParams += ")"
    return nameWithParams

}

fun QSignalEmitter.Signal0.connect(action: () -> Unit) {
    connect(action, "invoke()")
}

fun <A> QSignalEmitter.Signal1<A>.connect(action: (A) -> Unit) {
    connect(action, "invoke(java.lang.Object)")
}

fun <A, B> QSignalEmitter.Signal2<A, B>.connect(action: (A, B) -> Unit) {
    connect(action, "invoke(java.lang.Object, java.lang.Object)")
}

fun <A, B, C> QSignalEmitter.Signal3<A, B, C>.connect(action: (A, B, C) -> Unit) {
    connect(action, "invoke(java.lang.Object, java.lang.Object, java.lang.Object)")
}

fun <A, B, C, D> QSignalEmitter.Signal4<A, B, C, D>.connect(action: (A, B, C, D) -> Unit) {
    connect(action, "invoke(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object)")
}

fun <A, B, C, D, F> QSignalEmitter.Signal5<A, B, C, D, F>.connect(action: (A, B, C, D, F) -> Unit) {
    connect(action, "invoke(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object)")
}

