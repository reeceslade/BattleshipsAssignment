package Bships

import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate

class StudentBattleshipOpponent(
    override val columns: Int,
    override val rows: Int,
    override val ships: List<Ship>

) : BattleshipOpponent {

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
 /* override fun shootAt(column: Int, row: Int): Int {
        val shipInfo = shipAt(column, row)
        if (shipInfo != null) {
            val ship = shipInfo.ship
            val result = ship.shootAt(column, row)
            return when (result) {
                is GuessResult.HIT -> shipInfo.index + 1
                is GuessResult.SUNK -> -(shipInfo.index + 1)
                else -> throw IllegalStateException("Unexpected result $result")
            }
        }
        return 0
    }
} */

override fun shootAt(column: Int, row: Int): Int {
    val shipInfo = shipAt(column, row)
    if (shipInfo != null) {
        val ship = shipInfo.ship as StudentShip // Cast the ship to your StudentShip class
        val result = (ships.indices as Array<Pair<Int, Int>>).flatMap { (x, y) ->
            if (x == column && y == row) {
                listOf(ship.isSunk())
            } else {
                listOf()
            }
        }.firstOrNull()

        return if (result == true) {
            -(shipInfo.index + 1)
        } else {
            shipInfo.index + 1
        }
    }
    return 0
}
}