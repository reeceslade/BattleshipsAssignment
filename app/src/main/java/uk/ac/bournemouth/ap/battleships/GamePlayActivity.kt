package uk.ac.bournemouth.ap.battleships

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import uk.ac.bournemouth.ap.battleshiplib.GuessResult

class GamePlayActivity : AppCompatActivity(), OpponentGridView.OpponentGridListener {
    // ... Other code ...

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

        val opponentGrid = findViewById<OpponentGridView>(R.id.opponentGrid)
        opponentGrid.setOpponentGridListener(this) // Set the OpponentGridListener here
    }

    override fun onCellSelected(column: Int, row: Int) {
        // Implement your logic here when a cell is selected in the OpponentGridView
    }


    override fun onMiss() {
        Snackbar.make(findViewById(R.id.opponentGrid), "SHIP MISSED", Snackbar.LENGTH_SHORT).show()
    }

    override fun onAlreadyMiss() {
        Snackbar.make(findViewById(R.id.opponentGrid), "ALREADY MISSED THIS SHIP DONE CLICK TWICE!", Snackbar.LENGTH_SHORT).show()
    }

    override fun onAlreadyHit() {
        Snackbar.make(findViewById(R.id.opponentGrid), "ALREADY HIT THIS SHIP DONT CLICK TWICE!", Snackbar.LENGTH_SHORT).show()
    }
    override fun onShipHit() {
        Snackbar.make(findViewById(R.id.opponentGrid), "SHIP AHOY", Snackbar.LENGTH_SHORT).show()
    }

    override fun onShipAlreadySunk() {
        Snackbar.make(findViewById(R.id.opponentGrid), "SHIP ALREADY SUNK DONT CLICK TWICE!", Snackbar.LENGTH_SHORT).show()
    }

    override fun onShipTouched(shipIndex: Int) {
        // Implement your logic here when a ship is touched in the OpponentGridView
    }

    override fun onShipSunk(shipIndex: Int) {
        Snackbar.make(findViewById(R.id.opponentGrid), "YOUVE SUNK A SHIP!", Snackbar.LENGTH_SHORT).show()
    }

}
