package pages.regular

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

class HomePageRegularContent : Screen {
    /**
     * Composable for the Home Page of an Regular User
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
                text = "University Computer Booking System - Regular User",
                style = MaterialTheme.typography.h5,
                color = Color(0xFF3F51B5)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Admin Actions
            Text(
                text = "User Actions",
                style = MaterialTheme.typography.h6,
                color = Color(0xFF3F51B5)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Search for room by building */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search for room by building")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Search for room by OS */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search for room by OS")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Book a computer */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Book a computer")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* View Bookings */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Bookings")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Cancel Bookings */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel Bookings")
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
