package au.com.smort.interfaces

import au.com.smort.models.APIRespond
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizAPI {

    @GET("/api/v1/questions")
    suspend fun getQuiz(@Query("limit") limit:String,
                        @Query("category") category:String,
                        @Query("difficulty") difficulty:String,
                        @Query("type") type: String): Response<APIRespond>
}