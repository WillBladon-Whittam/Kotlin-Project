package pages.regular

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import core.services.RoomService
import data.dao.RoomDao
import org.koin.compose.koinInject
import pages.BaseContent

class SearchRoomContent : BaseContent() {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val roomService: RoomService = koinInject()

        var selectedBuilding by remember { mutableStateOf("All Buildings") }
        var selectedOS by remember { mutableStateOf("All OS") }
        var expandedBuilding by remember { mutableStateOf(false) }
        var expandedOS by remember { mutableStateOf(false) }

        val buildings = listOf("All Buildings") + roomService.university.getBuildings().map { it.name }
        val operatingSystems = listOf("All OS", "Windows", "Linux", "Mac")

        var searchResults by remember { mutableStateOf("") }

        super.Content {
            Text(
                text = "Search for Room",
                style = MaterialTheme.typography.h4.copy(fontSize = 24.sp),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                        onClick = { expandedOS = true },
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
                            Text(text = selectedOS, color = Color.White) // White text color
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Arrow",
                                tint = Color.White
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = expandedOS,
                        onDismissRequest = { expandedOS = false }
                    ) {
                        operatingSystems.forEach { os ->
                            DropdownMenuItem(onClick = {
                                selectedOS = os
                                expandedOS = false
                            }) {
                                Text(text = os)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ActionButton(text = "Search for room") {
                val allRooms = if (selectedBuilding == "All Buildings") {
                    roomService.university.getRooms()
                } else {
                    val building = roomService.university.getBuildingByName(selectedBuilding)
                    building?.getRooms() ?: listOf()
                }

                val filteredRooms = if (selectedOS == "All OS") {
                    allRooms
                } else {
                    allRooms.filter { it.getOperatingSystem() == selectedOS }
                }

                searchResults = if (filteredRooms.isNotEmpty()) {
                    filteredRooms.joinToString(separator = "\n\n") { it.toString() }
                } else {
                    "No results found."
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(1.dp, Color.Gray)
                    .background(Color(0xFFF0F0F0))
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    if (searchResults.isNotEmpty()) {
                        val rooms = searchResults.split("\n\n")
                        rooms.forEachIndexed { index, room ->
                            Text(
                                text = room,
                                color = Color.Black,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            if (index < rooms.size - 1) {
                                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                            }
                        }
                    } else {
                        Text(
                            text = "No results to display.",
                            color = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ActionButton(text = "Return") { navigator?.pop() }
        }
    }
}
