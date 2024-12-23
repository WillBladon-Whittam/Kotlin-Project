package pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import dao.UserDao
import org.koin.compose.koinInject
import pages.admin.HomePageAdminContent
import pages.regular.HomePageRegularContent

class LoginPageContent : BasePage() {

    @Composable
    override fun Content() {
        val userDao: UserDao = koinInject()
        val navigator = LocalNavigator.current

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        super.Content {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to University Computer Booking System",
                    style = MaterialTheme.typography.h4.copy(fontSize = 24.sp),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Login to continue",
                    style = MaterialTheme.typography.subtitle1.copy(fontSize = 18.sp),
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = 8.dp,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.h6.copy(color = Color(0xFF3F51B5)),
                            textAlign = TextAlign.Center
                        )

                        TextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("Username") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xFFF0F0F0)
                            )
                        )

                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xFFF0F0F0)
                            )
                        )

                        errorMessage?.let {
                            Text(
                                text = it,
                                color = Color.Red,
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }

                        ActionButton(text = "Login") {
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
                        }
                    }
                }
            }
        }
    }
}
