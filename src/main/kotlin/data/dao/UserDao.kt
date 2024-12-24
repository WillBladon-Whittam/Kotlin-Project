package data.dao

import core.models.*
import org.jetbrains.exposed.sql.transactions.transaction

import data.schema.UsersDaoInterface
import data.schema.UsersTable

import data.schema.RoomsTable
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

    override fun getUsers() : List<User> {
        var usersList = listOf<User>()
        transaction {
            val results = UsersTable.selectAll()
            usersList = results.map { row ->
                if (row[UsersTable.userType] == "Regular") {
                    RegularUser(
                        row[UsersTable.name],
                        row[UsersTable.password],
                        row[UsersTable.email],
                        row[UsersTable.loggedIn]
                    )
                } else {
                    AdminUser(
                        row[UsersTable.name],
                        row[UsersTable.password],
                        row[UsersTable.email],
                        row[UsersTable.loggedIn]
                    )
                }
            }
        }
        return usersList
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