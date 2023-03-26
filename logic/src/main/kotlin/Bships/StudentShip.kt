package Bships

import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate

class StudentShip(
    override val top: Int,
    override val left: Int,
    override val bottom: Int,
    override val right: Int
) : Ship {
    private val hits = mutableSetOf<Coordinate>()
    //determines whether ship has been sunk or not

    override fun isSunk(): Boolean {
        return hits.size == size
    }
}