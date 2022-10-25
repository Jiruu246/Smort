package au.com.smort.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import au.com.smort.R
import com.google.android.material.snackbar.Snackbar

class QuizSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_select)

        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener{
            startActivity(Intent(this, QuizTakingActivity::class.java))
        }

    }

}