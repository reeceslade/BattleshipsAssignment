package Bships
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate


class StudentBattleshipOpponent(
    override val columns: Int,
    override val rows: Int,
    override val ships: List<Ship>

) : BattleshipOpponent {
    //need to check every ship
    init {
        checkShips(ships, columns, rows)
    }

    private fun checkShips(ships: List<Ship>, columns: Int, rows: Int) {
        ships.forEach { ship ->

            if (ship.top > ship.bottom) {
                throw IllegalArgumentException("Ship is upside down.")
            }

            require(ship.top in 0 until rows) {
             "Ship $ship is not in bounds"
            }
            require(ship.bottom in 0 until rows) {
                "Ship $ship is not in bounds"
            }
            require(ship.left in 0 until columns) {
                "Ship $ship is not in bounds"
            }
            require(ship.right in 0 until columns) {
                "Ship $ship is not in bounds"
            }
            require(ship.top == ship.bottom || ship.left == ship.right) {
                "Ship $ship is not a rectangle"
            }
            ships.filter { it !== ship }
                .forEach { otherShip ->
                    require(!ship.overlaps(otherShip)) {
                        "Ships $ship and $otherShip overlap"
                    }
                }
        }
    }

    private fun Ship.overlaps(other: Ship): Boolean {
        return (this.left..this.right).intersect(other.left..other.right).isNotEmpty()
                && (this.top..this.bottom).intersect(other.top..other.bottom).isNotEmpty()
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