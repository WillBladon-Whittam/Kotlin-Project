package classes

open class User (private var name:String){
    private val bookings = mutableListOf<String>()

    open fun menu(): String{
        return "1. Search for room by building\n2. Search for room by OS\n3. Book a computer\n4. View Bookings\n5. Cancel Bookings\n6. Exit"
    }

    fun getBookings() : List<String> {
        return bookings
    }

    fun getName() : String {
        return name
    }

    fun searchRoomByBuilding(index: Int, university: University) : List<Room>? {
        if (university.getBuildings().size > index && index >= 0) {
            return university.getBuildings()[index].getRooms()
        }
        return null
    }

    fun searchRoomByOS() { println("searchRoomByOS") }

    fun bookComputer(computer: String, day: String, time: String) {
        bookings.add("$computer, $day, $time, $name")
    }

    fun viewBooking(): List<String> {
        return bookings
    }

    fun cancelBooking() { println("cancelBooking") }
}