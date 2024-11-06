/**
 * @author  William Bladon-Whittam
 */

package main.kotlin.classes

class University(val name: String) {
    /**
     * University object to store all the buildings of the University
     * There should only be one university required for the project - but multiple can be used
     */
    private var buildings = mutableListOf<Building>()

    fun getBuildings() : List<Building> {
        return buildings
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
        return "main.kotlin.classes.University: $name Buildings: $buildings"
    }
}