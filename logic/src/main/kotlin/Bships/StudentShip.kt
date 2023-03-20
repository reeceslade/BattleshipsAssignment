package Bships

import uk.ac.bournemouth.ap.battleshiplib.Ship

class StudentShip(
    override val top: Int,
    override val left: Int,
    override val bottom: Int,
    override val right: Int
) : Ship {
}