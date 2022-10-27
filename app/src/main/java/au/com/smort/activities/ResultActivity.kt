package au.com.smort.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import au.com.smort.R
import au.com.smort.models.QuizRound

class ResultActivity : AppCompatActivity(), View.OnClickListener{
    lateinit var tvScore: TextView
    lateinit var tvCorrect: TextView
    lateinit var tvSkip: TextView
    lateinit var tvWrong: TextView

    lateinit var btnRetry: Button
    lateinit var btnContinue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvScore  = findViewById(R.id.tvScore)
        tvCorrect = findViewById(R.id.tvCorrectScore)
        tvSkip = findViewById(R.id.tvSkippedScore)
        tvWrong = findViewById(R.id.tvWrongScore)

        btnRetry = findViewById(R.id.btnRetry)
        btnRetry.setOnClickListener(this)
        btnContinue = findViewById(R.id.btnContinue)
        btnContinue.setOnClickListener(this)

        var quizRound = intent.getParcelableExtra<QuizRound>("round")

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

                startActivity(Intent(this, QuizTakingActivity::class.java))
            }
            R.id.btnContinue -> {
                finish()
            }
        }
    }
}