package pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import core.models.Computer
import core.models.ComputerBooking
import core.models.User
import core.services.BookingService
import core.services.RoomService
import core.services.UserService
import org.koin.compose.koinInject

class BookComputerContent : BaseContent() {

    @Composable
    override fun Content() {
        val bookingService: BookingService = koinInject()
        val roomService: RoomService = koinInject()
        val userService: UserService = koinInject()

        val navigator = LocalNavigator.current

        var selectedBuilding by remember { mutableStateOf("Select Building") }
        var selectedRoom by remember { mutableStateOf("Select Room") }
        var selectedTimeSlot by remember { mutableStateOf("") }
        var selectedDay by remember { mutableStateOf("") }
        var expandedBuilding by remember { mutableStateOf(false) }
        var expandedRoom by remember { mutableStateOf(false) }

        var showDialog by remember { mutableStateOf(false) }
        var dialogMessage by remember { mutableStateOf("") }
        var dialogAction: (() -> Unit)? by remember { mutableStateOf(null) }

        val buildings = roomService.university.getBuildings().map { it.name }
        val rooms = if (selectedBuilding != "Select Building") {
            roomService.university.getBuildingByName(selectedBuilding)?.getRooms()?.map { it.roomNumber.toString() }
                ?: emptyList()
        } else {
            emptyList()
        }
        val timeSlots = listOf("9am-11am", "11am-1pm", "1pm-3pm", "3pm-5pm")
        val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")

        var computers by remember { mutableStateOf<List<Computer>>(emptyList()) }
        var showKey by remember { mutableStateOf(false) }

        // Fetch computers when all filters are selected
        LaunchedEffect(selectedRoom, selectedTimeSlot, selectedDay) {
            if (selectedRoom != "Select Room" && selectedTimeSlot.isNotBlank() && selectedDay.isNotBlank()) {
                val building = roomService.university.getBuildingByName(selectedBuilding)
                val room = building?.getRoomByNumber(selectedRoom.toInt())
                if (room != null) {
                    computers = room.getComputers()
                    showKey = true // Show the key when computers are available
                }
            }
        }

        super.Content {
            // Header
            Text(
                text = "Book a Computer",
                style = MaterialTheme.typography.h4.copy(fontSize = 24.sp),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Top bar with "Return" button and Key
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // "Return" button
                IconButton(onClick = { navigator?.pop() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                // Key visibility condition
                if (showKey) {
                    Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                        Row {
                            Text(
                                text = "Available",
                                color =Color(0xFF4CAF50),
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "Booked by You",
                                color = Color(0xFF9C27B0),
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "Booked by Someone Else",
                                color = Color(0xFFF44336),
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                    TextButton(
                        onClick = { expandedBuilding = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0x800D47A1))
                            .border(1.dp, Color.Gray)
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = selectedBuilding, color = Color.White)
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Arrow",
                                tint = Color.White
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = expandedBuilding,
                        onDismissRequest = { expandedBuilding = false }
                    ) {
                        buildings.forEach { building ->
                            DropdownMenuItem(onClick = {
                                selectedBuilding = building
                                expandedBuilding = false
                            }) {
                                Text(text = building)
                            }
                        }
                    }
                }

                Box(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    TextButton(
                        onClick = { expandedRoom = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0x800D47A1)) // Semi-transparent purple background
                            .border(1.dp, Color.Gray)
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = selectedRoom, color = Color.White) // White text color
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Arrow",
                                tint = Color.White
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = expandedRoom,
                        onDismissRequest = { expandedRoom = false }
                    ) {
                        rooms.forEach { os ->
                            DropdownMenuItem(onClick = {
                                selectedRoom = os
                                expandedRoom = false
                            }) {
                                Text(text = os)
                            }
                        }
                    }
                }
            }

            // Day and Time Slot Selection Buttons
            if (selectedBuilding != "Select Building" && selectedRoom != "Select Room") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Day Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        days.forEach { day ->
                            Button(
                                onClick = { selectedDay = day },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = if (selectedDay == day) Color(0xFF3F51B5) else Color.LightGray
                                ),
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(text = day, color = Color.White)
                            }
                        }
                    }

                    // Time Slot Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        timeSlots.forEach { timeSlot ->
                            Button(
                                onClick = { selectedTimeSlot = timeSlot },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = if (selectedTimeSlot == timeSlot) Color(0xFF3F51B5) else Color.LightGray
                                ),
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(text = timeSlot, color = Color.White)
                            }
                        }
                    }
                }
            }
            // Computers Grid
            if (selectedRoom != "Select Room" && computers.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    computers.chunked(5).forEach { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            row.forEach { computer ->
                                ComputerCircle(
                                    computer = computer,
                                    currentUser = userService.loggedIn!!,
                                    selectedDay = selectedDay,
                                    selectedTimeSlot = selectedTimeSlot,
                                    onComputerClick = {
                                        val booking = computer.getBookings().firstOrNull {
                                            it.day == selectedDay && it.timeSlot == selectedTimeSlot
                                        }
                                        when {
                                            booking?.student?.name == userService.loggedIn!!.name -> {
                                                dialogMessage =
                                                    "Do you want to cancel your booking for Computer ${computer.globalId}?"
                                                dialogAction = {
                                                    bookingService.cancelBooking(computer, booking)
                                                    computers = roomService.university
                                                        .getBuildingByName(selectedBuilding)
                                                        ?.getRoomByNumber(selectedRoom.toInt())
                                                        ?.getComputers() ?: emptyList()
                                                }
                                                showDialog = true
                                            }

                                            booking == null -> {
                                                dialogMessage =
                                                    "Do you want to book Computer ${computer.globalId}?"
                                                dialogAction = {
                                                    bookingService.addBooking(
                                                        computer,
                                                        ComputerBooking(
                                                            computer.globalId,
                                                            selectedDay,
                                                            selectedTimeSlot,
                                                            userService.loggedIn!!)
                                                    )
                                                    computers = roomService.university
                                                        .getBuildingByName(selectedBuilding)
                                                        ?.getRoomByNumber(selectedRoom.toInt())
                                                        ?.getComputers() ?: emptyList()
                                                }
                                                showDialog = true
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            } else if (selectedRoom != "Select Room") {
                Text(
                    text = "No computers found in this room.",
                    color = Color.Gray,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Confirmation Dialog
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirmation") },
                text = { Text(dialogMessage) },
                confirmButton = {
                    TextButton(onClick = {
                        dialogAction?.invoke()
                        showDialog = false
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("No")
                    }
                }
            )
        }
    }

    @Composable
    private fun ComputerCircle(
        computer: Computer,
        currentUser: User,
        selectedDay: String,
        selectedTimeSlot: String,
        onComputerClick: () -> Unit
    ) {
        val booking = computer.getBookings().firstOrNull {
            it.day == selectedDay && it.timeSlot == selectedTimeSlot
        }
        val color = when {
            booking?.student?.name == currentUser.name -> Color(0xFF9C27B0) // Purple for current user's booking
            booking != null -> Color(0xFFF44336) // Red for another user's booking
            else -> Color(0xFF4CAF50) // Green for available
        }

        Box(
            modifier = Modifier
                .size(60.dp)
                .background(color, shape = CircleShape)
                .clickable(onClick = onComputerClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = computer.globalId,
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
        }
    }
}
