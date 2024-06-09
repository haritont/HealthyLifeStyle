package vika.app.healthy_lifestyle.bean.sport

import vika.app.healthy_lifestyle.bean.main.Type

class PhysicalExercise (
    var id: Long = 0,
    var name: String = "",
    var met: Double = 0.0,
    var type: Type,
    var training: Boolean = false,
    var favorite: Boolean = false,
    var exception: Boolean = false,
)