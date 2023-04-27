package uk.ac.bournemouth.ap.battleships

import android.content.Intent
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
            val intent = Intent(this, HomeViewTest::class.java)
            startActivity(intent)
        }
        val exitGameButton = findViewById<Button>(R.id.btn_exit)
        exitGameButton.setOnClickListener {
            finish() // Finish the activity, which will exit the game
        }
    }
}
