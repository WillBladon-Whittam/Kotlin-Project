package classes

class University(val name: String) {
    private var buildings = mutableListOf<Building>(Building("Spark"),Building("Herbert Collins"))

    fun getBuildings(): List<Building> {
        return buildings
    }
}

class Building(val name:String) {
    private var rooms = mutableListOf<Room>()

    fun getRooms(): List<Room> {
        return rooms
    }

    fun addRoom(room: Room) {
        rooms.add(room)
    }
}

class Room(val name:String, val computers:Int, val timeSlots:List<String>) {
    private val bookings: MutableMap<String, MutableList<MutableList<String>>>

    init {
        val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        bookings = mutableMapOf()

        for (day in daysOfWeek) {
            val grid: MutableList<MutableList<String>> = MutableList(timeSlots.size) { MutableList(computers) { "-" } }
            bookings[day] = grid
        }
    }

    fun getBookings(day: String): MutableList<MutableList<String>>? {
        return bookings[day]
    }

    fun book(name:String, day: String, computer:Int, timeSlot: String) {
        val rowIndex = timeSlots.indexOf(timeSlot)
        bookings[day]?.get(rowIndex)?.set(computer, name)
    }
}