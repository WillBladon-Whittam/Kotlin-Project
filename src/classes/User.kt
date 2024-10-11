package classes

abstract class User(val name: String, val password: String, val email: String) {

}

class Regular(name: String, password: String, email: String) : User(name, email, password) {}

class Admin(name: String, password: String, email: String) : User(name, email, password) {}