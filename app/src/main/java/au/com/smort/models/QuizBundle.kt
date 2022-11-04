package au.com.smort.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizBundle(val category: String, val description: String, var level: String) : Parcelable{
}