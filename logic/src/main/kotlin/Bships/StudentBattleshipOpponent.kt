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
    // need to check every ship
    private val shipSizes = intArrayOf(2, 3, 3, 4, 5)
    private val random = Random

    init {
        checkShips(ships, columns, rows)
    }

    companion object {
        fun randomOpponent(
            columns: Int,
            rows: Int,
            shipSizes: IntArray,
            random: Random
        ): StudentBattleshipOpponent {
            val ships = mutableListOf<Ship>()
            for (size in shipSizes) {
                var ship: Ship
                var isValidPlacement: Boolean
                do {
                    val isVertical = random.nextBoolean()
                    val left = random.nextInt(columns)
                    val top = random.nextInt(rows)
                    val bottom = if (isVertical) top + size - 1 else top
                    val right = if (isVertical) left else left + size - 1
                    ship = StudentShip(top, left, bottom, right)
                    isValidPlacement = !ships.any { it.overlaps(ship) } &&
                            left < columns && right < columns && top < rows && bottom < rows
                } while (!isValidPlacement)
                ships.add(ship)
            }
            return StudentBattleshipOpponent(columns, rows, ships)
        }

        private fun checkShips(ships: List<Ship>, columns: Int, rows: Int) {
            ships.forEach { ship ->
                require(ship.top in 0 until rows) {
                    throw IllegalArgumentException("Ship $ship is not in bounds")
                }
                require(ship.bottom in 0 until rows) {
                    throw IllegalArgumentException("Ship $ship is not in bounds")
                }
                require(ship.left in 0 until columns) {
                    throw IllegalArgumentException("Ship $ship is not in bounds")
                }
                require(ship.right in 0 until columns) {
                    throw IllegalArgumentException("Ship $ship is not in bounds")
                }
                ships.filter { it !== ship }.forEach { otherShip ->
                    require(!ship.overlaps(otherShip)) {
                        throw IllegalArgumentException("Ships $ship and $otherShip overlap")
                    }
                }
            }
        }

        private fun Ship.overlaps(other: Ship): Boolean {
            val xOverlap = this.left <= other.right && this.right >= other.left
            val yOverlap = this.top <= other.bottom && this.bottom >= other.top
            return xOverlap && yOverlap
        }

        private fun Ship.coversCoordinate(coord: Coordinate): Boolean {
            return coord.x in left..right && coord.y in top..bottom
        }
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
