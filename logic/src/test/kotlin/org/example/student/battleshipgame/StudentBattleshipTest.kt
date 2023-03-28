package org.example.student.battleshipgame

import Bships.StudentGrid
import Bships.StudentBattleshipOpponent
import Bships.StudentShip
import uk.ac.bournemouth.ap.battleshiplib.*
import uk.ac.bournemouth.ap.battleshiplib.test.BattleshipTest
import uk.ac.bournemouth.ap.lib.matrix.boolean.BooleanMatrix
import kotlin.random.Random
//create own test
class StudentBattleshipTest : BattleshipTest<StudentShip>() {
    override fun createOpponent(
        columns: Int,
        rows: Int,
        ships: List<StudentShip>//opponent ship?
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
        random: Random//use this parameter Object Random repeated
    ): StudentBattleshipOpponent {
        // Note that the passing of random allows for repeatable testing

        return TODO("Create an instance of StudentBattleshipOpponent for the given game size, " +
                "target ship sizes and random generator")
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

