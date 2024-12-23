import interfaces.RoomsTable
import interfaces.UsersTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

class DatabaseManager(private val databasePath: String = "database.db") {
    /**
     * Database Manager
     * Ensures Integrity of the database.
     */

    private val tables: List<Table> = listOf(UsersTable, RoomsTable)

    fun initializeDatabase(args: Array<String>) {
        /**
         * Initialize the database
         *
         * Different Initializing options:
         *  - If the database does not exist, it is created
         *  - If tables are missing in the database
         *  - If the database exists, with the correct data, the database data is loaded
         *  - If "-r" or "--reload-db" flag is present on executing of the program, recreate the database,
         *    matter the state.
         *
         *    Executing the project with args:
         *    .\gradlew run --args='-r'
         */
        val dbFile = File(databasePath)
        val reloadFlag = args.contains("-r") || args.contains("--reload-db")

        Database.connect("jdbc:sqlite:$databasePath", driver = "org.sqlite.JDBC")

        when {
            reloadFlag -> {
                recreateDatabase()
            }
            !dbFile.exists() -> {
                recreateDatabase()
            }
            else -> {
                validateTables()
            }
        }
    }

    private fun recreateDatabase() {
        /**
         * Recreate the database by dropping all the tables and reading them.
         * Add all the default data ti the tables.
         */
        transaction {
            for (table in tables) {
                SchemaUtils.drop(table)
                SchemaUtils.create(table)
            }
        }
        preloadData()
    }

    private fun validateTables() {
        /**
         * Validate the all the tables exist in the database
         */
        transaction {
            for (table in tables) {
                val missingTables = listOf(table).filter { !it.exists() }
                if (missingTables.isNotEmpty()) {
                    recreateDatabase()
                }
            }
        }
    }

    private fun preloadData() {
        /**
         * Preload any default data to be inserted into the database
         */

        transaction {
            if (UsersTable.selectAll().empty()) {
                UsersTable.insert {
                    it[name] = "John"
                    it[password] = "123"
                    it[email] = "john@outlook.com"
                    it[loggedIn] = false
                    it[userType] = "Regular"
                }
                UsersTable.insert {
                    it[name] = "Steve"
                    it[password] = "456"
                    it[email] = "steve@hotmail.co.uk"
                    it[loggedIn] = false
                    it[userType] = "Admin"
                }
                UsersTable.insert {
                    it[name] = "Bob"
                    it[password] = "789"
                    it[email] = "bob@solent.ac.uk"
                    it[loggedIn] = false
                    it[userType] = "Regular"
                }
            }

            if (RoomsTable.selectAll().empty()) {
                RoomsTable.insert {
                    it[roomNumber] = 101
                    it[building] = "The Spark"
                    it[operatingSystem] = "Windows"
                    it[timeSlots] = "9am-11am, 11am-1pm, 1pm-3pm, 3pm-5pm"
                    it[daysOfTheWeek] = "Monday, Tuesday, Wednesday, Thursday, Friday"
                    it[numOfComputers] = 10
                }
                RoomsTable.insert {
                    it[roomNumber] = 202
                    it[building] = "The Spark"
                    it[operatingSystem] = "Linux"
                    it[timeSlots] = "9am-11am, 11am-1pm, 1pm-3pm, 3pm-5pm"
                    it[daysOfTheWeek] = "Monday, Tuesday, Wednesday, Thursday, Friday"
                    it[numOfComputers] = 8
                }
                RoomsTable.insert {
                    it[roomNumber] = 303
                    it[building] = "Herbert Collins"
                    it[operatingSystem] = "Mac"
                    it[timeSlots] = "9am-11am, 11am-1pm, 1pm-3pm, 3pm-5pm"
                    it[daysOfTheWeek] = "Monday, Tuesday, Wednesday, Thursday, Friday"
                    it[numOfComputers] = 2
                }
            }
        }
    }
}
