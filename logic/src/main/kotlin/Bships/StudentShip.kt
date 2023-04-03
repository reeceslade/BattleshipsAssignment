package Bships

import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate

enum class StudentShip(
    override val top: Int,
    override val left: Int,
    override val bottom: Int,
    override val right: Int
) : Ship {
    DESTROYER(0, 0, 1, 0),
    //2 holes
    SUBMARINE(0, 1, 2, 1),
    //3 holes
    CRUISER(2, 2, 4, 2),
    //3 holes
    BATTLESHIP(3, 5, 7, 5),
    //4 holes
    CARRIER(6, 8, 12, 8);
    //5 holes

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



