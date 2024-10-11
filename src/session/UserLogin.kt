package session

import classes.Admin
import classes.User

class UserLogin {
    //Usernames, Passwords and Admin
    private var usernames = listOf<String>("John","Steve","Bob")
    private var passwords = listOf<String>("123","456","789")
    private var admins = listOf<Boolean>(false,true,false)

    fun login() : User {
        while (true) {
            print("Username : ")
            val inputUsername = readln()
            print("Password : ")
            val inputPassword = readln()

            val user = login(inputUsername, inputPassword)
            if (user == null) {
                println("Incorrect username or password")
            } else {
                println("-----SIGN IN SUCCESSFUL-----")
                return user
            }
        }
    }

    private fun login(inputUsername:String, inputPassword:String) : User? {
        for ((i,username) in usernames.withIndex()) {
            if (inputUsername == username) {
                if (inputPassword == passwords[i]) {
                    return if (admins[i]) Admin(inputUsername) else User(inputUsername)
                }
            }
        }
        return null
    }
}