package session

import classes.User

class UserSession {
    //Hard Coded Bookings
    private val bookings = listOf(
        mapOf("computerId" to "TS101-1", "day" to "Monday", "timeSlot" to "9am-11am", "studentId" to "User"),
        mapOf("computerId" to "TS101-1", "day" to "Tuesday", "timeSlot" to "11am-1pm", "studentId" to "User"),
        mapOf("computerId" to "TS101-1", "day" to "Wednesday", "timeSlot" to "1pm-3pm", "studentId" to "User"),
        mapOf("computerId" to "TS101-1", "day" to "Thursday", "timeSlot" to "3pm-5pm", "studentId" to "User"))

    fun mainLoop(user: User) {
        var running = true
        while (running) {
            println(user.printMenu())
            val choice = readlnOrNull()?.toIntOrNull() ?: 0
            running = user.optionMenu(choice)
        }
    }
}