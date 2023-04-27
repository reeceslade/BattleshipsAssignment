package uk.ac.bournemouth.ap.battleships

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import uk.ac.bournemouth.ap.battleshiplib.Ship

class HomeViewTest : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placement_grid)
        val placementButton = findViewById<Button>(R.id.placementBtn)
        placementButton.setOnClickListener {
            var shipPositionsData: HashMap<Ship, Pair<Int, Int>>
            val intent = Intent(this, HomeView3::class.java)
            startActivity(intent)
            finish()
            // Add your click listener code here
            Toast.makeText(this, "Placement button clicked!", Toast.LENGTH_SHORT).show()

            }
        }
    }
