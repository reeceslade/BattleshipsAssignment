    package com.example.s5306951
    //main activity loads layout
    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.view.View
    import android.widget.Button
    import android.widget.EditText


    const val EXTRA_MESSAGE = "MYMESSAGE"
    class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            val exitBtn = findViewById<Button>(R.id.exitBtn)
            exitBtn.setOnClickListener {
                finish()
            }

            // JUST WANT TO DISPLAY NEW PAGE AS CELL BACKGROUND
            // Get a reference to the play button
            val playBtn = findViewById<Button>(R.id.playBtn)

            // Set a click listener on the play button
            playBtn.setOnClickListener {
                // Create an Intent to start the PlayActivity


                val intent = Intent(this, DisplayMessageActivity::class.java)

                // Start the PlayActivity
                startActivity(intent)
            }
        }


        /** Called when the user taps the Send button */
        fun sendMessage(view: View) {
            // Do something in response to button
            val editText = findViewById<EditText>(R.id.editText)
            val message = editText.text.toString()
            val intent = Intent(this, Play::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)   
        }
    }