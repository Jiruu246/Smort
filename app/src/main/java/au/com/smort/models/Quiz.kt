package au.com.smort.models

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize

@Parcelize
class Quiz (
    val id: Int = 0,
    val question: String = "",
    val difficulty: String = "",
    val type:String = "",
    val category: String = "",
    val correct_answer: String = "",
    private val incorrect_answers:  ArrayList<String> = arrayListOf(),
    val explanation: String = "",
    var all_answers: ArrayList<Answer>? = null
    ) : Parcelable{

    fun generateAnswer(){
        all_answers = arrayListOf(
            Answer(correct_answer, true),
            Answer(incorrect_answers[0], false),
            Answer(incorrect_answers[1], false),
            Answer(incorrect_answers[2], false),
        )
        all_answers = all_answers!!.shuffled() as ArrayList<Answer> /* = java.util.ArrayList<au.com.smort.models.Answer> */
    }
}