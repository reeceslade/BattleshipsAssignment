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



