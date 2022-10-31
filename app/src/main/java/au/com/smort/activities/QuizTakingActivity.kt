package au.com.smort.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import au.com.smort.R
import au.com.smort.Singleton.RetrofitSingleton
import au.com.smort.activities.dialogs.LoadingDialog
import au.com.smort.fragments.QuestionAnswer
import au.com.smort.interfaces.QuizAPI
import au.com.smort.interfaces.AnswerSelectedListener
import au.com.smort.models.Quiz
import au.com.smort.models.QuizRound
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://quizzybankky.herokuapp.com/"

class QuizTakingActivity : AppCompatActivity(), View.OnClickListener, AnswerSelectedListener {

    //repository
//    private val quizRepository: QuizRepository = QuizRepository(getDatabase(application))

    //Sharepreference
    private lateinit var SharedPreference: SharedPreferences

    //FireStore
    private val database = Firebase.firestore

    //variables
    private var fQuizzes: List<Quiz>? = null
    private var index: Int = -1
    private var crntRound = QuizRound()

    //diaglog
    private var builder: AlertDialog.Builder? = null

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

        //share preference
        SharedPreference = getSharedPreferences(getString(au.com.smort.R.string.shared_preference_quiz), MODE_PRIVATE)

        //if shared prefernce found in memory restore the quiz
        if(SharedPreference.getInt("currentQuiz", -99) != -99){
            //dialog asking
            createDialog("Previous Session Detected!", "Do you want to resume the quiz?")
        }
        else{
            //LoadDing Activity
            loadingDialog = LoadingDialog(this)
            loadingDialog.startLoading()

            getQuestionFromRetro()
        }
    }


    private fun getQuestionFromRetro(){


        GlobalScope.launch(Dispatchers.IO + coroutineExceptionHandler){

            //Getting the quizzes from API
            fQuizzes = RetrofitSingleton.refreshQuiz()

            fQuizzes?.let {
                crntRound.maxQuestion = it.count()

                //save to database

                //start the quiz
                nextQuestion()
                loadingDialog.dismissDialog()
            }
        }
    }

    /*
    New function from repository pattern
     */
//    private fun getQuestion(){
//        val api = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(QuizAPI::class.java)
//
//        val quizDocument = database.collection("quizzes")
//
//        GlobalScope.launch(Dispatchers.IO + coroutineExceptionHandler){
//            val respond = api.getQuiz("10", "Programming", "easy", "multiple-choice")
//            if (respond.isSuccessful) {
//                fQuizzes = respond.body()!!.data
//                crntRound.maxQuestion = fQuizzes!!.count()
//
//                for(q in fQuizzes!!){
//                    q.generateAnswer()
//                }
//
//                val saveTodatabase =  launch {
//                    //FireStore
//                    for (q in fQuizzes!!){
//                        quizDocument.document("${q.id}").set(q).await()
//                    }
//                }
//                saveTodatabase.invokeOnCompletion { cause: Throwable? ->
//                    if(cause != null){
//                        Log.w("FAIL to add document", "Erro adding document", cause)
//                    }
//                }
//
////                Log.d("mtest", "${questions?.get(0)?.all_answers}")
//                nextQuestion()
//                loadingDialog.dismissDialog()
//            }
//        }
//    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, _ ->

        loadingDialog.dismissDialog()

        GlobalScope.launch(Dispatchers.Main) {
            createAlert("Connection Error!!", "Please check your internet connection and try again")
        }
    }

    private fun createAlert(title: String, message: String){
        builder = AlertDialog.Builder(this)

        builder?.let{
            it.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK"){ _, _ ->
                    finish()
                }.show()
        }
    }

    private fun createDialog(title: String, message: String){
        builder = AlertDialog.Builder(this)

        builder?.let{
            it.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK"){ _, _ ->

                    index = SharedPreference.getInt("currentQuiz", -99)
                    loadingDialog = LoadingDialog(this)
                    loadingDialog.startLoading()

                    getQuestionFromRetro()
                }.setNegativeButton("No Thanks"){_,_ ->
                    loadingDialog = LoadingDialog(this)
                    loadingDialog.startLoading()

                    getQuestionFromRetro()
                }.show()
        }
    }

    /*
    Open new fragment
     */
    private fun nextQuestion() {
        SharedPreference.edit().putInt("currentQuiz", index).apply()
        index++
        if (index < crntRound.maxQuestion){
            val bundle = Bundle()
            bundle.putParcelable("quiz", fQuizzes?.get(index))
            bundle.putInt("crntQ", index)

            val fragment = QuestionAnswer()
            fragment.arguments = bundle

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.quizFragment, fragment)
                .commit()
        }else{
            SharedPreference.edit().remove("currentQuiz").apply()
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