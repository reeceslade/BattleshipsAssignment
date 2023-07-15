package uk.ac.bournemouth.ap.battleships

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class GamePlayActivity : AppCompatActivity(), OpponentGridView.OpponentGridListener {
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

        val opponentGrid = findViewById<OpponentGridView>(R.id.opponentGrid)
        opponentGrid.setOpponentGridListener(this)
    }

    override fun onCellSelected(column: Int, row: Int) {
        val message = "Cell selected: Column $column, Row $row"
        val duration = Snackbar.LENGTH_SHORT
        val snackbar = Snackbar.make(findViewById(R.id.opponentGrid), message, duration)
        snackbar.show()
    }

}

