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
            val direction: Boolean = if (size > columns) {
                false // Vertical placement
            } else if (size > rows) {
                true // Horizontal placement
            } else {
                random.nextBoolean()
            }
            do {
                val top: Int
                val left: Int
                val bottom: Int
                val right: Int
                if (direction) {
                    // Horizontal placement
                    top = random.nextInt(rows)
                    left = random.nextInt(columns - size + 1)
                    bottom = top
                    right = left + size - 1
                } else {
                    // Vertical placement
                    top = random.nextInt(rows - size + 1)
                    left = random.nextInt(columns)
                    bottom = top + size - 1
                    right = left
                }
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