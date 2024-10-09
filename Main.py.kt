fun main() {
    // ToDo: Sign in (2 users: normal user and admin)

    // ToDo: Session current only opens as a base User, if they login as Admin a different session needs opening
    val session = UserSession()
    session.mainLoop()
}