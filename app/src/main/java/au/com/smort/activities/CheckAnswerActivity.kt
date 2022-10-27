package au.com.smort.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.com.smort.R
import au.com.smort.adapters.QuizViewAdapter
import au.com.smort.interfaces.AnswerSelectedListener
import au.com.smort.interfaces.QandASelectedListenter
import au.com.smort.models.Quiz
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CheckAnswerActivity : AppCompatActivity(), QandASelectedListenter {

    private val database = Firebase.firestore
    private var quizzes: ArrayList<Quiz>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_answer)

        database.collection("quizzes")
            .get()
            .addOnSuccessListener { results ->
                quizzes = arrayListOf()
                for(r in results){
                    val quiz = r.toObject(Quiz::class.java)

                    quizzes!!.add(quiz)

                }

                quizzes?.let{
                    with(findViewById<RecyclerView>(R.id.recyclerView)){
                        layoutManager = LinearLayoutManager(this@CheckAnswerActivity)
                        adapter = QuizViewAdapter(it, this@CheckAnswerActivity)
                    }
                }

            }
            .addOnFailureListener{e ->
                Log.w("CheckHere", "Error exception", e)
            }





    }



    override fun onItemSelect(quiz: Quiz) {

    }

}