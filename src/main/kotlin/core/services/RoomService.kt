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

    init {
        val rooms = roomDao.getRooms(university)
        for (room in rooms) {
            room.building.addRoom(room)
        }
    }
}