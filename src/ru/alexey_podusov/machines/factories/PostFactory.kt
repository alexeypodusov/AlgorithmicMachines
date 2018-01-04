package ru.alexey_podusov.machines.factories

import ru.alexey_podusov.machines.models.BaseEngine
import ru.alexey_podusov.machines.models.PostEngine
import ru.alexey_podusov.machines.ui.BaseCommands
import ru.alexey_podusov.machines.ui.BaseWorkarea
import ru.alexey_podusov.machines.ui.post.PostLineCommands
import ru.alexey_podusov.machines.ui.post.PostCellsWorkarea

class PostFactory: IFactory {

    override fun createModel(): BaseEngine {
        return  PostEngine()
    }

    override fun createWorkareaWidget(engine: BaseEngine): BaseWorkarea {
        return PostCellsWorkarea(engine as PostEngine)
    }

    override fun createCommandsBaseWidget(engine: BaseEngine): BaseCommands {
        return PostLineCommands(engine as PostEngine)
    }
}