import Bships.StudentShip
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.battleships.HomeView3
import uk.ac.bournemouth.ap.battleships.R
import uk.ac.bournemouth.ap.battleships.databinding.FragmentTestBinding

class FragmentTest : Fragment(R.layout.fragment_test) {

    private lateinit var binding: FragmentTestBinding
    private val shipPositions = HashMap<Ship, Pair<Int, Int>>()
    private val ships = StudentShip.generateRandomShips(10, 10)

    // Define an interface to pass shipPositions data to HomeView3
    interface OnShipPositionsReadyListener {
        fun onShipPositionsReady(shipPositions: HashMap<Ship, Pair<Int, Int>>)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTestBinding.bind(view)
        binding.placementBtn.setOnClickListener {

        }

        binding.placementBtn.setOnClickListener {
            for (ship in ships) {
                val position = Pair(ship.left, ship.top)
                shipPositions[ship] = position
            }
            processShipPositions(shipPositions)

            // Call the callback method to pass the shipPositions data to HomeView3
            if (activity is OnShipPositionsReadyListener) {
                // Cast the activity to OnShipPositionsReadyListener
                val listener = activity as OnShipPositionsReadyListener
                listener.onShipPositionsReady(shipPositions)

                // Create an Intent to start HomeView3 activity
                val intent = Intent(activity, HomeView3::class.java)
                // Pass the shipPositions data as an extra
                intent.putExtra("shipPositions", shipPositions)
                startActivity(intent)
            }
        }
    }

    // A helper method to process the shipPositions data
    private fun processShipPositions(shipPositions: HashMap<Ship, Pair<Int, Int>>) {
        // Perform any action with the shipPositions data
    }
}
