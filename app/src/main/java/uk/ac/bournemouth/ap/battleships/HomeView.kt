package uk.ac.bournemouth.ap.battleships

//5A!!!

import Bships.StudentBattleshipOpponent
import Bships.StudentGrid
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
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate

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


    private val colCount: Int get() = game.columns
        private val rowCount: Int get() = game.rows
        private var circleDiameter: Float = 0f
        private var circleSpacing: Float = 0f
        private var circleSpacingRatio: Float = 0.2f

        private val gridPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
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
        private val player2Paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = Color.RED
        }

        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            super.onSizeChanged(w, h, oldw, oldh)
            val diameterX = w / (colCount + (colCount + 1) * circleSpacingRatio)
            val diameterY = h / (rowCount + (rowCount + 1) * circleSpacingRatio)
            circleDiameter = minOf(diameterX, diameterY)
            circleSpacing = circleDiameter * circleSpacingRatio
        }

        private fun recalculateDimensions(w: Int = width, h: Int = height) {}

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            val gridLeft: Float = 0f
            val gridTop: Float = 0f
            val gridRight: Float =
                gridLeft + colCount * (circleDiameter + circleSpacing) + circleSpacing
            val gridBottom: Float =
                gridTop + rowCount * (circleDiameter + circleSpacing) + circleSpacing
            val radius = circleDiameter / 2f
            canvas?.drawRect(gridLeft, gridTop, gridRight, gridBottom, gridPaint)

            for (row in 0 until rowCount) {
                // The vertical center is the same for each circle in the row
                val cy = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * row) + radius

                for (col in 0 until colCount) {
                    // We will later on want to use the game data to determine this
                    val paint = when (game.get(1, 4)) {
                        1 -> player1Paint
                        2 -> player2Paint
                        else -> null
                    }

                    // Drawing circles uses the center and radius
                    val cx =
                        gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * col) + radius
                    val rectLeft = cx - radius
                    val rectTop = cy - radius
                    val rectRight = cx + radius
                    val rectBottom = cy + radius
                    canvas?.drawRect(rectLeft, rectTop, rectRight, rectBottom, paint)

                }
            }
        }

        private val gestureDetector = GestureDetectorCompat(context, object :
            GestureDetector.SimpleOnGestureListener() {

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

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                val columnTouched =
                    ((e.x - circleSpacing * 0.5f) / (circleSpacing + circleDiameter)).toInt()
                if (columnTouched in 0 until game.columns) {
                    val isValidMove = game.shipAt(columnTouched,  game.playerTurn)
                    invalidate()
                    if (isValidMove) {
                        // Handle valid move
                        return true
                    } else {
                        // Show Snackbar message for invalid move
                        val message = "Invalid move!"
                        val duration = Snackbar.LENGTH_SHORT
                        val snackbar = Snackbar.make(this@HomeView, message, duration)
                        snackbar.show()
                        return false
                    }
                } else {
                    return false
                }
            }
        })

        override fun onTouchEvent(event: MotionEvent): Boolean {
            return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
        }
    }

}