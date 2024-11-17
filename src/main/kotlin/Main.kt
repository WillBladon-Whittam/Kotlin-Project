/**
 * @author  William Bladon-Whittam
 * @author  Charlie Clark
 * @author  Edward Kirr
 */

package main.kotlin

import main.kotlin.classes.AdminUser
import main.kotlin.classes.RegularUser
import main.kotlin.sessions.UserSession
import main.kotlin.sessions.LoginSession
import main.kotlin.sessions.AdminSession

/**
 * Useful default hard-coded information while running:
 *
 *  Accounts:
 *      Username: John
 *      Password: 123
 *      Contact Information: john@outlook.com
 *      Admin: No
 *
 *      Username: Steve
 *      Password: 456
 *      Contact Information: steve@hotmail.co.uk
 *      Admin: Yes
 *
 *      Username: Bob
 *      Password: 789
 *      Contact Information: bob@solent.ac.uk
 *      Admin: No
 *
 *  University: Solent
 *  Buildings:
 *      Name: The Spark
 *      Code: TS
 *      Rooms:
 *          Room Number: 101
 *          Type: Windows Room
 *          Computers: 10
 *
 *          Room Number: 202:
 *          Type: Linux Room:
 *          Computers: 8
 *
 *      Name: Herbert Collins
 *      Code: HS
 *      Rooms:
 *          Room Number: 303
 *          Type: Mac Room
 *          Computers: 2
 *
 *  Bookings:
 *      John booked at TS101-1 on Monday 9am-11am
 *      John booked at TS101-3 on Monday 9am-11am
 *      Bob booked at TS101-1 on Monday 11am-1pm
 *      Bob booked at TS101-4 on Monday 11am-1pm
 *
 */

/**
 * During integration this file pulled together the Login, User and Admin sessions.
 */


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
        user.loggedIn = false
    }
}