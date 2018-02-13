package ru.alexey_podusov.machines.factories

import com.google.gson.GsonBuilder
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.WorkareaTab
import ru.alexey_podusov.machines.engines.tyuring.TyuringCommandTab
import ru.alexey_podusov.machines.engines.tyuring.TyuringEngine
import ru.alexey_podusov.machines.engines.tyuring.TyuringWorkareaTab
import ru.alexey_podusov.machines.ui.BaseCommands
import ru.alexey_podusov.machines.ui.BaseWorkarea
import ru.alexey_podusov.machines.ui.tyuring.TyuringCellsWorkarea
import ru.alexey_podusov.machines.ui.tyuring.TyuringCommands
import ru.alexey_podusov.machines.utils.ConcreteClassDeserializer

class TyuringFactory : IFactory {
    override fun createEngine(): BaseEngine {
        return TyuringEngine()
    }

    override fun readEngineFromJson(json: String): BaseEngine {
        val gson = GsonBuilder()
                .registerTypeAdapter(CommandTab::class.java, ConcreteClassDeserializer(TyuringCommandTab::class.java))
                .registerTypeAdapter(WorkareaTab::class.java, ConcreteClassDeserializer(TyuringWorkareaTab::class.java))
                .create()

        val engine = gson.fromJson<TyuringEngine>(json, TyuringEngine::class.java)
        engine.setMainEngineOnTabs()
        return engine
    }

    override fun createWorkWidget(tab: WorkareaTab): BaseWorkarea {
        return TyuringCellsWorkarea(tab as TyuringWorkareaTab)
    }

    override fun createCommandsWidget(tab: CommandTab): BaseCommands {
        return TyuringCommands(tab as TyuringCommandTab)
    }
}