package ru.alexey_podusov.machines.engines

abstract class CommandTab(name:String, engine: BaseEngine): EngineTab(name, engine) {
    abstract fun getCommandsSize(): Int
}