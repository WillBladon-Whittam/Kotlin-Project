////////////////////////////// User.kt ////////////////////////////////////
///////////////////////// Author: Edward Kirr ////////////////////////////
///////////// Description: User class to handle creation of /////////////
////////// different types of users and inform the interface ///////////
///////////////////////////////////////////////////////////////////////

package classes

abstract class User(var name: String,  var email: String, var password: String, var loggedIn: Boolean = false) {
    // Attributes: name, email & password.
    // Abstract class User, provide a foundation for the
    // subclasses (admin & regular) to based off of
    abstract fun getUserType(): String
}

class RegularUser(name: String, password: String, email: String, loggedIn: Boolean = false) : User(name, password, email, loggedIn) {
    // Regular Subclass of User.
    // Provides limited functionality with the interface, only allowed to create user
    override fun getUserType(): String {
        // Responsible for identifying the user type
        // Return a string of the user type
        return "$name is a Regular user"
    }
}

class AdminUser(name: String, password: String, email: String, loggedIn: Boolean = false) : User(name, password, email, loggedIn) {
    // Admin subclass of User.
    // Provides full functionality of the interface, such as editing rooms,
    // deleting rooms, editing users and deleting users.
    override fun getUserType(): String {
        // Responsible for identifying the user type
        // Return a string of the user type
        return "$name is an Admin user"
    }
}