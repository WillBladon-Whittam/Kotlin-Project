//Author Charles Clark
package classes

class University(val name: String) {
    private var buildings = mutableListOf(Building("Spark"),Building("Herbert Collins"))

    fun getBuildings(): List<Building> {
        return buildings
    }
}