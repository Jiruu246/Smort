package au.com.smort.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class QuizRound(
    val maxHealth: Int = 100,
    var Score: Int = 0,
    var countSkipped: Int = 0,
    var countCorrected: Int = 0,
    var countWrong: Int = 0,
    var crntHealth: Int = 100,
    var maxQuestion: Int = 0): Parcelable {

    var _score: Int = 0
        set(value) {
            if(value < 0){
                field = 0
            }else{
                field = _score
            }
            Score = field
        }
}