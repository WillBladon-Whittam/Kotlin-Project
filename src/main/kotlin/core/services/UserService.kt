package core.services

import core.models.*
import data.dao.RoomDao
import data.dao.UserDao

open class UserService() {
    val accounts =  UserAccounts()
    val userDao: UserDao = UserDao()

    var loggedIn: User? = null

    init {
        val users = userDao.getUsers()
        for (user in users) {
            accounts.addUser(user)
        }
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