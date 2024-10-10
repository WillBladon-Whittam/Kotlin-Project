/**
 * @author  William Bladon-Whittam
 */

package src.main.kotlin

import src.main.kotlin.sessions.UserSession

fun main() {
     // Sign in (2 users: normal user and admin)
     // Session current only opens as a base User, if they log in as Admin a different session needs opening
     // Member B task implemented here when integrating

    val session = UserSession()
    session.mainLoop()
}