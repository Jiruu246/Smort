package au.com.smort.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import au.com.smort.R
import au.com.smort.activities.dialogs.ErrorDialog
import au.com.smort.activities.dialogs.LoadingDialog
import au.com.smort.fragments.QuestionAnswer
import au.com.smort.interfaces.QuizAPI
import au.com.smort.interfaces.AnswerSelectedListener
import au.com.smort.models.Quiz
import au.com.smort.models.QuizRound
import au.com.smort.repository.QuizRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://quizzybankky.herokuapp.com/"

class QuizTakingActivity : AppCompatActivity(), View.OnClickListener, AnswerSelectedListener {

    //repository
//    private val quizRepository: QuizRepository = QuizRepository(getDatabase(application))

    //FireStore
    private val database = Firebase.firestore

    //variables
    private var questions: List<Quiz>? = null
    private var index: Int = -1
    private var crntRound = QuizRound()
    private var maxQ: Int = 0

    //UI declare
    lateinit var skipBtn: Button
    lateinit var healthBar: ProgressBar
    lateinit var healthCount: TextView
    lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_taking)

        //UI create
        skipBtn = findViewById(R.id.skipQbtn)
        skipBtn.setOnClickListener(this)

        healthBar = findViewById(R.id.healthBar)
        healthCount = findViewById(R.id.tvHealtCount)
        healthBar.max = crntRound.maxHealth
        healthBar.progress = crntRound.crntHealth
        healthCount.text = crntRound.maxHealth.toString()

        //LoadDing Activity
        loadingDialog = LoadingDialog(this)
        loadingDialog.startLoading()

        getQuestionFromRepo()
    }


    private fun getQuestion(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizAPI::class.java)

        GlobalScope.launch(Dispatchers.IO){
            val respond = api.getQuiz("10", "Programming", "easy", "multiple-choice")
            if (respond.isSuccessful) {
                questions = respond.body()!!.data
                maxQ = questions!!.count()

                for(q in questions!!){
                    q.generateAnswer()
                }
//                Log.d("mtest", "${questions?.get(0)?.all_answers}")
                nextQuestion()
                loadingDialog.dismissDialog()
            }
        }
    }

    /*
    New function from repository pattern
     */
    private fun getQuestionFromRepo(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizAPI::class.java)

        val quizDocument = database.collection("quizzes")

        GlobalScope.launch(Dispatchers.IO + coroutineExceptionHandler){
            val respond = api.getQuiz("10", "Programming", "easy", "multiple-choice")
            if (respond.isSuccessful) {
                questions = respond.body()!!.data
                maxQ = questions!!.count()

                for(q in questions!!){
                    q.generateAnswer()
                }

                //FireStore
                for (q in questions!!){
                    quizDocument.document("${q.id}").set(q).await()
                }

//                Log.d("mtest", "${questions?.get(0)?.all_answers}")
                nextQuestion()
                loadingDialog.dismissDialog()
            }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        Log.d("coroutine","CoroutineExceptionHandler got $throwable")
        loadingDialog.dismissDialog()
        finish()
//        val errorDialog = ErrorDialog(this)
//        errorDialog.startDialog()
    }

    /*
    Open new fragment
     */
    private fun nextQuestion() {
        index++
        if (index < maxQ){
            val bundle = Bundle()
            bundle.putParcelable("quiz", questions?.get(index))
            bundle.putInt("crntQ", index)

            val fragment = QuestionAnswer()
            fragment.arguments = bundle

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.quizFragment, fragment)
                .commit()
        }else{
            finish()
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("round", crntRound)

            startActivity(intent)
        }

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.skipQbtn -> {
                takeDamage(10)
                crntRound.countSkipped++
                crntRound.Score -= 10
                nextQuestion()
            }
        }
    }

    override fun onAnswerSelected(isCorrect: Boolean) {
        if (isCorrect){
            Log.d("results", "you just click on correct answer")
            crntRound.countCorrected++
            crntRound.Score += 10
        }else{
            Log.d("results", "that's wrong")
            crntRound.countWrong++
            crntRound.Score -= 10
            takeDamage(10)
        }
        nextQuestion()
    }

    private fun takeDamage(dame: Int){
        crntRound.crntHealth -= dame
        healthBar.progress = crntRound.crntHealth
        healthCount.text = crntRound.crntHealth.toString()
    }
}