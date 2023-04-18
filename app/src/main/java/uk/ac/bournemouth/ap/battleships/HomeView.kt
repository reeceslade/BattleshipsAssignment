package uk.ac.bournemouth.ap.battleships
import Bships.StudentBattleshipOpponent
import Bships.StudentGrid
import Bships.StudentShip
import Bships.ships
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GestureDetectorCompat
import com.google.android.material.snackbar.Snackbar
import uk.ac.bournemouth.ap.battleshiplib.*
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate
import kotlin.system.exitProcess

class HomeView: View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var game: StudentGrid = StudentGrid(StudentBattleshipOpponent(10, 10, ships))
        set(value) {
            field = value
            // After the new value is set, make sure to recalculate sizes and then trigger a redraw
            recalculateDimensions()
            invalidate()
        }

    private val colCount: Int get() = game.columns
    private val rowCount: Int get() = game.rows
    //private val shipCount: List<Ship> get() = game.opponent.ships
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
        color = Color.RED
    }
    private val xPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val diameterX = w / (colCount + (colCount + 1) * circleSpacingRatio)
        val diameterY = h / (rowCount + (rowCount + 1) * circleSpacingRatio)
        circleDiameter = minOf(diameterX, diameterY)
        circleSpacing = circleDiameter * circleSpacingRatio
        gridPaint.strokeWidth = circleSpacing
        xPaint.strokeWidth = circleSpacing / 2f
    }

    private fun recalculateDimensions(w: Int = width, h: Int = height) {}

    val gridLeft = 0f
    val gridTop = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val gridLeft = 0f
        val gridTop = 0f
        val gridRight = gridLeft + colCount * (circleDiameter + circleSpacing) + circleSpacing
        val gridBottom = gridTop + rowCount * (circleDiameter + circleSpacing) + circleSpacing
        val radius = circleDiameter / 2f
        canvas.drawRect(gridLeft, gridTop, gridRight, gridBottom, noPlayerPaint)
        for (row in 0..rowCount) {
            val y = gridTop + circleSpacing / 2 + (circleDiameter + circleSpacing) * row
            canvas.drawLine(gridLeft, y, gridRight, y, gridPaint)
        }
        for (col in 0..colCount) {
            val x = gridLeft + circleSpacing / 2 + (circleDiameter + circleSpacing) * col
            canvas.drawLine(x, gridTop, x, gridBottom, gridPaint)
        }


        //GRID
        //GRID

        //COMMENT THIS OUT IN ACTUAL GAME PLAY
        for (ship in ships) {
            val left = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * ship.left)
            val top = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * ship.top)
            val right = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * (ship.right)) + circleDiameter
            val bottom = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * (ship.bottom)) + circleDiameter
            canvas.drawRect(left, top, right, bottom, xPaint)
        }  //SHIPS
        //SHIPS

        // MISSED CELLS
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
                    is GuessCell.HIT -> {
                        val cx =
                            gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * col) + radius
                        val cy =
                            gridTop + circleSpacing + ((circleDiameter + circleSpacing) * row) + radius
                        canvas.drawCircle(cx, cy, radius, player1Paint)
                    }
                    is GuessCell.SUNK -> {
                        val cx =
                            gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * col) + radius
                        val cy =
                            gridTop + circleSpacing + ((circleDiameter + circleSpacing) * row) + radius
                        canvas.drawCircle(cx, cy, radius, player1Paint)
                        val startX = cx - radius
                        val startY = cy
                        val endX = cx + radius
                        val endY = cy
                        canvas.drawLine(startX, startY, endX, endY, xPaint)
                    }
                    else -> {}
                }
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

    // Create a list to keep track of sunk status of all ships
    val shipsSunk = MutableList(game.opponent.ships.size) { false }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val col = ((event.x - circleSpacing / 2 - gridLeft) / (circleDiameter + circleSpacing)).toInt()
            val row = ((event.y - circleSpacing / 2 - gridTop) / (circleDiameter + circleSpacing)).toInt()
            if (col in 0 until colCount && row in 0 until rowCount) {
                val foundShip: BattleshipOpponent.ShipInfo<Ship>? = game.opponent.shipAt(col, row)
                if (foundShip == null) {
                    game.cells[col, row] = GuessCell.MISS
                    Snackbar.make(this, "Ship missed", Snackbar.LENGTH_SHORT).show()
                    //if the coords are empty (dont have the ship and index) == MISS
                } else {
                    val (shipIndex, Ship) = foundShip
                    game.cells[col, row] = GuessCell.HIT(shipIndex)
                    var isSunk = true
                    Ship.forEachIndex { x, y -> isSunk = isSunk && game.cells[x, y] is GuessCell.HIT }
                    Snackbar.make(this, "Ship HIT", Snackbar.LENGTH_SHORT).show()

                    if (isSunk) {
                        val state = GuessCell.SUNK(shipIndex)
                        Ship.forEachIndex { x, y -> game.cells[x, y] = state }
                        shipsSunk[shipIndex] = true
                        GuessResult.SUNK(shipIndex)
                        Snackbar.make(this, "Ship sunkk", Snackbar.LENGTH_SHORT).show()

                        if (shipsSunk.all { it }) {
                            showGameOverScreen()
                            exitProcess(0)
                        }

                    } else {
                        GuessResult.HIT(shipIndex)

                        Snackbar.make(this, "Ship hitt", Snackbar.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Display a message indicating that the guess missed
                Snackbar.make(this, "Ship misseddd", Snackbar.LENGTH_SHORT).show()
            }
            invalidate()
            return true
        }
        return false
    }
    private fun showGameOverScreen() {
        // Create an Intent to start the GameOverActivity
        val intent = Intent(this, GameOverActivity::class.java)
        // Add any extra data to the Intent if needed
        // For example, you can pass the game score or other relevant information
        // using intent.putExtra() method

        // Start the GameOverActivity
        startActivity(intent)

        // Finish the current activity if needed
        finish()
    }

}