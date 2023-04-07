package Bships

import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate

 class StudentShip(
    override val top: Int,
    override val left: Int,
    override val bottom: Int,
    override val right: Int
) : Ship {
    override val columnIndices: IntRange
        get() = super.columnIndices
    override val rowIndices: IntRange
        get() = super.rowIndices
    override val width: Int
        get() = super.width
    override val height: Int
        get() = super.height
    override val size: Int
        get() = super.size
    override val topLeft: Coordinate
        get() = super.topLeft
    override val bottomRight: Coordinate
        get() = super.bottomRight
}
val ship1 = StudentShip(0, 0, 0, 4) // creates a ship at coordinates (0,0) to (0,4)
val ship2 = StudentShip(2, 3, 5, 3) // creates a ship at coordinates (2,3) to (5,3)
// create more ships as needed...
val ships = listOf(ship1, ship2)


