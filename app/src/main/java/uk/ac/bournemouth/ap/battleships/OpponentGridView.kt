package uk.ac.bournemouth.ap.battleships
import Bships.StudentBattleshipOpponent
import Bships.StudentGrid
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.google.android.material.snackbar.Snackbar
import uk.ac.bournemouth.ap.battleshiplib.*

class OpponentGridView: View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var game: StudentGrid = StudentGrid(StudentBattleshipOpponent(BattleshipGrid.DEFAULT_COLUMNS, BattleshipGrid.DEFAULT_ROWS, StudentShip.generateRandomShips(BattleshipGrid.DEFAULT_COLUMNS, BattleshipGrid.DEFAULT_ROWS)))
        set(value) {
            field = value
            field = value
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
        color = Color.RED
    }
    private val whitePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }
    private val redPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.RED
    }
    private val shipPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        recalculateDimensions(w, h)
    }

    private fun recalculateDimensions(w: Int = width, h: Int = height) {
        val diameterX = w / (colCount + (colCount + 1) * circleSpacingRatio)
        val diameterY = h / (rowCount + (rowCount + 1) * circleSpacingRatio)
        circleDiameter = minOf(diameterX, diameterY)
        circleSpacing = circleDiameter * circleSpacingRatio
        gridPaint.strokeWidth = circleSpacing
        shipPaint.strokeWidth = circleSpacing / 2f
    }

    private val gridLeft = 0f
    private val gridTop = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val gridLeft = 0f
        val gridTop = 0f
        val gridRight = gridLeft + colCount * (circleDiameter + circleSpacing) + circleSpacing
        val gridBottom = gridTop + rowCount * (circleDiameter + circleSpacing) + circleSpacing
        val radius = circleDiameter / 2f
        drawGrid(canvas, gridLeft, gridTop, gridRight, gridBottom)
        //GRID
        //COMMENT THIS OUT IN ACTUAL GAME PLAY
       /*for (ship in ships) {
            val left = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * ship.left)
            val top = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * ship.top)
            val right = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * (ship.right)) + circleDiameter
            val bottom = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * (ship.bottom)) + circleDiameter
            canvas.drawRect(left, top, right, bottom, xPaint)
        } */
        //SHIPS
        //SHIPS

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
                        canvas.drawLine(startX, startY, endX, endY, shipPaint)
                        canvas.drawLine(startX, endY, endX, startY, shipPaint)
                    }
                    is GuessCell.HIT -> {
                        val cx = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * col) + radius
                        val centerY = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * row) + radius
                        canvas.drawCircle(cx, centerY, radius, redPaint)
                    }
                    is GuessCell.SUNK -> {
                        val cx = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * col) + radius
                        val centerY = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * row) + radius
                        canvas.drawCircle(cx, centerY, radius, redPaint)
                        val startX = cx - radius
                        val endX = cx + radius
                        canvas.drawLine(startX, cy, endX, cy, shipPaint)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun drawGrid(
        canvas: Canvas,
        gridLeft: Float,
        gridTop: Float,
        gridRight: Float,
        gridBottom: Float
    ) {
        canvas.drawRect(gridLeft, gridTop, gridRight, gridBottom, whitePaint)
        for (row in 0..rowCount) {
            val y = gridTop + circleSpacing / 2 + (circleDiameter + circleSpacing) * row
            canvas.drawLine(gridLeft, y, gridRight, y, gridPaint)
        }
        for (col in 0..colCount) {
            val x = gridLeft + circleSpacing / 2 + (circleDiameter + circleSpacing) * col
            canvas.drawLine(x, gridTop, x, gridBottom, gridPaint)
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

                val snackbar = Snackbar.make(this@OpponentGridView, message, duration)
                snackbar.show()
            }
            return isWithinBounds
        }

        private fun handleGuessResult(guessResult: GuessResult) {
            when (guessResult) {
                is GuessResult.HIT -> {
                    val message = "HIT!"
                    val duration = Snackbar.LENGTH_SHORT
                    val snackbar = Snackbar.make(this@OpponentGridView, message, duration)
                    snackbar.show()
                }
                is GuessResult.SUNK -> {
                    val message = "SUNK!"
                    val duration = Snackbar.LENGTH_SHORT
                    val snackbar = Snackbar.make(this@OpponentGridView, message, duration)
                    snackbar.show()
                }
                is GuessResult.MISS -> {
                    val message = "Miss"
                    val duration = Snackbar.LENGTH_SHORT
                    val snackbar = Snackbar.make(this@OpponentGridView, message, duration)
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
    /*  private fun switchComputerTurn() {
          Handler().postDelayed({
              val homeView = findViewById<HomeView2>(R.id.homeView22)
              homeView.handleComputerTurn()
              playerTurn = 0
              Snackbar.make(this, "Your turn", Snackbar.LENGTH_SHORT).show()
          }, 500)
          invalidate()
      } */
    private val shipsSunk = MutableList(game.opponent.ships.size) { false }
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val col = ((event.x - circleSpacing / 2 - gridLeft) / (circleDiameter + circleSpacing)).toInt()
            val row = ((event.y - circleSpacing / 2 - gridTop) / (circleDiameter + circleSpacing)).toInt()
            if (col in 0 until colCount && row in 0 until rowCount) {
                opponentGridListener?.onCellSelected(col, row)
                val foundShip: BattleshipOpponent.ShipInfo<Ship>? = game.opponent.shipAt(col, row)

                if (foundShip == null) {
                    opponentGridListener?.onMiss() // Notify the listener about the empty space
                    if (game.cells[col, row] !is GuessCell.HIT) {
                        if (game.cells[col, row] !is GuessCell.MISS) {
                            game.cells[col, row] = GuessCell.MISS
                            opponentGridListener?.onMiss()
                        } else {
                            opponentGridListener?.onAlreadyMiss()
                        }
                    } else {
                        opponentGridListener?.onAlreadyHit()
                    }
                } else {
                    val (shipIndex, Ship) = foundShip
                    opponentGridListener?.onShipTouched(shipIndex)

                    if (game.cells[col, row] !is GuessCell.SUNK && game.cells[col, row] !is GuessCell.HIT) {
                        game.cells[col, row] = GuessCell.HIT(shipIndex)
                        var isSunk = true
                        Ship.forEachIndex { x, y ->
                            isSunk = isSunk && game.cells[x, y] is GuessCell.HIT
                        }
                        if (isSunk) {
                            val state = GuessCell.SUNK(shipIndex)
                            Ship.forEachIndex { x, y -> game.cells[x, y] = state }
                            shipsSunk[shipIndex] = true
                            opponentGridListener?.onShipSunk(shipIndex) // Notify the listener about the sunk ship

                            if (shipsSunk.all { it }) {
                                opponentGridListener?.onShipAlreadySunk()
                            }
                        } else {
                            opponentGridListener?.onShipHit()
                        }
                    } else if (game.cells[col, row] is GuessCell.HIT) {
                        opponentGridListener?.onAlreadyHit()
                    } else {
                        opponentGridListener?.onShipAlreadySunk()
                    }
                }
            } else {
                opponentGridListener?.onMiss() // Notify the listener about the empty space
            }

            invalidate()
            return true
        }
        return false
    }



    interface OpponentGridListener {
        fun onCellSelected(column: Int, row: Int)
        fun onShipSunk(shipIndex: Int)
        fun onShipTouched(shipIndex: Int)
        fun onAlreadyMiss()
        fun onAlreadyHit()
        fun onShipHit()
        fun onShipAlreadySunk()
        fun onMiss()
    }

    fun randomShoot() {
        // Generate random column and row indices within the grid size
        val randomColumn = (0 until colCount).random()
        val randomRow = (0 until rowCount).random()

        // Call the `onTouchEvent` function with the generated indices to simulate a tap on the grid
        onTouchEvent(
            MotionEvent.obtain(
                System.currentTimeMillis(), System.currentTimeMillis(),
                MotionEvent.ACTION_DOWN, gridLeft + circleSpacing + (randomColumn * (circleDiameter + circleSpacing)),
                gridTop + circleSpacing + (randomRow * (circleDiameter + circleSpacing)), 0
            )
        )
    }


    private var opponentGridListener: OpponentGridListener? = null

    fun setOpponentGridListener(listener: OpponentGridListener) {
        opponentGridListener = listener
    }

    private fun showGameOverScreen() {
        val gameOverIntent = Intent(context, GameOverActivity::class.java)
     context.startActivity(gameOverIntent)
    }

}