package org.example.student.battleshipgame

import Bships.StudentGrid
import Bships.StudentBattleshipOpponent
import Bships.StudentShip
import Bships.ships
import uk.ac.bournemouth.ap.battleshiplib.*
import uk.ac.bournemouth.ap.battleshiplib.test.BattleshipTest
import uk.ac.bournemouth.ap.lib.matrix.boolean.BooleanMatrix
import kotlin.random.Random
//create own test
class StudentBattleshipTest : BattleshipTest<StudentShip>() {
    override fun createOpponent(
        columns: Int,
        rows: Int,
        ships: List<StudentShip>
    ): StudentBattleshipOpponent {
        return StudentBattleshipOpponent(columns, rows, ships)
    }

    override fun transformShip(sourceShip: Ship): StudentShip {
        return StudentShip(sourceShip.top, sourceShip.left, sourceShip.bottom, sourceShip.right)
    }

    override fun createOpponent(
        columns: Int,
        rows: Int,
        shipSizes: IntArray,
        random: Random
    ): StudentBattleshipOpponent {
        val ships = mutableListOf<StudentShip>()
        for (size in shipSizes) {
            var ship: StudentShip
            var r: Int
            do {
                r = random.nextInt(0, columns) // generate a random column index
                val top = random.nextInt(rows - size + 1)
                val left = r
                val bottom = top + size - 1
                val right = left
                ship = StudentShip(top, left, bottom, right)
            } while (ships.any { it.overlaps(ship) } || ship.bottom >= rows || ship.top < 0 || ship.left < 0 || ship.right >= columns)
            ships.add(ship)
        }
        return StudentBattleshipOpponent(columns, rows, ships)
    }






    override fun createGrid(
        grid: BooleanMatrix,
        opponent: BattleshipOpponent
    ): StudentGrid {
        // If the opponent is not a StudentBattleshipOpponent, create it based upon the passed in data
        val studentOpponent =
            opponent as? StudentBattleshipOpponent
                ?: createOpponent(opponent.columns, opponent.rows, opponent.ships.map { it as? StudentShip ?: transformShip(it) })

        return StudentGrid(studentOpponent)
    }
}

