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
        // Check if the given coordinates are valid
        if (column < 0 || column >= columns || row < 0 || row >= rows) {
            throw IllegalArgumentException("Invalid coordinates: ($column, $row)")
            // return GuessResult.INVALID
        }
        // Otherwise return the cell at the given coordinates
        return cells[column, row]
    }

    override operator fun get(coordinate: Coordinate): GuessCell {
        return get(coordinate.x, coordinate.y)
    }

    override fun shootAt(column: Int, row: Int): GuessResult {
        // Check if the given coordinates are valid
        if (column < 0 || column >= columns || row < 0 || row >= rows) {
            //return GuessResult.INVALID
            throw IllegalArgumentException("Invalid coordinates: ($column, $row)")
        }
        // Check if the cell at the given coordinates has already been guessed
        //else if????
        if (cells[column, row] != GuessCell.UNSET) {
            //  return GuessResult.INVALID
            throw IllegalArgumentException("already been guessed?")
        }
        // Update the cell at the given coordinates to reflect the result of the guess
        val shipIndex = opponent.shootAt(column, row)
        cells[column, row] = if (shipIndex >= 0) GuessCell.HIT(shipIndex) else GuessCell.MISS
        // Return the appropriate GuessResult based on the result of the guess
        return if (shipIndex >= 0) {
            if (opponent.ships[shipIndex].isSunk()) GuessResult.SUNK(shipIndex) else GuessResult.HIT(shipIndex)
        } else {
            GuessResult.MISS
        }
    }

    override fun shootAt(coordinate: Coordinate): GuessResult{
        return shootAt(coordinate.x, coordinate.y)
    }
//ik it overrides the function but why are we using x and y instead of row and colu,m

    private val gameChangeListeners = mutableListOf<BattleshipGrid.BattleshipGridListener>()

    override fun addOnGridChangeListener(listener: BattleshipGrid.BattleshipGridListener) {
       if(listener !in gameChangeListeners){
           gameChangeListeners.add(listener)
       }//else remove OnGridChangeListener?
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
