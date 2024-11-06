/**
 * @author  William Bladon-Whittam
 * @author  Charlie Clark
 */

package main.kotlin.classes

data class User(
    /**
     * During integration moved methods to more appropriate places, so only a data class is required
     */
    val name: String,
    val password: String,
    val admin: Boolean,
)
