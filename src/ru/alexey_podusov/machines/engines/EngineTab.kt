package ru.alexey_podusov.machines.engines

import com.google.gson.annotations.Expose

abstract class EngineTab(@Expose var name:String) {
    var engine: BaseEngine? = null
    abstract fun setMainEngine(engine: BaseEngine)
}