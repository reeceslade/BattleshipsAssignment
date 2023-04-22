package uk.ac.bournemouth.ap.battleships

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
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import uk.ac.bournemouth.ap.battleshiplib.Ship
import kotlin.random.Random

class HomeView2 : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val ships = StudentShip.generateRandomShips(10, 10)

    private val colCount = 10
    private val rowCount = 10
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


        //draw ships and grids
        for (ship in ships) {
            val left = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * ship.left)
            val top = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * ship.top)
            val right =
                gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * (ship.right)) + circleDiameter
            val bottom =
                gridTop + circleSpacing + ((circleDiameter + circleSpacing) * (ship.bottom)) + circleDiameter
            canvas.drawRect(left, top, right, bottom, xPaint)
            invalidate()
        }

        // MISSED CELLS
    }
    private val gridLeft = 0f
    private val gridTop = 0f
    // Inside the onTouchEvent() method
    val shipPositions = HashMap<Ship, Pair<Int, Int>>()
    private var selectedShip: Ship? = null
    private var offsetY: Float = 0f
    private var offsetX: Float = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        println(shipPositions)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val touchX = event.x
                val touchY = event.y

                // Check if the touch coordinates are within a ship's bounding box
                for (ship in ships) {
                    val left = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * ship.left)
                    val top = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * ship.top)
                    val right = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * (ship.right)) + circleDiameter
                    val bottom = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * (ship.bottom)) + circleDiameter
                    if (touchX in left..right && touchY >= top && touchY <= bottom) {
                        selectedShip = ship
                        offsetX = touchX - left
                        offsetY = touchY - top
                        return true
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val touchX = event.x
                val touchY = event.y

                selectedShip?.let { ship ->
                    val newLeft = touchX - offsetX - gridLeft - circleSpacing
                    val newTop = touchY - offsetY - gridTop - circleSpacing
                    val newRight = newLeft + ship.width * (circleDiameter + circleSpacing)
                    val newBottom = newTop + ship.height * (circleDiameter + circleSpacing)

                    // Update the ship's position
                    ship.left = (newLeft / (circleDiameter + circleSpacing)).toInt()
                    ship.top = (newTop / (circleDiameter + circleSpacing)).toInt()
                    ship.right = (newRight / (circleDiameter + circleSpacing)).toInt() - 1
                    ship.bottom = (newBottom / (circleDiameter + circleSpacing)).toInt() - 1

                    // Update the ship's position in the shipPositions HashMap
                    shipPositions[ship] = Pair(ship.left, ship.top)
                    println(shipPositions)
                    // Invalidate the view to trigger a redraw
                    invalidate()
                }
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                selectedShip = null
                offsetX = 0f
                offsetY = 0f
                return true
            }
        }
        return false
    }


}

