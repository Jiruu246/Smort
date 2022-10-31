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

    private lateinit var fAPI: QuizAPI

    init {
        fAPI = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizAPI::class.java)
    }

    //get the Quiz using API
    suspend fun refreshQuiz(): List<Quiz>?{
        var lQuiz: List<Quiz>? = null

        val respond = fAPI.getQuiz("10", "Programming", "easy", "multiple-choice")
        if (respond.isSuccessful) {

                lQuiz = respond.body()!!.data

//                maxQ = questions!!.count()

                for(q in lQuiz!!){
                    q.generateAnswer()
                }

//                val saveTodatabase =  launch {
//                    //FireStore
//                    for (q in questions!!){
//                        quizDocument.document("${q.id}").set(q).await()
//                    }
//                }
//                saveTodatabase.invokeOnCompletion { cause: Throwable? ->
//                    if(cause != null){
//                        Log.w("FAIL to add document", "Erro adding document", cause)
//                    }
//                }

//                nextQuestion()
//                loadingDialog.dismissDialog()
        }

        return lQuiz
    }

}