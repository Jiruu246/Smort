package au.com.smort.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import au.com.smort.R
import au.com.smort.activities.QuizTakingActivity

class QuizSelectFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quiz_select, container, false)

        view.findViewById<Button>(R.id.button4).setOnClickListener{
            val intent = Intent(activity, QuizTakingActivity::class.java)
            startActivity(intent)
        }


        return view
    }

}