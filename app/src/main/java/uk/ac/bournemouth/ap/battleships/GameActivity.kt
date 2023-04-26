import Bships.StudentBattleshipOpponent
import Bships.StudentGrid
import Bships.StudentShip
import Bships.StudentShip.Companion.generateRandomShips
import uk.ac.bournemouth.ap.battleshiplib.Ship
import android.content.Context
import android.content.Intent
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import kotlin.random.Random

class GameActivity(private val context: Context) {
    // Retrieve the ships list from the intent extras
    private var shipsList: ArrayList<Ship>? = null
    private var opponent: StudentBattleshipOpponent? = null
/*
    init {
        // Check if ships list is null
        val intent = Intent(this.context, GameActivity::class.java) // Update with proper intent from your app
        if (intent.hasExtra("shipsList")) {
            shipsList = intent.getParcelableArrayListExtra("shipsList")
        }

        // Generate ships randomly if list is null
        if (shipsList == null) {
            shipsList = generateRandomShips()
        }

        // Create an opponent instance
        setOpponent()

        // Create a grid instance with opponent and grid size
        val gridSize = 10 // Update with proper grid size
        val opponent = this.opponent // Store opponent as local variable
        if (opponent != null) {
            val grid = StudentGrid(StudentBattleshipOpponent(10,10,shipsList)) // Pass opponent and gridSize as arguments
        }
    }

    // Set opponent using shipsList
    private fun setOpponent() {
        shipsList?.let { ships ->
            opponent = StudentBattleshipOpponent(10, 10, ships)
        }
    }
}*/
}

