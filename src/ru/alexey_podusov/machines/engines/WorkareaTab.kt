package ru.alexey_podusov.machines.engines

abstract class WorkareaTab(name: String): EngineTab(name) {
    abstract fun saveWorkarea()
    abstract fun restoreWorkarea()
    abstract fun savedIsNull(): Boolean
}

