import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dao.RoomDao

import org.koin.dsl.module
import org.koin.core.context.startKoin

import dao.UserDao

fun main(args: Array<String>) = application {
    val databaseManager = DatabaseManager()
    databaseManager.initializeDatabase(args)

    val appModule = module {
        single { UserDao() }
        single { RoomDao() }
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