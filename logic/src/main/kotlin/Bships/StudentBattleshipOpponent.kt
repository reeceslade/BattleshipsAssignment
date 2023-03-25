package Bships

import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import uk.ac.bournemouth.ap.battleshiplib.GuessResult
import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate
//opponent ship placement
//19 mins video
//want the ship and the index
//determine whether ship was sunk
//ship.All
class StudentBattleshipOpponent(
    override val columns: Int,
    override val rows: Int,
    override val ships: List<Ship>

) : BattleshipOpponent {
    //ship.All If all ship coordinates are hit == sunk else false
    data class ShipInfo<out S : Ship>(val index: Int, val ship: S)

    // Helper function that determines whether a ship covers a given coordinate
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

    //fun BattleshipOpponent.shipAt(coordinate: Coordinate): BattleshipOpponent.ShipInfo<Ship>? =
    // shipAt(coordinate.x, coordinate.y)

    override fun shootAt(column: Int, row: Int): Int {
        val shipInfo = shipAt(column, row)
        return if (shipInfo != null) {
            // A ship was hit
            shipInfo.ship.hit(Coordinate(column, row))
            if (shipInfo.ship.isSunk()) {
                // The ship was sunk
                BattleshipOpponent.SUNK
            } else {
                // The ship was hit but not sunk
                BattleshipOpponent.HIT
            }
        } else {
            // The shot missed
            BattleshipOpponent.MISSED
        }
    }
}
