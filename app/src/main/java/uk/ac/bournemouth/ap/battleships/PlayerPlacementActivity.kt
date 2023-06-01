package uk.ac.bournemouth.ap.battleships
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PlayerPlacementActivity : AppCompatActivity() {
    private lateinit var playerGridView: PlayerGridView

    @SuppressLint("LogConditional")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placement_grid)
        playerGridView = findViewById(R.id.playerGrid)

        val placementBtn = findViewById<Button>(R.id.placementBtn)
        placementBtn.setOnClickListener {
            // Create a new list to store the ship positions
            val shipPositions = mutableListOf<StudentShip>()

            // Add the ship positions to the list
            shipPositions.addAll(playerGridView.ships)

            // Log the ship positions
            for (ship in shipPositions) {
                Log.d("Ship Positions", "Ship: $ship")
            }

            // Pass the shipPositions list to the GamePlayActivity
            val intent = Intent(this, GamePlayActivity::class.java)
            intent.putParcelableArrayListExtra("shipPositions", ArrayList(shipPositions))
            startActivity(intent)
        }
    }
}
