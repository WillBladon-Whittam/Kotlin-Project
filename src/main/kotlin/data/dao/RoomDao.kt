package data.dao

import data.DatabaseManager
import core.models.Room
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

    override fun insertRoom(room: Room): Boolean {
        TODO("Not yet implemented")
    }
}