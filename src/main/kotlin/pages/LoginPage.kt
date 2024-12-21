package pages

import pages.admin.HomePageAdminContent
import pages.regular.HomePageRegularContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dao.UserDao
import org.koin.compose.koinInject

class LoginPageContent : Screen {
    /**
     * Composable for the Login Page
     */

    @Composable
    override fun Content() {
        val userDao: UserDao = koinInject()

        val navigator = LocalNavigator.current

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.h5,
                color = Color(0xFF3F51B5)
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(0.8f),
                visualTransformation = PasswordVisualTransformation()
            )

            // Show error message if there is an issue
            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val user = userDao.validateUserLogin(username, password)
                    if (user != null) {
                        if (user.loggedIn) {
                            errorMessage = "You are already logged in."
                        } else {
                            if (user.getUserType() == "Regular") {
                                navigator?.push(HomePageRegularContent())
                            } else {
                                navigator?.push(HomePageAdminContent())
                            }
                            errorMessage = null
                        }

                    } else {
                        errorMessage = "Incorrect username or password."
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
