package Bships

class RandomPlayer {
    fun generateRandomMove(colCount: Int, rowCount: Int): Pair<Int, Int> {
        // Generate random column and row
        val randomCol = (0 until colCount).random()
        val randomRow = (0 until rowCount).random()
        return randomCol to randomRow
    }
}