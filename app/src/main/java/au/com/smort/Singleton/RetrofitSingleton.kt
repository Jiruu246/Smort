package au.com.smort.Singleton

import android.util.Log
import au.com.smort.interfaces.QuizAPI
import au.com.smort.models.Quiz
import au.com.smort.models.QuizRound
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object RetrofitSingleton {

    const val BASE_URL = "https://quizzybankky.herokuapp.com/"

    private var fAPI: QuizAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuizAPI::class.java)

    //get the Quiz using API
    suspend fun refreshQuiz(category: String, difficulty: String): List<Quiz>?{
        var lQuiz: List<Quiz>? = null

        val respond = fAPI.getQuiz("10", category, difficulty, "multiple-choice")
        if (respond.isSuccessful) {

                lQuiz = respond.body()!!.data

                for(q in lQuiz!!){
                    q.generateAnswer()
                }

        }

        return lQuiz
    }

}