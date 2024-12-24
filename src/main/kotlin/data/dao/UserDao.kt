package data.dao

import org.jetbrains.exposed.sql.transactions.transaction

import data.schema.UsersDaoInterface
import data.schema.UsersTable
import core.models.AdminUser
import core.models.RegularUser

import core.models.User
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

    override fun insertUser(user: User) : Int {
        var userId = 0
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
    }
}