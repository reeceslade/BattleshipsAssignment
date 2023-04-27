package uk.ac.bournemouth.ap.battleships

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class OpponentActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opponent_board)
        val homeView3 = findViewById<HomeView3>(R.id.homeView33)
    }
}