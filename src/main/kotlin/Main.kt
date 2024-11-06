/**
 * @author  William Bladon-Whittam
 * @author  Charlie Clark
 */

package main.kotlin

import main.kotlin.sessions.UserSession
import main.kotlin.sessions.LoginSession
import main.kotlin.sessions.AdminSession


fun main() {
    val loginSession = LoginSession()
    while (true) {
        val user = loginSession.startMenu()

        when(user) {
            false -> {  // Not an admin user
                val userSession = UserSession(loginSession.university)
                userSession.mainLoop()
            }
            true -> {  // An admin user
                val adminSession = AdminSession(loginSession.university)
                adminSession.mainLoop()
            }
            null -> return
        }
    }
}