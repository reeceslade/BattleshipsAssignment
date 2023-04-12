package Bships
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate
import kotlin.random.Random


class StudentBattleshipOpponent(
    override val columns: Int,
    override val rows: Int,
    override val ships: List<Ship>

) : BattleshipOpponent {
    init {
        require(ships.all { it.top in 0 until rows && it.bottom in 0 until rows && it.left in 0 until columns && it.right in 0 until columns }) { "Ships must be within the bounds of the game board" }
        val carrier = StudentShip(0, 0, 0, 4) // creates a ship at coordinates (0,0) to (0,4)
        val battleship = StudentShip(1, 1, 4, 1) // creates a ship at coordinates (1,1) to (4,1)
        val cruiser = StudentShip(2, 2, 4, 2) // creates a ship at coordinates (2,2) to (4,2)
        val submarine = StudentShip(3, 3, 5, 3) // creates a ship at coordinates (3,3) to (5,3)
        val destroyer = StudentShip(4, 4, 5, 4) // creates a ship at coordinates (4,4) to (5,4)
        val ships = listOf(carrier, battleship, cruiser, submarine, destroyer)

    }
    fun createOpponent( columns: Int,
                        rows: Int,
                        shipSizes: IntArray,
                        random: Random
    ): StudentBattleshipOpponent {

        val maxShipSize = shipSizes.maxOrNull() ?: 0
        require(rows >= maxShipSize) { "The number of rows must be greater than or equal to the maximum ship size" }
        require(columns >= maxShipSize) { "The number of columns must be greater than or equal to the maximum ship size" }

        val ships = mutableListOf<StudentShip>()
        for (size in shipSizes) {
            var ship: StudentShip
            do {
                val top = random.nextInt(rows - size + 1)
                val left = random.nextInt(columns)
                val bottom = top + size - 1
                val right = left
                ship = StudentShip(top, left, bottom, right)
            } while (ships.any { it.overlaps(ship) } || bottom > rows - 1)
            ships.add(ship)
        }
        return StudentBattleshipOpponent(columns, rows, ships)
    }

    private fun Ship.coversCoordinate(coord: Coordinate): Boolean {
        return coord.x in left..right && coord.y in top..bottom
    }

    override fun shipAt(column: Int, row: Int): BattleshipOpponent.ShipInfo<Ship>? {
        for (i in ships.indices) {
            val ship = ships[i]
            if (ship.coversCoordinate(Coordinate(column, row))) {
                return BattleshipOpponent.ShipInfo(i, ship)
            }
        }
        return null
    }
}