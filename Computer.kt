class Computer(var globalId: String) {

    val daysOfTheWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val timeSlots =  listOf("9-11", "11-1", "1-3", "3-5")

    override fun toString() : String {
        return "Global ID: $globalId"
    }
}