package uk.ac.bournemouth.ap.battleships

import Bships.StudentBattleshipOpponent
import Bships.StudentGrid
import Bships.StudentShip
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class HomeView3 : View {
    private lateinit var game: StudentGrid
    private val ships get() = game.opponent.ships
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
        color = Color.RED
    }
    private val xPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }

    constructor(context: Context?, shipPositions: List<Pair<Int, Int>>) : super(context) {
        val ships = shipPositions.map { (row, col) ->
            StudentShip(col, row, col, row)
        }
        game = StudentGrid(StudentBattleshipOpponent(10, 10, ships))
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        recalculateDimensions(w, h)
    }

    private fun recalculateDimensions(w: Int, h: Int) {
        val diameterX = w / (colCount + (colCount + 1) * circleSpacingRatio)
        val diameterY = h / (rowCount + (rowCount + 1) * circleSpacingRatio)
        circleDiameter = minOf(diameterX, diameterY)
        circleSpacing = circleDiameter * circleSpacingRatio
        gridPaint.strokeWidth = circleSpacing
        xPaint.strokeWidth = circleSpacing / 2f
    }
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
        for (ship in ships) {
            val left = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * ship.left)
            val top = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * ship.top)
            val right = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * (ship.right)) + circleDiameter
            val bottom = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * (ship.bottom)) + circleDiameter
            canvas.drawRect(left, top, right, bottom, xPaint)
        }
        }
    }