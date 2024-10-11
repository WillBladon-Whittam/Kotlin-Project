import session.UserLogin
import session.UserSession

fun main (){
    val userValidation = UserLogin()
    val user = userValidation.login()

    val session = UserSession()
    session.mainLoop(user)
}