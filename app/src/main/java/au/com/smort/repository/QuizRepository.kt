package au.com.smort.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import au.com.smort.models.Quiz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuizRepository() {

//    val quiz: LiveData<List<Quiz>> = Transformations.map(database.videoDao.getQuizzes())
    /*
    refresh offline cache
     */
    suspend fun refreshQuiz(){
        withContext(Dispatchers.IO){
            //fetch API from network using an API Instance

            //insert data into firebase


        }
    }
}