class University(val name: String) {
    private var buildings = mutableListOf<Building>()

    fun getBuildings() : List<Building> {
        return buildings
    }

    fun addBuilding(building: Building) {
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