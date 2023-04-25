package uk.ac.bournemouth.ap.battleships

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        val playGameButton = findViewById<Button>(R.id.playGameBtn)
        playGameButton.setOnClickListener {
          //  val shipPositions = listOf<Pair<Int, Int>>(/* Add your ship positions here */)
            val homeView2 = HomeView2(this)
            setContentView(homeView2)
        }

        val exitGameButton = findViewById<Button>(R.id.btn_exit)
        exitGameButton.setOnClickListener {
            finish() // Finish the activity, which will exit the game
        }
    }
}