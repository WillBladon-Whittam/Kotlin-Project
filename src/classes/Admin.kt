package classes

class Admin(name: String) : User(name) {

    override fun menu(): String{
        return "1. All Bookings\n2. Add Room\n3. Exit"
    }

    fun addRoom(university: University, index: Int, name: String) {
        university.getBuildings()[index].addRoom(Room(name))
    }
}