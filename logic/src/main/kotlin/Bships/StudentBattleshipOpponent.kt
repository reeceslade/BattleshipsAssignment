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
    //need to check every ship
    private fun checkShipsOverlap(ships: List<Ship>): Boolean {
        for (i in ships.indices) {
            for (j in i + 1 until ships.size) {
                val ship1 = ships[i]
                val ship2 = ships[j]
                val leftOverlap = (ship1.left..ship1.right).intersect(ship2.left..ship2.right).count()
                val topOverlap = (ship1.top..ship1.bottom).intersect(ship2.top..ship2.bottom).count()
                if (leftOverlap > 0 && topOverlap > 0) {
                    return true
                }
            }
        }
        return false
    }


    init {
        require(ships.all { it.top in 0 until rows && it.bottom in 0 until rows && it.left in 0 until columns && it.right in 0 until columns }) { "Ships must be within the bounds of the game board" }
        require(!checkShipsOverlap(ships)) { "Ships cannot overlap" }
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