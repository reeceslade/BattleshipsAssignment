package Bships

import Bships.StudentShip.Companion.generateRandomShips
import uk.ac.bournemouth.ap.battleshiplib.Ship
import kotlin.random.Random

class StudentShip(
    override var top: Int,
    override var left: Int,
    override var bottom: Int,
    override var right: Int
) : Ship {

    companion object {
        fun generateRandomShips(columns: Int, rows: Int): List<StudentShip> {
            val ships = mutableListOf<StudentShip>()
            val random = Random.Default
            val sizes = listOf(5, 4, 3, 3, 2) // sizes of the ships

            for (size in sizes) {
                var ship: StudentShip
                var isValidPlacement: Boolean
                do {
                    val isVertical = random.nextBoolean()
                    val left = random.nextInt(columns)
                    val top = random.nextInt(rows)
                    val bottom = if (isVertical) top + size - 1 else top
                    val right = if (isVertical) left else left + size - 1
                    ship = StudentShip(top, left, bottom, right)
                    isValidPlacement = !ships.any { it.overlaps(ship) } &&
                            ship.top >= 0 && ship.bottom < rows && ship.left >= 0 && ship.right < columns
                } while (!isValidPlacement)
                ships.add(ship)
            }
            return ships
        }
    }

        fun overlaps(other: StudentShip): Boolean {
         val leftOverlap = (left..right).intersect(other.left..other.right).count()
         val topOverlap = (top..bottom).intersect(other.top..other.bottom).count()
         return leftOverlap > 0 && topOverlap > 0
     }
 }

val ships = generateRandomShips(10, 10)
