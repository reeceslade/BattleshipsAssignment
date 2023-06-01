package uk.ac.bournemouth.ap.battleships

import android.os.Parcel
import android.os.Parcelable
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.battleshiplib.overlaps
import kotlin.random.Random

class StudentShip(
    override var top: Int,
    override var left: Int,
    override var bottom: Int,
    override var right: Int
) : Ship, Parcelable {

    // Implement the writeToParcel method to write the object's properties to the parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(top)
        parcel.writeInt(left)
        parcel.writeInt(bottom)
        parcel.writeInt(right)
    }

    // Implement the describeContents method and return 0
    override fun describeContents(): Int {
        return 0
    }

    // Create a companion object that implements the Parcelable.Creator interface
    companion object CREATOR : Parcelable.Creator<StudentShip> {

        // Implement the createFromParcel method to recreate the object from the parcel
        override fun createFromParcel(parcel: Parcel): StudentShip {
            return StudentShip(
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt()
            )
        }

        // Implement the newArray method and return an array of StudentShip with the specified size
        override fun newArray(size: Int): Array<StudentShip?> {
            return arrayOfNulls(size)
        }

        fun generateRandomShips(
            columns: Int = BattleshipGrid.DEFAULT_COLUMNS,
            rows: Int = BattleshipGrid.DEFAULT_ROWS,
            sizes: IntArray = BattleshipGrid.DEFAULT_SHIP_SIZES,
            random: Random = Random
        ): List<StudentShip> {
            val ships = mutableListOf<StudentShip>()
            for (size in sizes) {
                var ship: StudentShip
                var isValidPlacement: Boolean
                do {
                    val isVertical = random.nextBoolean()
                    val left = random.nextInt(columns)
                    val top = random.nextInt(rows)
                    val bottom = if (isVertical) top + size - 1 else top
                    val right = if (isVertical) left else left + size - 1
                    ship = StudentShip(top, left, bottom, right)
                    isValidPlacement = !ships.any { it.overlaps(ship) } &&
                            ship.top >= 0 && ship.bottom < rows && ship.left >= 0 && ship.right < columns
                } while (!isValidPlacement)
                ships.add(ship)
            }
            return ships
        }
    }
}

