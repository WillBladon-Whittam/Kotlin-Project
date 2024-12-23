package interfaces

import classes.User
import org.jetbrains.exposed.sql.Table

object UsersTable : Table("users") {
    val id  = integer("id").autoIncrement()
    val name  = text("name")
    val password = text("password")
    val email = text("email")
    val loggedIn = bool("loggedIn")
    val userType = text("userType")
    override val primaryKey = PrimaryKey(id)
}

interface UsersDaoInterface {
    fun getUserByUsername(username: String): User?
    fun insertUser(user: User) : Int
    fun validateUserLogin(username: String, password: String): User?
}