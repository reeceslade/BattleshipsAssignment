package uk.ac.bournemouth.ap.battleships

//5A!!!

import Bships.StudentBattleshipOpponent
import Bships.StudentGrid
import Bships.StudentShip
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.google.android.material.snackbar.Snackbar
import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import uk.ac.bournemouth.ap.battleshiplib.GuessResult

class HomeView: View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var game: StudentGrid = StudentGrid(StudentBattleshipOpponent(10,10, emptyList()))
        set(value) {
            field = value
            // After the new value is set, make sure to recalculate sizes and then trigger a redraw
            recalculateDimensions()
            invalidate()
        }

    var shipList: List<StudentShip> = emptyList()
        set(value) {
            field = value
            // ship placement
            recalculateDimensions()
            invalidate()
        }


    private val colCount: Int get() = game.columns
        private val rowCount: Int get() = game.rows
        private var circleDiameter: Float = 0f
        private var circleSpacing: Float = 0f
        private var circleSpacingRatio: Float = 0.2f

        private val gridPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = Color.BLUE
        }
       private val noPlayerPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = Color.WHITE
        }
        private val player1Paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = Color.YELLOW
        }
        private val xPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 8f //
            color = Color.BLACK //
        }
        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            super.onSizeChanged(w, h, oldw, oldh)
            val diameterX = w / (colCount + (colCount + 1) * circleSpacingRatio)
            val diameterY = h / (rowCount + (rowCount + 1) * circleSpacingRatio)
            circleDiameter = minOf(diameterX, diameterY)
            circleSpacing = circleDiameter * circleSpacingRatio
            gridPaint.strokeWidth = circleSpacing
            xPaint.strokeWidth = circleSpacing/2f
        }

        private fun recalculateDimensions(w: Int = width, h: Int = height) {}

        private fun getCoordinate(ship: StudentShip, index: Int): Pair<Int, Int> {
        val row = ship.top + index / ship.size
        val col = ship.left + index % ship.size
        return Pair(col, row)
    }

    data class Ship(
        val size: Int,
        var isDestroyed: Boolean = false,
        var coordinates: List<Pair<Int, Int>> = emptyList()
    )

    private fun createShips(): List<Ship> {
        return StudentShip.values().map { ship ->
            val size = ship.size
            val coordinates = mutableListOf<Pair<Int, Int>>()
            for (i in 0 until size) {
                when {
                    ship.top == ship.bottom -> {
                        coordinates.add(Pair(ship.left + i, ship.top))
                    }
                    ship.left == ship.right -> {
                        coordinates.add(Pair(ship.left, ship.top + i))
                    }
                }
            }
            Ship(size, coordinates = coordinates)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val gridLeft: Float = 0f
        val gridTop: Float = 0f
        val gridRight: Float =
            gridLeft + colCount * (circleDiameter + circleSpacing) + circleSpacing
        val gridBottom: Float =
            gridTop + rowCount * (circleDiameter + circleSpacing) + circleSpacing
        val radius = circleDiameter / 2f
        canvas.drawRect(gridLeft, gridTop, gridRight, gridBottom, noPlayerPaint)

        for (col in 0..colCount) {
            val x = gridLeft + circleSpacing / 2 + (circleDiameter + circleSpacing) * col
            canvas.drawLine(x, gridTop, x, gridBottom, gridPaint)
        }
        for (row in 0..rowCount) {
            val y = gridTop + circleSpacing / 2 + (circleDiameter + circleSpacing) * row
            canvas.drawLine(gridLeft, y, gridRight, y, gridPaint)
        }
        //draw grid
        for (row in 0 until rowCount) {
            val cy = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * row) + radius
            for (col in 0 until colCount) {
                when (game[col, row]) {
                    GuessCell.MISS -> {
                        val startX =
                            gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * col)
                        val startY = cy - radius
                        val endX = startX + circleDiameter
                        val endY = cy + radius
                        canvas.drawLine(startX, startY, endX, endY, xPaint)
                        canvas.drawLine(startX, endY, endX, startY, xPaint)
                    }
                    // GuessCell.HIT -> player2Paint
                    else -> {
                    }
                }
            }
        }
        // Add a coordinates property to the Ship class

        val shipList = createShips()

        // Draw ships
        for (ship in shipList) {
            for ((col, row) in ship.coordinates) {
                val cx =
                    gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * col) + radius
                val cy =
                    gridTop + circleSpacing + ((circleDiameter + circleSpacing) * row) + radius
                canvas.drawCircle(cx, cy, radius, xPaint)
            }
        }
    }
        private val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            val x = e.x
            val y = e.y
            val isWithinBounds = x >= paddingLeft &&
                    x < width - paddingRight &&
                    y >= paddingTop &&
                    y < height - paddingBottom
            if (!isWithinBounds) {
                val message = "Invalid move!"
                val duration = Snackbar.LENGTH_SHORT
                val snackbar = Snackbar.make(this@HomeView, message, duration)
                snackbar.show()
            }
            return isWithinBounds
        }

        private fun handleGuessResult(guessResult: GuessResult) {
            when (guessResult) {
                is GuessResult.HIT -> {
                    val message = "HIT!"
                    val duration = Snackbar.LENGTH_SHORT
                    val snackbar = Snackbar.make(this@HomeView, message, duration)
                    snackbar.show()
                }
                is GuessResult.SUNK -> {
                    val message = "SUNK!"
                    val duration = Snackbar.LENGTH_SHORT
                    val snackbar = Snackbar.make(this@HomeView, message, duration)
                    snackbar.show()
                }
                is GuessResult.MISS -> {
                    val message = "Miss"
                    val duration = Snackbar.LENGTH_SHORT
                    val snackbar = Snackbar.make(this@HomeView, message, duration)
                    snackbar.show()
                }
            }
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val columnTouched = ((e.x - circleSpacing * 0.5f) / (circleSpacing + circleDiameter)).toInt()
            val rowTouched = ((e.y - circleSpacing * 0.5f) / (circleSpacing + circleDiameter)).toInt()
            if (columnTouched in 0 until game.columns && rowTouched in 0 until game.rows) {
                val guessCell = game[columnTouched, rowTouched]
                val hit = guessCell is GuessCell.HIT

                val guessResult = if (hit) {
                    val hitShip = game.opponent.ships.find { ship ->
                        (columnTouched in ship.left..ship.right) && (rowTouched in ship.top..ship.bottom)
                    }
                    if (hitShip != null) GuessResult.HIT(game.opponent.ships.indexOf(hitShip)) else GuessResult.MISS
                } else {
                    GuessResult.MISS
                }
                game.shootAt(columnTouched, rowTouched)
                invalidate()
                handleGuessResult(guessResult)
                return true
            }
            return false
        }
    })

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }
}