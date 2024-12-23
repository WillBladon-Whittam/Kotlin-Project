package pages.regular

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import pages.BaseContent

class HomePageRegularContent : BaseContent() {
    /**
     * Composable for the Home Page of a Regular User
     */
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        super.Content {
            Text(
                text = "University Computer Booking System",
                style = MaterialTheme.typography.h4.copy(fontSize = 24.sp),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "- Regular User -",
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
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "User Actions",
                        style = MaterialTheme.typography.h6.copy(color = Color(0xFF3F51B5)),
                        textAlign = TextAlign.Center
                    )

                    ActionButton(text = "Search for room") { navigator?.push(SearchRoomContent()) }
                    ActionButton(text = "Book a computer") { /* Navigate to Book a computer page */ }
                    ActionButton(text = "View Bookings") { /* Navigate to View Bookings page */ }
                    ActionButton(text = "Cancel Bookings") { /* Navigate to Cancel Bookings page */ }

                    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)

                    ActionButton(
                        text = "Logout",
                        backgroundColor = Color(0xFFFF5252),
                        textColor = Color.White
                    ) {
                        navigator?.popUntilRoot()
                    }
                }
            }
        }
    }
}
