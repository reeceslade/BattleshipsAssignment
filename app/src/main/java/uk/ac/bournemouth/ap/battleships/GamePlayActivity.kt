package uk.ac.bournemouth.ap.battleships

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GamePlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playgame)

        // Get the ship positions from the intent
        val shipPositions = intent.getSerializableExtra("shipPositions") as HashMap<StudentShip, Pair<Int, Int>>

        // Pass the ship positions to the PlayerGridView in the GamePlayActivity
        val playerGrid = findViewById<NewGridView>(R.id.newGridView)
        playerGrid.setShipPositions(shipPositions)
    }
}

