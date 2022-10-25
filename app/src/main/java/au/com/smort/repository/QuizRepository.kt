package au.com.smort.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuizRepository() {

    /*
    refresh offline cache
     */
    suspend fun refreshQuiz(){
        withContext(Dispatchers.IO){

        }
    }
}