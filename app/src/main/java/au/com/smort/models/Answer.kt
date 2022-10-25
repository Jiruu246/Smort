package au.com.smort.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answer(
    val text: String,
    val isCorrect: Boolean
) : Parcelable {
}