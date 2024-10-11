package session

import classes.Admin
import classes.User

class UserSession {
    private var usernames = listOf<String>("John","Steve","Bob")
    private var passwords = listOf<String>("123","456","789")
    private var admins = listOf<Int>(0,1,0)

    fun start() {
        var user: User? = null
        var validation = false
        var running = false
        while (!validation) {
            print("Username : ")
            val inputUsername = readln()
            print("Password : ")
            val inputPassword = readln()

            user = login(inputUsername, inputPassword)
            if (user == null) {
                println("Incorrect username or password")
            } else {
                validation = true
                running = true
            }
        }

        while (running) {
            println(user?.printMenu())
            val choice = readln().toInt()
            if (user?.optionMenu(choice) == false){
                running = false
            }
        }

    }

    private fun login(inputUsername:String, inputPassword:String) : User? {
        for ((i,username) in usernames.withIndex()) {
            if (inputUsername == username) {
                if (inputPassword == passwords[i]) {
                    return if (admins[i] == 1) Admin(inputUsername) else User(inputUsername)
                }
            }
        }
        return null
    }
}