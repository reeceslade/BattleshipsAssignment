package uk.ac.bournemouth.ap.battleships

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class PlayerPlacementActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placement_grid)
        val placementBtn = findViewById<Button>(R.id.placementBtn)
        placementBtn.setOnClickListener {
            val snackbar = Snackbar.make(it, "Your message here", Snackbar.LENGTH_SHORT)
            snackbar.show()
            val intent = Intent(this, GamePlayActivity::class.java)
            intent.putExtra("placementConfirmed", true)
            startActivity(intent)
        }
    }
}