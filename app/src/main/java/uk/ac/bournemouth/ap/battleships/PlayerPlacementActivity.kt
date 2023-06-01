package uk.ac.bournemouth.ap.battleships
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import uk.ac.bournemouth.ap.battleshiplib.Ship

class PlayerPlacementActivity : AppCompatActivity() {
    private lateinit var playerGridView: PlayerGridView

    @SuppressLint("LogConditional")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placement_grid)
        playerGridView = findViewById(R.id.playerGrid)

        val placementBtn = findViewById<Button>(R.id.placementBtn)
        placementBtn.setOnClickListener {
            // Create a new HashMap to store the ship positions
            val shipPositions = HashMap<Ship, Pair<Int, Int>>()

            // Iterate over the ships and add their positions to the HashMap
            for (ship in playerGridView.ships) {
                shipPositions[ship] = Pair(ship.left, ship.top)
            }

            // Log the ship positions
            for ((ship, position) in shipPositions) {
                Log.d("Ship Positions", "Ship: $ship, Position: $position")
            }

            // Pass the shipPositions HashMap to the GamePlayActivity
            val intent = Intent(this, GamePlayActivity::class.java)
            intent.putExtra("shipPositions", shipPositions)
            startActivity(intent)
        }
    }
}
