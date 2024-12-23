package pages.admin

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

class HomePageAdminContent : BaseContent() {
    /**
     * Composable for the Home Page of an Admin User
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
                text = "Admin Dashboard",
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
                        text = "Admin Actions",
                        style = MaterialTheme.typography.h6.copy(color = Color(0xFF3F51B5)),
                        textAlign = TextAlign.Center
                    )

                    ActionButton(text = "View All Bookings") { /* View all bookings */ }
                    ActionButton(text = "Manage Rooms") { /* Manage Rooms */ }
//                    ActionButton(text = "Add a Room") { /* Add a Room */ }
//                    ActionButton(text = "Modify a Room") { /* Modify a Room */ }
//                    ActionButton(text = "Delete a Room") { /* Delete a Room */ }
                    ActionButton(text = "Manage Users") { /* Manage Users */ }
//                    ActionButton(text = "Add a User") { /* Add a User */ }
//                    ActionButton(text = "Modify a User") { /* Modify a User */ }
//                    ActionButton(text = "Delete a User") { /* Delete a User */ }

                    ActionButton(
                        text = "Logout",
                        backgroundColor = Color(0xFFB71C1C)
                    ) {
                        navigator?.popUntilRoot()
                    }
                }
            }
        }
    }
}
