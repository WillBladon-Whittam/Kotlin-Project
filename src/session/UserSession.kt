package session

import classes.User

class UserSession {

    fun mainLoop(user: User) {
        var running = true
        while (running) {
            println(user.printMenu())
            val choice = readlnOrNull()?.toIntOrNull() ?: 0
            if (!user.optionMenu(choice)){
                running = false
            }
        }
    }
}