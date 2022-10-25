package au.com.smort.models

data class APIRespond(
    val status: String,
    val message: String,
    val data: List<Quiz>?
) {
}