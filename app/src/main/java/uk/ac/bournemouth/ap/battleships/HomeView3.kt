package uk.ac.bournemouth.ap.battleships
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uk.ac.bournemouth.ap.battleshiplib.Ship

class HomeView3 : AppCompatActivity(), FragmentTest.OnShipPositionsReadyListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set up your HomeView3 UI and functionality

        // Retrieve the shipPositions data from the intent extras
        val shipPositions = intent.getSerializableExtra("shipPositions") as HashMap<Ship, Pair<Int, Int>>?
        if (shipPositions != null) {
            // Perform any action with the shipPositions data
        }
    }

    override fun onShipPositionsReady(shipPositions: HashMap<Ship, Pair<Int, Int>>) {
        // Receive the shipPositions data here and perform desired action
    }
}
