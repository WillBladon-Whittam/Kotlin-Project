//Author - Charles Clark
package classes

class Room(val name:String, val computers:Int, val timeSlots:List<String>) {
    private val bookings: MutableMap<String, MutableList<MutableList<String>>>

    //Initialises the bookings for the room where all slots are created unbooked
    init {
        val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        bookings = mutableMapOf()

        for (day in daysOfWeek) {
            val grid: MutableList<MutableList<String>> = MutableList(timeSlots.size) { MutableList(computers) { "-" } }
            bookings[day] = grid
        }
    }

    //Returns all the bookings for a selected day
    fun getBookings(day: String): MutableList<MutableList<String>>? {
        return bookings[day]
    }

    //Books the chosen time slot for a computer on a selected day with the persons name
    fun book(name:String, day: String, computer:Int, timeSlot: String) {
        val rowIndex = timeSlots.indexOf(timeSlot)
        bookings[day]?.get(rowIndex)?.set(computer, name)
    }
}