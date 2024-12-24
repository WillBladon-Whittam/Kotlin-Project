package core.services

import core.models.Room
import core.models.University
import data.dao.RoomDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class RoomService : KoinComponent {
    var university = University("Solent")
    private val roomDao: RoomDao = RoomDao()

    // Creating Building (Buildings must be created via the University - buildings cant exist without a university)
    val sparkBuilding = university.createBuilding("The Spark", "TS")
    val herbetCollinsBuilding = university.createBuilding("Herbert Collins", "HS")

    // Creating Rooms (Rooms must be created via Building - rooms cant exist without a building)
    val sparkBuildingRoom1 = sparkBuilding.createWindowsRoom(101, numOfComputers = 10)
    val sparkBuildingRoom2 = sparkBuilding.createLinuxRoom(202, numOfComputers = 8)
    val herbetCollinsBuildingRoom1 = herbetCollinsBuilding.createMacRoom(303, numOfComputers = 2)

}