package data.dao

import core.models.*
import data.DatabaseManager
import org.jetbrains.exposed.sql.transactions.transaction

import data.schema.RoomsDaoInterface
import data.schema.RoomsTable
import org.jetbrains.exposed.sql.*

class RoomDao : RoomsDaoInterface {
    /**
     * Room Data Access Object
     */

    init {
        transaction {
            SchemaUtils.create(RoomsTable)
        }
    }

    override fun getRooms(university: University) : List<Room> {
        var roomsList = listOf<Room>()
        transaction {
            val results = RoomsTable.selectAll()
            roomsList = results.map { row ->
                if (row[RoomsTable.operatingSystem] == "Windows") {
                    WindowsRoom(
                        row[RoomsTable.roomNumber],
                        university.getBuildingByName(row[RoomsTable.building])!!,
                        row[RoomsTable.daysOfTheWeek].split(",").map { it.trim() },
                        row[RoomsTable.timeSlots].split(",").map { it.trim() },
                        row[RoomsTable.numOfComputers]
                    )
                } else if (row[RoomsTable.operatingSystem] == "Mac") {
                    MacRoom(
                        row[RoomsTable.roomNumber],
                        university.getBuildingByName(row[RoomsTable.building])!!,
                        row[RoomsTable.daysOfTheWeek].split(",").map { it.trim() },
                        row[RoomsTable.timeSlots].split(",").map { it.trim() },
                        row[RoomsTable.numOfComputers]
                    )
                    } else {
                    LinuxRoom(
                        row[RoomsTable.roomNumber],
                        university.getBuildingByName(row[RoomsTable.building])!!,
                        row[RoomsTable.daysOfTheWeek].split(",").map { it.trim() },
                        row[RoomsTable.timeSlots].split(",").map { it.trim() },
                        row[RoomsTable.numOfComputers]
                    )
                }
            }
        }
        return roomsList
    }

    override fun insertRoom(room: Room): Boolean {
        TODO("Not yet implemented")
    }
}