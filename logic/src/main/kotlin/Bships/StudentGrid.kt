package Bships
//student grid in game to make guesses and place ships
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import uk.ac.bournemouth.ap.battleshiplib.GuessResult
import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.lib.matrix.MutableMatrix
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate

class StudentGrid(override val opponent: StudentBattleshipOpponent) : BattleshipGrid {
    //takes in my opponent and returns the interface
    override val columns: Int
        get() = opponent.columns
    override val rows: Int
        get() = opponent.rows
    override val shipsSunk: BooleanArray = BooleanArray(opponent.ships.size)
//value shipSunk returns a boolean array
    private val cells = MutableMatrix<GuessCell>(columns, rows, GuessCell.UNSET)//what does guesscell unset do
    // do i need this ?? override val ships: List<Ship> get() = opponent.ships


    override fun get(column: Int, row: Int): GuessCell {
        require(column < 0 || column >= columns || row < 0 || row >= rows) {
           "Invalid coordinates: ($column, $row)"
        }
        return cells[column, row]
    }

    override operator fun get(coordinate: Coordinate): GuessCell {
        return get(coordinate.x, coordinate.y)
    }

    override fun shootAt(column: Int, row: Int): GuessResult {
        require(column < 0 || column >= columns || row < 0 || row >= rows) {
        ("Invalid coordinates: ($column, $row)")
        }
        require(cells[column, row] != GuessCell.UNSET) {
            "already been guessed?"
        }
        val shipIndex = opponent.shootAt(column, row)
        cells[column, row] = if (shipIndex >= 0) GuessCell.HIT(shipIndex) else GuessCell.MISS
        return if (shipIndex >= 0) {
            if (opponent.ships[shipIndex].isSunk()) GuessResult.SUNK(shipIndex) else GuessResult.HIT(shipIndex)
        } else {
            GuessResult.MISS
        }
    }

    override fun shootAt(coordinate: Coordinate): GuessResult{
        fireGridChange()
        return shootAt(coordinate.x, coordinate.y)
    }
//ik it overrides the function but why are we using x and y instead of row and colu,m

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
    private fun fireGridChange() {
        //where to call fireGameChange()
        //when we want to change the state of game
        for(listener in gameChangeListeners) {
            listener.onGridChanged(this, columns, rows)
        }
    }
}
