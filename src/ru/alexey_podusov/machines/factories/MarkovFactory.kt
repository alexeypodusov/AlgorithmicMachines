package ru.alexey_podusov.machines.factories

import com.google.gson.GsonBuilder
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.WorkareaTab
import ru.alexey_podusov.machines.engines.markov.MarkovCommandTab
import ru.alexey_podusov.machines.engines.markov.MarkovEngine
import ru.alexey_podusov.machines.engines.markov.MarkovWorkareaTab
import ru.alexey_podusov.machines.ui.BaseCommands
import ru.alexey_podusov.machines.ui.BaseWorkarea
import ru.alexey_podusov.machines.ui.markov.MarkovLineCommands
import ru.alexey_podusov.machines.ui.markov.MarkovWorkarea
import ru.alexey_podusov.machines.utils.ConcreteClassDeserializer

class MarkovFactory: IFactory {
    override fun createEngine(): BaseEngine {
        return MarkovEngine()
    }

    override fun readEngineFromJson(json: String): BaseEngine {
        val gson = GsonBuilder()
                .registerTypeAdapter(CommandTab::class.java, ConcreteClassDeserializer(MarkovCommandTab::class.java))
                .registerTypeAdapter(WorkareaTab::class.java, ConcreteClassDeserializer(MarkovWorkareaTab::class.java))
                .create()

        val engine = gson.fromJson<MarkovEngine>(json, MarkovEngine::class.java)
        engine.setMainEngineOnTabs()
        return engine
    }

    override fun createWorkWidget(tab: WorkareaTab): BaseWorkarea {
        return MarkovWorkarea(tab as MarkovWorkareaTab)
    }

    override fun createCommandsWidget(tab: CommandTab): BaseCommands {
        return MarkovLineCommands(tab as MarkovCommandTab)
    }
}