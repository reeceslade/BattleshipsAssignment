package uk.ac.bournemouth.ap.battleships

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val playGameButton = findViewById<Button>(R.id.playGameBtn)
        playGameButton.setOnClickListener {
            val homeView = HomeView(this)
            setContentView(homeView)
        }
        val exitGameButton = findViewById<Button>(R.id.btn_exit)
        exitGameButton.setOnClickListener {
            finish() // Finish the activity, which will exit the game
        }
    }
}