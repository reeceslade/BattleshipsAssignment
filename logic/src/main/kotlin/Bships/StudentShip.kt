package Bships

import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate

 class StudentShip(
    override val top: Int,
    override val left: Int,
    override val bottom: Int,
    override val right: Int
) : Ship {
     fun overlaps(other: StudentShip): Boolean {
         val leftOverlap = (left..right).intersect(other.left..other.right).count()
         val topOverlap = (top..bottom).intersect(other.top..other.bottom).count()
         return leftOverlap > 0 && topOverlap > 0
     }
 }

val carrier = StudentShip(6, 6, 0, 4) // creates a ship at coordinates (0,0) to (0,4)
val battleship = StudentShip(1, 1, 4, 1) // creates a ship at coordinates (1,1) to (4,1)
val cruiser = StudentShip(2, 2, 4, 2) // creates a ship at coordinates (2,2) to (4,2)
val submarine = StudentShip(3, 3, 5, 3) // creates a ship at coordinates (3,3) to (5,3)
val destroyer = StudentShip(4, 4, 5, 4) // creates a ship at coordinates (4,4) to (5,4)

val ships = listOf(carrier, battleship, cruiser, submarine, destroyer)
