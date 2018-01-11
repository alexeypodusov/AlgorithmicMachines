package ru.alexey_podusov.machines.utils.instance_creators

import com.google.gson.InstanceCreator
import ru.alexey_podusov.machines.MachineType
import ru.alexey_podusov.machines.MachineType.POST
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.post.PostCommandTab
import java.lang.reflect.Type

class CommandTabInstanceCreator(var type: MachineType): InstanceCreator<CommandTab> {

    override fun createInstance(p0: Type?): CommandTab {
        //TODO
//        when(type) {
//            POST -> return PostCommandTab("")
//        }
        return PostCommandTab("")
    }
}