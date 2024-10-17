package classes

class UserLogin {
    //Usernames, Passwords and Admin List - Hard Coded
    private var usernames = listOf<String>("John","Steve","Bob")
    private var passwords = listOf<String>("123","456","789")
    private var admin = listOf<Boolean>(false,true,false)

    /**
    Checks if the username is in the usernames list
           if the same password matches
           if the account is an Admin account
    If correct + Admin = creates an Admin user
                         else creates a user
    Incorrect returns NULL
    **/
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