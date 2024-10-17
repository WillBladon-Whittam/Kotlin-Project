package userClasses

class UserLogin {
    //Usernames, Passwords and Admin List - Hard Coded
    private var usernames = listOf<String>("John","Steve","Bob")
    private var passwords = listOf<String>("123","456","789")
    private var admin = listOf<Boolean>(false,true,false)

    //Check usernames and passwords the either creates a new admin/user or selects an existing admin/user
    fun login(inputUsername:String, inputPassword:String, users:MutableList<User>) : User? {
        for ((i,username) in usernames.withIndex()) {
            if (inputUsername == username) {
                if (inputPassword == passwords[i]) {
                    for (person in users) {
                        if (username == person.getName()) {
                            return person
                        }
                    }
                    val returnVal = if (admin[i]) Admin(username) else User(username)
                    users.add(returnVal)
                    return returnVal
                }
            }
        }
        return null
    }
}