package ru.alexey_podusov.machines.engines

abstract class CommandTab(name:String): EngineTab(name) {
    abstract fun getCommandsSize(): Int

    abstract fun insertCommand(number: Int)
    abstract fun removeCommand(number: Int)

    abstract fun isEmptyCommand(number :Int): Boolean
}