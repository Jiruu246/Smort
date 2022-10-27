package au.com.smort.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answer(
    val text: String = "",
    @field:JvmField
    val isCorrect: Boolean = false
) : Parcelable {
}