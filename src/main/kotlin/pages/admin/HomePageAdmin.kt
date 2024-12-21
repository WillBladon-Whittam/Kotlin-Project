package pages.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import pages.LoginPageContent

class HomePageAdminContent : Screen {
    /**
     * Composable for the Home Page of an Admin User
     */
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "University Computer Booking System - Admin User",
                style = MaterialTheme.typography.h5,
                color = Color(0xFF3F51B5)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Admin Actions
            Text(
                text = "Admin Actions",
                style = MaterialTheme.typography.h6,
                color = Color(0xFF3F51B5)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* View all bookings */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View All Bookings")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Add a Room */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add a Room")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Modify a Room */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Modify a Room")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Delete a Room */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Delete a Room")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Add a User */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add a User")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Modify a User */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Modify a User")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Delete a User */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Delete a User")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navigator?.push(LoginPageContent()) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }
        }
    }
}
