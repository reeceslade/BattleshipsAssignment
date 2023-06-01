package uk.ac.bournemouth.ap.battleships

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
class GamePlayActivity : AppCompatActivity() {
    @SuppressLint("LogConditional")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playgame)

        // Get the ship positions from the intent
        val shipPositions = intent.getParcelableArrayListExtra<StudentShip>("shipPositions")

        if (shipPositions != null) {
            for (ship in shipPositions) {
                Log.d("Ship Positions", "Ship: $ship")
            }
        }

        // Pass the ship positions to the PlayerGridView in the GamePlayActivity
        val playerGrid = findViewById<NewGridView>(R.id.newGridView)
        if (shipPositions != null) {
            playerGrid.setShipPositions(shipPositions)
        }
    }
}
