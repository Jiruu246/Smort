package au.com.smort.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.com.smort.R
import au.com.smort.activities.QuizTakingActivity
import au.com.smort.adapters.QuizBundleViewAdapter
import au.com.smort.interfaces.BundleSelectedListener
import au.com.smort.models.QuizBundle

class QuizSelectFragment : Fragment(), BundleSelectedListener {

    private lateinit var category: Array<QuizBundle>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        category = arrayOf(
            QuizBundle("Programming", "Sample text", "easy"),
            QuizBundle("Mathematics", "Sample text", "easy"),
            QuizBundle("Biology", "Sample text", "easy"),
            QuizBundle("Chemistry", "Sample text", "easy"),
            QuizBundle("History", "Sample text", "easy"),
            QuizBundle("General Knowledge", "Sample text", "easy"),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quiz_select, container, false)

        with(view.findViewById<RecyclerView>(R.id.bundle_select)){
            layoutManager = LinearLayoutManager(context)
            adapter = QuizBundleViewAdapter(category, this@QuizSelectFragment)
        }


        return view
    }

    override fun onBundleSelected(bundle: QuizBundle) {
        val intent = Intent(activity, QuizTakingActivity::class.java)
        intent.putExtra("quizBundle", bundle)
        startActivity(intent)
    }

}