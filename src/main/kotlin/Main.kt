/**
 * @author  William Bladon-Whittam
 * @author  Charlie Clark
 * @author  Edward Kirr
 */

/**
 * During intergration this file pulled together the Login, User and Admin sessions.
 */

package main.kotlin

import main.kotlin.classes.AdminUser
import main.kotlin.classes.RegularUser
import main.kotlin.sessions.UserSession
import main.kotlin.sessions.LoginSession
import main.kotlin.sessions.AdminSession


fun main() {
    val loginSession = LoginSession()
    while (true) {
        val user = loginSession.startMenu() ?: return

        when(user) {
            is RegularUser -> {  // Not an admin user
                val userSession = UserSession(loginSession.university, loginSession.accounts)
                userSession.mainLoop()
            }
            is AdminUser -> {  // An admin user
                val adminSession = AdminSession(loginSession.university, loginSession.accounts)
                adminSession.mainLoop()
            }
        }
    }
}