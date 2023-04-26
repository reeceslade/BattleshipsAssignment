package uk.ac.bournemouth.ap.battleships

import FragmentTest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import uk.ac.bournemouth.ap.battleships.databinding.ActivityHomeBinding
import uk.ac.bournemouth.ap.battleships.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.playGameBtn.setOnClickListener { onPlayBtnClick() }
        }
    private fun onPlayBtnClick() {
        val testFragment = FragmentTest()

        // Begin a fragment transaction
        supportFragmentManager.beginTransaction()
            // Replace the fragment container with the TestFragment
            .replace(R.id.fragmentContainer, testFragment)
            // Add the transaction to the back stack (optional)
            .addToBackStack(null)
            // Commit the transaction
            .commit()
    }

     /*   val exitGameButton = findViewById<Button>(R.id.btn_exit)
        exitGameButton.setOnClickListener {
            finish() // Finish the activity, which will exit the game
        }*/
    }