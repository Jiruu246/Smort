package au.com.smort.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import au.com.smort.R
import au.com.smort.models.Quiz
import au.com.smort.models.QuizBundle
import au.com.smort.models.QuizRound

class ResultActivity : AppCompatActivity(), View.OnClickListener{
    lateinit var tvScore: TextView
    lateinit var tvCorrect: TextView
    lateinit var tvSkip: TextView
    lateinit var tvWrong: TextView
    lateinit var tvExplain: TextView

    lateinit var btnRetry: Button
    lateinit var btnContinue: Button

    lateinit var fQuizBundle: QuizBundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvScore  = findViewById(R.id.tvScore)
        tvCorrect = findViewById(R.id.tvCorrectScore)
        tvSkip = findViewById(R.id.tvSkippedScore)
        tvWrong = findViewById(R.id.tvWrongScore)
        tvExplain = findViewById(R.id.tvExplain)

        btnRetry = findViewById(R.id.btnRetry)
        btnRetry.setOnClickListener(this)
        btnContinue = findViewById(R.id.btnContinue)
        btnContinue.setOnClickListener(this)
        tvExplain = findViewById(R.id.tvExplain)
        tvExplain.setOnClickListener(this)

        var quizRound = intent.getParcelableExtra<QuizRound>("round")
        fQuizBundle = intent.getParcelableExtra("quizBundle")!!

        quizRound?.let {
            tvScore.text = it.Score.toString()
            tvCorrect.text = "${it.countCorrected}/10"
            tvSkip.text = "${it.countSkipped}/10"
            tvWrong.text = "${it.countWrong}/10"
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnRetry -> {
                finish()
                startActivity(Intent(this, QuizTakingActivity::class.java).putExtra("quizBundle", fQuizBundle))
            }
            R.id.btnContinue -> {
                finish()
            }
            R.id.tvExplain -> {
                startActivity(Intent(this, CheckAnswerActivity::class.java))
            }
        }
    }
}