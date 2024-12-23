package dao

import org.jetbrains.exposed.sql.transactions.transaction

import interfaces.UsersDaoInterface
import interfaces.UsersTable
import classes.AdminUser
import classes.RegularUser

import classes.User
import org.jetbrains.exposed.sql.*

class UserDao : UsersDaoInterface {
    /**
     * User Data Access Object
     */
    init {
        transaction {
            SchemaUtils.create(UsersTable)
        }
    }

    override fun getUserByUsername(username: String) : User? {
        var user: User? = null
        transaction {
            val resultRow   = UsersTable.selectAll()
                .where { UsersTable.name eq username }.singleOrNull()

            resultRow?.apply {
                user = if (this[UsersTable.userType] == "Regular") {
                    RegularUser(
                        this[UsersTable.name],
                        this[UsersTable.password],
                        this[UsersTable.email],
                        this[UsersTable.loggedIn]
                    )
                } else {
                    AdminUser(
                        this[UsersTable.name],
                        this[UsersTable.password],
                        this[UsersTable.email],
                        this[UsersTable.loggedIn]
                    )
                }
            }
        }
        return user
    }

    override fun insertUser(user: User) : Int {
        var userId = 0
        if (this.getUserByUsername(user.name) == null) {
            transaction {
                userId = UsersTable.insert {
                    it[name] = user.name
                    it[password] = user.password
                    it[email] = user.email
                    it[loggedIn] = user.loggedIn
                    it[userType] = user.getUserType()
                }[UsersTable.id]
            }
            return userId
        } else {
            return -1
        }
    }

    override fun validateUserLogin(username: String, password: String) : User? {
        var user: User? = null
        transaction {
            val resultRow  = UsersTable.selectAll()
                .where { (UsersTable.name eq username) and (UsersTable.password eq password) }.singleOrNull()

            resultRow?.apply {
                user = if (this[UsersTable.userType] == "Regular") {
                    RegularUser(
                        this[UsersTable.name],
                        this[UsersTable.password],
                        this[UsersTable.email],
                        this[UsersTable.loggedIn]
                    )
                } else {
                    AdminUser(
                        this[UsersTable.name],
                        this[UsersTable.password],
                        this[UsersTable.email],
                        this[UsersTable.loggedIn]
                    )
                }
            }
        }
        return user
    }
}