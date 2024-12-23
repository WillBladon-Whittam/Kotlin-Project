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
import classes.RegularUser
import dao.UserDao
import org.koin.compose.koinInject
import pages.admin.HomePageAdminContent
import pages.regular.HomePageRegularContent

class RegisterContent : BaseContent() {

    @Composable
    override fun Content() {
        val userDao: UserDao = koinInject()
        val navigator = LocalNavigator.current

        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        var errorMessage by remember { mutableStateOf<String?>(null) }
        var error by remember { mutableStateOf(false) }

        super.Content {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to the University Computer Booking System",
                    style = MaterialTheme.typography.h4.copy(fontSize = 24.sp),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Register to continue",
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
                            text = "Register",
                            style = MaterialTheme.typography.h6.copy(color = Color(0xFF3F51B5)),
                            textAlign = TextAlign.Center
                        )

                        TextField(
                            value = username,
                            onValueChange = {
                                username = it
                                error = false
                            },
                            label = { Text("Username") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xFFF0F0F0),
                                focusedIndicatorColor = if (error) Color.Red else Color(0xFF3F51B5),
                                unfocusedIndicatorColor = if (error) Color.Red else Color.Gray
                            )
                        )

                        TextField(
                            value = email,
                            onValueChange = {
                                email = it
                                error = false
                            },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xFFF0F0F0),
                                focusedIndicatorColor = if (error) Color.Red else Color(0xFF3F51B5),
                                unfocusedIndicatorColor = if (error) Color.Red else Color.Gray
                            )
                        )

                        TextField(
                            value = password,
                            onValueChange = {
                                password = it
                                error = false
                            },
                            label = { Text("Password") },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xFFF0F0F0),
                                focusedIndicatorColor = if (error) Color.Red else Color(0xFF3F51B5),
                                unfocusedIndicatorColor = if (error) Color.Red else Color.Gray
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

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ActionButton(text = "Register") {
                                if (username == "" || email == "" || password == "") {
                                    errorMessage = "Please complete all fields"
                                    error = true
                                } else {
                                    val user = userDao.insertUser(RegularUser(username, password, email))

                                    if (user == -1) {
                                        errorMessage = "Username already exists!"
                                        error = true
                                    } else {
                                        navigator?.push(HomePageRegularContent())
                                    }
                                }
                            }

                            ActionButton(text = "Return") {
                                navigator?.popUntilRoot()
                            }
                        }
                    }
                }
            }
        }
    }
}
