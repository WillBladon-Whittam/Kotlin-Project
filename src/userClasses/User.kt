package userClasses

open class User (private var name:String){

    open fun menu(): String{
        return "1. Search for room by building\n2. Search for room by OS\n3. Book a computer\n4. View Bookings\n5. Cancel Bookings\n6. Logout"
    }

    fun getName() : String { return name }

    //searchRoomByBuilding done in main

    fun viewBookings() { println("viewBookings") }

    fun searchRoomByOS() { println("searchRoomByOS") }

    fun bookComputer() { println("bookComputer") }

    fun cancelBooking() { println("cancelBooking") }
}