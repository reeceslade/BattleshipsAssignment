package uk.ac.bournemouth.ap.battleships

import android.content.Context
import android.util.AttributeSet
import android.view.View

// Shared base class
abstract class BaseView : View {
    // Common properties and methods shared by child classes
    // ...
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

}

// Child class 1
class PlayerView : BaseView() {
    // Functionality specific to PlayerView
    // ...
}

// Child class 2
class OpponentView : BaseView() {
    // Functionality specific to OpponentView
    // ...
}

// Child class 3 (Intermediate child type)
class SharedChildView : BaseView() {
    // Functionality shared between PlayerView and OpponentView
    // ...
}
