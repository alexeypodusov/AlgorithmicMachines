package ru.alexey_podusov.machines.factories

import com.google.gson.GsonBuilder
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.WorkareaTab
import ru.alexey_podusov.machines.engines.post.PostCommandTab
import ru.alexey_podusov.machines.engines.post.PostEngine
import ru.alexey_podusov.machines.engines.post.PostWorkareaTab
import ru.alexey_podusov.machines.ui.BaseCommands
import ru.alexey_podusov.machines.ui.BaseWorkarea
import ru.alexey_podusov.machines.ui.post.PostCellsWorkarea
import ru.alexey_podusov.machines.ui.post.PostLineCommands
import ru.alexey_podusov.machines.utils.ConcreteClassDeserializer

class PostFactory : IFactory {
    override fun createEngine(): BaseEngine {
        return PostEngine()
    }

    override fun createWorkWidget(tab: WorkareaTab): BaseWorkarea {
        return PostCellsWorkarea(tab as PostWorkareaTab)
    }

    override fun createCommandsWidget(tab: CommandTab): BaseCommands {
        return PostLineCommands(tab as PostCommandTab)
    }

    override fun readEngineFromJson(json: String): BaseEngine {
        val gson = GsonBuilder()
                .registerTypeAdapter(CommandTab::class.java, ConcreteClassDeserializer(PostCommandTab::class.java))
                .registerTypeAdapter(WorkareaTab::class.java, ConcreteClassDeserializer(PostWorkareaTab::class.java))
                .create()

        val engine = gson.fromJson<PostEngine>(json, PostEngine::class.java)
        engine.setMainEngineOnTabs()
        return engine
    }
}