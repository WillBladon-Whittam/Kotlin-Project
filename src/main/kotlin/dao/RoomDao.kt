package dao

import classes.*
import org.jetbrains.exposed.sql.transactions.transaction

import interfaces.RoomsDaoInterface
import interfaces.RoomsTable
import org.jetbrains.exposed.sql.*

class RoomDao : RoomsDaoInterface {
    /**
     * Room Data Access Object
     */
    var university = University("Solent")

    val sparkBuilding = university.createBuilding("The Spark", "TS")
    val herbertCollinsBuilding = university.createBuilding("Herbert Collins", "HS")

    init {
        transaction {
            SchemaUtils.create(RoomsTable)
        }
    }

    override fun insertRoom(room: Room): Boolean {
        TODO("Not yet implemented")
    }

    override fun getRooms(buildingName: String?): List<Room> {
        /**
         * Get Rooms from the database
         *
         * The buildingName parameter can specify whether to return Rooms from a specific building.
         * To return Rooms from all buildings, buildingName is set to null
         */
        val rooms = mutableListOf<Room>()

        transaction {
            RoomsTable.selectAll().forEach { row ->
                val roomBuildingName = row[RoomsTable.building]
                if (buildingName == null || buildingName == roomBuildingName) {
                    val building = university.findBuildingByName(roomBuildingName)

                    if (building != null) {
                        val timeSlots = row[RoomsTable.timeSlots].split(",").map { it.trim() }
                        val weekDays = row[RoomsTable.daysOfTheWeek].split(",").map { it.trim() }

                        val room = when (row[RoomsTable.operatingSystem]) {
                            "Windows" -> building.createWindowsRoom(
                                row[RoomsTable.roomNumber],
                                timeSlots,
                                weekDays,
                                row[RoomsTable.numOfComputers]
                            )
                            "Mac" -> building.createMacRoom(
                                row[RoomsTable.roomNumber],
                                timeSlots,
                                weekDays,
                                row[RoomsTable.numOfComputers]
                            )
                            "Linux" -> building.createLinuxRoom(
                                row[RoomsTable.roomNumber],
                                timeSlots,
                                weekDays,
                                row[RoomsTable.numOfComputers]
                            )
                            else -> throw IllegalArgumentException(
                                "Unknown operating system: ${row[RoomsTable.operatingSystem]}"
                            )
                        }

                        room.let { rooms.add(it) }
                    }
                }
            }
        }
        return rooms
    }
}