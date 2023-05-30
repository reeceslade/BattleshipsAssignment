package uk.ac.bournemouth.ap.battleships

import Bships.StudentShip
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import uk.ac.bournemouth.ap.battleshiplib.Ship

class PlayerPlacementActivity: AppCompatActivity() {
    private val shipPositions = HashMap<Ship, Pair<Int, Int>>() // Store the ship positions here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placement_grid)

        val placementBtn = findViewById<Button>(R.id.placementBtn)
        placementBtn.setOnClickListener {
            // Store the ship positions in the HashMap (replace with your own logic)
            val ship1 = StudentShip(1, 4, 5, 4)
            val ship2 = StudentShip(3, 4, 3, 9)
            shipPositions[ship1] = Pair(5, 4)
            shipPositions[ship2] = Pair(4, 1)

            val intent = Intent(this, GamePlayActivity::class.java)
            intent.putExtra("shipPositions", shipPositions)
            startActivity(intent)
        }
    }
}
