class UserSession {
    private var university = University("Solent")

    init {
        // Adding Hard-Coded Buildings, Rooms and Computers.
        // Creating Building (Buildings must be created via the University - buildings cannot exist without a university)
        val building = university.createBuilding("Building1", "B1")

        // Creating Rooms (Rooms must be created via Building - rooms cannot exist without a building)
        val room1 = building.createWindowsRoom(1)
        val room2 = building.createMacRoom(2)

        // Creating Computers
        val computer1 = Computer("JM302-1")
        val computer2 = Computer("JM301-1")

        // Adding Computers to Rooms
        room1.addComputer(computer1)
        room2.addComputer(computer2)
    }

    fun mainLoop() {
        // Run main system (different options depending on the user) - only the normal user implemented
        var running = true
        while (running) {
            println("1. Search for room by building\n" +
                    "2. Search for room by OS\n" +
                    "3. Book a computer\n" +
                    "4. View Bookings\n" +
                    "5. Cancel Bookings\n" +
                    "6. Quit")

            val number = readlnOrNull()?.toIntOrNull() ?: 0

            when(number) {
                // Search for room by building
                1 -> this.searchRoomByBuilding()
                // Search for room by OS
                2 -> this.searchRoomByOS()
                3 -> {} // Book a computer
                4 -> {} // View Bookings
                5 -> {} // Cancel Bookings
                6 -> running = false

            }
        }
    }

    private fun searchRoomByBuilding() {
        println("Enter building name")
        val buildingName = readLine() ?: ""
        val rooms = university.findBuildingByName(buildingName)?.getRooms() ?: listOf()
        for (room in rooms) {
            println(room)
        }
    }

    private fun searchRoomByOS() {
        println("Enter Operating System\n" +
                "1. Windows\n" +
                "2. MAC\n" +
                "3. Linux")
        val number = readlnOrNull()?.toIntOrNull() ?: 0
        var os: String? = null

        when(number) {
            1 ->  os = "Windows"
            2 ->  os = "Mac"
            3 ->  os = "Linux"
            else -> {
                println("Invalid Option")
                return
            }
        }
        // Loop through all the buildings
        for (eachBuilding in university.getBuildings()) {
            for (room in eachBuilding.findRoomByOS(os)) {
                println(room)
            }
        }
    }
}