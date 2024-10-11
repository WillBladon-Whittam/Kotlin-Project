package classes

class Admin(name:String) : User(name) {

    override fun printMenu() : String {
        return "1. All Bookings\n2. Add Room\n3. Exit"
    }

    override fun optionMenu(choice:Int) : Boolean {
        when (choice) {
            1 -> println("All Bookings")
            2 -> println("Add Room")
            3 -> return false
        }
        return true
    }
}