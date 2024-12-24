import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import core.services.BookingService
import core.services.RoomService
import core.services.UserService
import data.dao.BookingDao
import data.dao.RoomDao

import org.koin.dsl.module
import org.koin.core.context.startKoin

import data.dao.UserDao
import data.DatabaseManager

fun main(args: Array<String>) = application {
    val databaseManager = DatabaseManager()
    databaseManager.initializeDatabase(args)

    val userService = UserService()
    val roomService = RoomService()
    val bookingService = BookingService(roomService, userService)

    val appModule = module {
        single { userService }
        single { roomService }
        single { bookingService }
    }

    startKoin {
        modules(appModule)
    }

    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            App()
        }
    }
}