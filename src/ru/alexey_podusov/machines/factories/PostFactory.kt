package ru.alexey_podusov.machines.factories

import ru.alexey_podusov.machines.models.ModelBase
import ru.alexey_podusov.machines.models.ModelPost
import ru.alexey_podusov.machines.ui.CommandsBaseWidget
import ru.alexey_podusov.machines.ui.WorkareaBaseWidget
import ru.alexey_podusov.machines.ui.post.CommandsPostWidget
import ru.alexey_podusov.machines.ui.post.WorkareaPostWidget
import javax.jws.WebParam

class PostFactory: IFactory {

    override fun createModel(): ModelBase{
        return  ModelPost()
    }

    override fun createWorkareaWidget(model: ModelBase): WorkareaBaseWidget {
        return WorkareaPostWidget(model as ModelPost)
    }

    override fun createCommandsBaseWidget(model: ModelBase): CommandsBaseWidget {
        return CommandsPostWidget(model as ModelPost)
    }
}