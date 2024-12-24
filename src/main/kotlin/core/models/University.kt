/**
 * @author  William Bladon-Whittam
 */

package core.models

class University(val name: String) {
    /**
     * University object to store all the buildings of the University
     * There should only be one university required for the project - but multiple can be used
     */
    private var buildings = mutableListOf<Building>()

    fun getBuildings() : List<Building> {
        return buildings
    }

    fun getBuildingByName(buildingName: String) : Building? {
        for (building in buildings) {
            if (buildingName == building.name) {
                return building
            }
        }
        return null
    }

    fun getRooms() : List<Room> {
        return this.getBuildings().flatMap { it.getRooms() }
    }

    fun getComputer() : List<Computer> {
        return this.getRooms().flatMap { it.getComputers() }
    }

    fun getBookings() : List<ComputerBooking> {
        return this.getComputer().flatMap { it.getBookings() }
    }

    private fun addBuilding(building: Building) {
        buildings.add(building)
    }

    fun createBuilding(name: String, code: String): Building {
        val building = Building(name, code, this)
        this.addBuilding(building)
        return building
    }

    // Assume all buildings have different names
    fun findBuildingByName (searchName: String) : Building? {
        for(currentBuilding in buildings) {
            if(currentBuilding.name == searchName) {
                return currentBuilding
            }
        }
        return null
    }

    override fun toString() : String {
        return "University: $name Buildings: $buildings"
    }
}