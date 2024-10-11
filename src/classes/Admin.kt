package classes

class Admin() : User() {

    override fun printMenu() : String {
        return "1. All Bookings\n2. Add Room\n3. Exit"
    }

    override fun optionMenu(choice:Int) : String {
         return when (choice) {
             1 -> "displayAllBookings"
             2 -> "addRoom"
             3 -> "Exit"
             else -> "Invalid"
        }
    }
}