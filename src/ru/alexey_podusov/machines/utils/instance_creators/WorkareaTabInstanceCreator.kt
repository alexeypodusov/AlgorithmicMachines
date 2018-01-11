package ru.alexey_podusov.machines.utils.instance_creators

import com.google.gson.InstanceCreator
import ru.alexey_podusov.machines.MachineType
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.WorkareaTab
import ru.alexey_podusov.machines.engines.post.PostWorkareaTab
import java.lang.reflect.Type

class WorkareaTabInstanceCreator(var type: MachineType): InstanceCreator<WorkareaTab> {

    override fun createInstance(p0: Type?): WorkareaTab {
        //TODO
//        when(type) {
//            POST -> return PostWorkareaTab("")
//        }
        return PostWorkareaTab("")
    }
}