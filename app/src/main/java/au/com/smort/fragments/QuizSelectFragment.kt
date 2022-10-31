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
            QuizBundle("Programming", "Sample text"),
            QuizBundle("Mathematics", "Sample text"),
            QuizBundle("Biology", "Sample text"),
            QuizBundle("Chemistry", "Sample text"),
            QuizBundle("History", "Sample text"),
            QuizBundle("General Knowledge", "Sample text"),
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
        startActivity(intent)
    }

}