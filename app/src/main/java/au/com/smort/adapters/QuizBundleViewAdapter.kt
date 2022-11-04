package au.com.smort.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import au.com.smort.R
import au.com.smort.interfaces.BundleSelectedListener
import au.com.smort.models.QuizBundle

class QuizBundleViewAdapter(private val quizzes: Array<QuizBundle>, private val listener: BundleSelectedListener) :
    RecyclerView.Adapter<QuizBundleViewAdapter.ViewHolder>(){

    private var isExpand = Array(quizzes.size) { _ -> false}

    private val fDifficultLevel: HashMap<Int, String> = hashMapOf(
        0 to "easy",
        1 to "medium",
        2 to "hard"
    )

    inner class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        var tvTitle: TextView
        var vDescription: TextView
        var sbLevel: SeekBar
        var btnStartQuiz: Button
        var vitem: ConstraintLayout
        var vExpand: GridLayout
        init {
            tvTitle = itemview.findViewById(R.id.Questiontype)
            vDescription = itemview.findViewById(R.id.description)
            sbLevel = itemview.findViewById(R.id.seekBar3)
            btnStartQuiz = itemview.findViewById(R.id.startQuizButton)
            vitem = itemview.findViewById(R.id.list_questions)
            vExpand = itemview.findViewById(R.id.level_selection)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_question_bundle, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bundle = quizzes[position]

        with(holder){
            tvTitle.text = bundle.category
            vDescription.text = bundle.description

            vExpand.visibility = if(isExpand[position]) View.VISIBLE else View.GONE

            vitem.setOnClickListener{
                isExpand[position] = !isExpand[position]
                notifyItemChanged(position)
            }

            btnStartQuiz.setOnClickListener{

                bundle.level = fDifficultLevel.get(sbLevel.progress).toString()
                listener.onBundleSelected(bundle)
            }

        }
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }
}