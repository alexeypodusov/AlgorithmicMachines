package ru.alexey_podusov.machines.engines

abstract class CommandTab(name:String): EngineTab(name) {
    abstract fun getCommandsSize(): Int
}