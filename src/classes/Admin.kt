package classes

class Admin() : User() {

    override fun printMenu() : String {
        return "1. All Bookings\n2. Add Room\n3. Exit"
    }

    override fun optionMenu(choice:Int) : Boolean {
        var running = true
        when (choice) {
             1 -> displayAllBookings()
             2 -> addRoom()
             3 -> running = false
             else -> println("Invalid")
        }
        return running
    }

    private fun displayAllBookings() { println("displayAllBookings") }
    private fun addRoom() { println("addRoom") }
}