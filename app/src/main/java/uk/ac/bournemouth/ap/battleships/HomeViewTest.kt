package uk.ac.bournemouth.ap.battleships

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import uk.ac.bournemouth.ap.battleshiplib.Ship

class HomeViewTest : Activity() {
// doesnt start homeview3 instead goes back to homepage
    // want it so it when we click btn
    //passes shipPositions to HomeView3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homeview2)
        val placementButton = findViewById<Button>(R.id.placementBtn)
        placementButton.setOnClickListener {
            var shipPositionsData: HashMap<Ship, Pair<Int, Int>>
            val intent = Intent(this, HomeView3::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Placement button clicked!", Toast.LENGTH_SHORT).show()

            }
        }
    }
