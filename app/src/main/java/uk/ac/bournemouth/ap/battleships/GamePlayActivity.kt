package uk.ac.bournemouth.ap.battleships

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uk.ac.bournemouth.ap.battleshiplib.Ship

class GamePlayActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playgame)

        // Get the ship positions from the intent
        val shipPositions = intent.getSerializableExtra("shipPositions") as HashMap<Ship, Pair<Int, Int>>

        // Pass the ship positions to the PlayerGridView in the GamePlayActivity
        val playerGrid = findViewById<PlayerGridView>(R.id.playerGrid)
        playerGrid.setShipPositions(shipPositions)

        /* Convert shipPositions to the appropriate type
val convertedPositions = HashMap<Ship, Pair<Int, Int>>()
for ((shipName, positions) in shipPositions) {
    // Create Ship instance based on the ship name and convert positions to Pair<Int, Int>
    val ship = Ship(shipName)
    val positionPair = Pair(positions[0], positions[1])
    convertedPositions[ship] = positionPair
}

// Pass the converted positions to the PlayerGridView
playerGrid.setShipPositions(convertedPositions)
*/
    }
}
