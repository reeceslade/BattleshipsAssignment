package uk.ac.bournemouth.ap.battleshiplib

import uk.ac.bournemouth.ap.lib.matrix.int.IntMatrix
import uk.ac.bournemouth.ap.lib.matrix.Matrix
import uk.ac.bournemouth.ap.lib.matrix.MutableMatrix
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate

//assumes shapes are rectangular
//dont have to use these
interface Ship {
    var top: Int
    var left: Int
    var bottom: Int
    var right: Int

    val columnIndices: IntRange get() = left..right
    val rowIndices: IntRange get() = top..bottom

    val width: Int get() = right - left + 1
    val height: Int get() = bottom - top + 1
    val size: Int get() = width*height
    val topLeft: Coordinate get() = Coordinate(top, left)
    val bottomRight: Coordinate get() = Coordinate(bottom, right)
}
//gets index for all positioned ships
//swapped y for x
inline fun Ship.forEachIndex(action: (Int, Int) -> Unit) {
    for (x in columnIndices) {
    for (y in rowIndices) {
            action(x,y)
        }
    }
}
fun Ship.overlaps(other: Ship): Boolean {
    val leftOverlap = (left..right).intersect(other.left..other.right).count()
    val topOverlap = (top..bottom).intersect(other.top..other.bottom).count()
    return leftOverlap > 0 && topOverlap > 0
}

inline fun Ship.mapIndices(action: (Int, Int) -> Int): IntMatrix {
    return IntMatrix(width, height) { mx, my ->
        action(mx+left, my+top)
    }
}

inline fun <T> Ship.mapIndices(action: (Int, Int) -> T): Matrix<T> {
    return MutableMatrix(width, height) { mx, my ->
        action(mx+left, my+top)
    }
}
