package uk.ac.bournemouth.ap.battleships

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button

class GameOverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameover)
        supportActionBar?.hide()

        val restartBtn = findViewById<Button>(R.id.restartBtn)
        restartBtn.setOnClickListener {
            val homeViewIntent = Intent(this, HomeView::class.java)
            homeViewIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK // Clear back stack and create a new task
            startActivity(homeViewIntent)
            finish()
        }
        val exitGameButton = findViewById<Button>(R.id.btn_exit)
        exitGameButton.setOnClickListener {
            finish()
        }
    }
}
