package core.services

import core.models.*
import data.dao.RoomDao
import data.dao.UserDao

open class UserService() {
    private val accounts =  UserAccounts()
    private val userDao: UserDao = UserDao()

    val john = RegularUser("John", "123", "john@outlook.com")
    val steve = AdminUser("Steve", "456", "steve@hotmail.co.uk")
    val bob = RegularUser("Bob", "789", "bob@solent.ac.uk")

    init {
        accounts.addUser(john)
        accounts.addUser(steve)
        accounts.addUser(bob)
    }

    fun login(username: String, password: String) : User? {
        return accounts.validateLogin(username, password)
    }

    fun addUser(username: String, email: String, password: String) : User? {
        val user = RegularUser(username, password, email)
        if (accounts.validateLogin(username, password) == null) {
            userDao.insertUser(user)
            accounts.addUser(user)
            return user
        }
        return null
    }

}