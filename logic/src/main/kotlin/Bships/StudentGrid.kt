package Bships
//student grid in game to make guesses and place ships
import uk.ac.bournemouth.ap.battleshiplib.*
import uk.ac.bournemouth.ap.lib.matrix.MutableMatrix
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate

class StudentGrid(override val opponent: StudentBattleshipOpponent) : BattleshipGrid {
    //takes in my opponent and returns the interface
    override val columns: Int
        get() = opponent.columns
    override val rows: Int
        get() = opponent.rows
    override val shipsSunk: BooleanArray = BooleanArray(opponent.ships.size)
    val cells = MutableMatrix<GuessCell>(columns, rows, GuessCell.UNSET)

    //player turn?

    override fun get(column: Int, row: Int): GuessCell {
        require(column in 0 until columns && row in 0 until rows) {
           "Invalid coordinates: ($column, $row)"
        }
        return cells[column, row]
    }

    override fun shootAt(column: Int, row: Int): GuessResult {

        require(column in 0 until columns && row in 0 until rows) {
        ("Invalid coordinates: ($column, $row)")
            //if col and row < 0 invalid coords
        }
        require(cells[column, row] == GuessCell.UNSET) {
            "already been guessed?"
            //this needs to be changed as this exits the game if same cell is touched
        }
        val foundShip: BattleshipOpponent.ShipInfo<Ship>? = opponent.shipAt(column, row)
        //foundship gets the coordinate and sees if the ship is there
        val result : GuessResult
        if(foundShip==null) {
            cells[column, row] = GuessCell.MISS
            result = GuessResult.MISS
            //if the coords are empty (dont have the ship and index) == MISS
        } else {
            val (shipIndex, ship) = foundShip
            cells[column, row] = GuessCell.HIT(shipIndex)
            var isSunk = true
            ship.forEachIndex {x, y -> isSunk = isSunk && cells[x,y] is GuessCell.HIT}

            if(isSunk) {
                val state = GuessCell.SUNK(shipIndex)
                ship.forEachIndex { x, y -> cells[x,y] = state  }
                shipsSunk[shipIndex] = true
                result = GuessResult.SUNK(shipIndex)
            } else {
                result = GuessResult.HIT(shipIndex)
            }

        }
        fireGridChange(column, row)
        return result
    }
    private val gameChangeListeners = mutableListOf<BattleshipGrid.BattleshipGridListener>()
    override fun addOnGridChangeListener(listener: BattleshipGrid.BattleshipGridListener) {
       if(listener !in gameChangeListeners){
           gameChangeListeners.add(listener)
       }
    }
    override fun removeOnGridChangeListener(listener: BattleshipGrid.BattleshipGridListener) {
        gameChangeListeners.remove(listener)
    }
    /**
     * This will execute the onGameChange method on all listeners (like the ButtonView).
     */
    private fun fireGridChange(column: Int, row: Int) {
        //where to call fireGameChange()
        //when we want to change the state of game
        for(listener in gameChangeListeners) {
            listener.onGridChanged(this, column, row)
        }
    }
}
