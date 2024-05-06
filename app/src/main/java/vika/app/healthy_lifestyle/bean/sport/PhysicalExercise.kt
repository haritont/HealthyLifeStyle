package vika.app.healthy_lifestyle.bean.sport

class PhysicalExercise (
    var id: Long = 0,
    var name: String = "",
    var met: Double = 0.0,
    var type: String = "",
    var training: Boolean = false,
    var favorite: Boolean = false,
    var exception: Boolean = false,
)