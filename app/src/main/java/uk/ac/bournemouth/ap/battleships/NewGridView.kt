package uk.ac.bournemouth.ap.battleships

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import kotlin.math.min

class NewGridView : View {
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

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    private var shipPositions = listOf<StudentShip>()
    private var selectedShipIndex: Int = 0 // Initialize with an invalid value
    private val clickedShipPositions: MutableList<Pair<Int, Int>> = mutableListOf()

    private var guessCells: Array<Array<GuessCell?>> =
        Array(BattleshipGrid.DEFAULT_COLUMNS) { arrayOfNulls(BattleshipGrid.DEFAULT_ROWS) }

    private fun onShipClicked() {
        // Display the Snackbar "HIT" message when the ship is clicked
        val message = "HIT!"
        val duration = Snackbar.LENGTH_SHORT
        val snackbar = Snackbar.make(this, message, duration)
        snackbar.show()
        // Optionally, you can add any other logic you want to perform when a ship is clicked
    }


    fun setShipPositions(positions: List<StudentShip>) {
        shipPositions = positions
        invalidate() // Redraw the view with new ship positions
    }

    private fun setGuessCells(cells: Array<Array<GuessCell?>>) {
        guessCells = cells
        invalidate() // Redraw the view with the new guess cells
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        recalculateDimensions(w, h)
    }


    private fun recalculateDimensions(w: Int = width, h: Int = height) {
        val diameterX =
            w / (BattleshipGrid.DEFAULT_COLUMNS + (BattleshipGrid.DEFAULT_COLUMNS + 1) * circleSpacingRatio)
        val diameterY =
            h / (BattleshipGrid.DEFAULT_ROWS + (BattleshipGrid.DEFAULT_ROWS + 1) * circleSpacingRatio)
        circleDiameter = min(diameterX, diameterY)
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
        val gridRight =
            gridLeft + BattleshipGrid.DEFAULT_COLUMNS * (circleDiameter + circleSpacing) + circleSpacing
        val gridBottom =
            gridTop + BattleshipGrid.DEFAULT_ROWS * (circleDiameter + circleSpacing) + circleSpacing
        val radius = circleDiameter / 2f
        drawGrid(canvas, gridLeft, gridTop, gridRight, gridBottom)

        // Draw ships if needed
        for (ship in shipPositions) {
            val left = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * ship.left)
            val top = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * ship.top)
            val right =
                gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * (ship.right)) + circleDiameter
            val bottom =
                gridTop + circleSpacing + ((circleDiameter + circleSpacing) * (ship.bottom)) + circleDiameter
            canvas.drawRect(left, top, right, bottom, shipPaint)
        }
        for (clickedPosition in clickedShipPositions) {
            val (clickedRow, clickedCol) = clickedPosition
            val cx = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * (clickedCol)) + circleDiameter / 2f
            val cy = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * (clickedRow)) + circleDiameter / 2f
            canvas.drawCircle(cx, cy, radius, redPaint)
        }
        // Iterate over guessCells to draw hit, miss, and sunk positions
        for (x in 0 until BattleshipGrid.DEFAULT_COLUMNS) {
            val cx = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * x) + radius
            for (y in 0 until BattleshipGrid.DEFAULT_ROWS) {
                val cy = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * y) + radius
                when (guessCells[x][y]) {
                    is GuessCell.HIT -> {
                        canvas.drawCircle(cx, cy, radius, redPaint)
                    }
                    is GuessCell.MISS -> {
                        val startX = cx - radius
                        val startY = cy - radius
                        val endX = cx + radius
                        val endY = cy + radius
                        canvas.drawLine(startX, startY, endX, endY, shipPaint)
                        canvas.drawLine(startX, endY, endX, startY, shipPaint)
                    }
                    is GuessCell.SUNK -> {
                        val startX = cx - radius
                        val endX = cx + radius
                        canvas.drawLine(startX, cy, endX, cy, shipPaint)
                    }
                    else -> {} // Do nothing for empty cells
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
        for (row in 0..BattleshipGrid.DEFAULT_ROWS) {
            val y = gridTop + circleSpacing / 2 + (circleDiameter + circleSpacing) * row
            canvas.drawLine(gridLeft, y, gridRight, y, gridPaint)
        }
        for (col in 0..BattleshipGrid.DEFAULT_COLUMNS) {
            val x = gridLeft + circleSpacing / 2 + (circleDiameter + circleSpacing) * col
            canvas.drawLine(x, gridTop, x, gridBottom, gridPaint)
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                val y = event.y
                val col = ((x - circleSpacing / 2 - gridLeft) / (circleDiameter + circleSpacing)).toInt()
                val row = ((y - circleSpacing / 2 - gridTop) / (circleDiameter + circleSpacing)).toInt()
                if (col in 0 until BattleshipGrid.DEFAULT_COLUMNS && row in 0 until BattleshipGrid.DEFAULT_ROWS) {
                    // Check if the clicked cell is part of a ship
                    val ship = shipPositions.find { it.left <= col && col <= it.right && it.top <= row && row <= it.bottom }
                    if (ship != null) {
                        val clickedPosition = Pair(row, col)
                        if (!clickedShipPositions.contains(clickedPosition)) {
                            clickedShipPositions.add(clickedPosition)
                            val message = "HIT ship at row ${row}, column ${col}!"
                            Log.d("NewGridView", "HIT ship at row $row, column $col!")
                            val duration = Snackbar.LENGTH_SHORT
                            val snackbar = Snackbar.make(this, message, duration)
                            snackbar.show()
                            invalidate()
                        }
                        return true
                    }

                    val guessCell = guessCells[col][row]
                    if (guessCell == null) {
                        // Handle empty cell (MISS)
                        // Update the guessCells and redraw the view
                        guessCells[col][row] = GuessCell.MISS
                        setGuessCells(guessCells)
                        // You can call a listener method or perform other actions for MISS here
                        val message = "MISS!"
                        val duration = Snackbar.LENGTH_SHORT
                        val snackbar = Snackbar.make(this, message, duration)
                        snackbar.show()
                    } else {
                        when (guessCell) {
                            is GuessCell.HIT -> {
                                // Handle already HIT
                                // You can call a listener method or perform other actions for HIT here
                                val message = "I HIT YOUR SHIP!"
                                val duration = Snackbar.LENGTH_SHORT
                                val snackbar = Snackbar.make(this, message, duration)
                                snackbar.show()
                            }
                            is GuessCell.SUNK -> {
                                // Handle already SUNK
                                // You can call a listener method or perform other actions for SUNK here
                            }
                            else -> {}
                        }
                    }
                    return true // Consume the touch event
                }
            }
        }
        return super.onTouchEvent(event)
    }

}
