package classes

class Admin(name:String) : User(name) {

    override fun printMenu() : String {
        return "1. All Bookings\n2. Add Room\n3. Exit"
    }

    override fun optionMenu(choice:Int) : Boolean {
         when (choice) {
            1 -> bookings()
            2 -> addRoom()
            3 -> return false

        }
        return true
    }

    private fun bookings() {
        println("1")
    }

    private fun addRoom() {
        print("Which building are you adding the new room to? : ")
        val building = readlnOrNull().toString()
        print("What is the name of the new room? : ")
        val name = readlnOrNull().toString()

    }
}