package ru.alexey_podusov.machines.engines

import com.google.gson.annotations.Expose

abstract class EngineTab(@Expose var name:String) {
    var engine: BaseEngine? = null
    open fun setMainEngine(engine: BaseEngine) {
        this.engine = engine
    }
}