package uk.ac.bournemouth.ap.battleshiplib
//3 child types
//miss no extra info also same thing why its sealed class
sealed class GuessResult {
    object MISS : GuessResult() {
        override fun toString() = "MISS"
    }
    data class HIT(val shipIndex:Int) : GuessResult()
    data class SUNK(val shipIndex: Int) : GuessResult()
    }