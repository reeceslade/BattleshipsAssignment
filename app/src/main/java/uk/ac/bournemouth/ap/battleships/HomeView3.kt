package uk.ac.bournemouth.ap.battleships

import Bships.StudentShip
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import uk.ac.bournemouth.ap.battleshiplib.GuessResult
import uk.ac.bournemouth.ap.lib.matrix.MutableMatrix

class HomeView3 : View {
    //recieves coordinates and makes guesses based on the ship positions
    //(sort out logic) iterate between activities and playerTurns
    //update ondraw based on hit sink miss
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    private var placementConfirmed = false
    private val ships = StudentShip.generateRandomShips(10, 10)
    private val colCount = 10
    private val rowCount = 10
    private var circleDiameter: Float = 0f
    private var circleSpacing: Float = 0f
    private var circleSpacingRatio: Float = 0.2f
    private val cells = MutableMatrix<GuessCell>(colCount, rowCount, GuessCell.UNSET)
    private val isPlayerTurn = false
    init {
        setOnClickListener {
            computerTurn()
        }
    }
    private fun computerTurn() {
        //change to shipPositions
        // Generate a random column and row
        val randomCol = (0 until colCount).random()
        val randomRow = (0 until rowCount).random()
        val guessCell = cells[randomCol, randomRow]
        val hit = guessCell is GuessCell.HIT
        val guessResult = if (hit) {
            val hitShip = ships.find { ship ->
                (randomCol in ship.left..ship.right) && (randomRow in ship.top..ship.bottom)
            }
            if (hitShip != null) GuessResult.HIT(ships.indexOf(hitShip)) else GuessResult.MISS
        } else {
            GuessResult.MISS
        }
        cells[randomCol, randomRow] = when (guessResult) {
            GuessResult.MISS -> GuessCell.MISS
            is GuessResult.HIT -> GuessCell.HIT(guessResult.ship)
            is GuessResult.SUNK -> GuessCell.SUNK(guessResult.ship)
            else -> GuessCell.UNSET
        }
        // Redraw the view
        invalidate()
    }



    private val gridPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLUE
    }
    private val noPlayerPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }
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
    }
}

