package au.com.smort.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import au.com.smort.R
import au.com.smort.interfaces.QandASelectedListenter
import au.com.smort.models.Quiz

class QuizViewAdapter(private val quizzes: List<Quiz>, private val listener: QandASelectedListenter) :
    RecyclerView.Adapter<QuizViewAdapter.ViewHolder>(){

    private var isExpand = Array(quizzes.size) { _ -> false}

    inner class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        var tvTitle: TextView
        var vQuestion: TextView
        var vAnswer: TextView
        var vCorrect: TextView
        var vExplain: TextView
        var vitem: ConstraintLayout
        var vExpand: GridLayout
        init {
            tvTitle = itemview.findViewById(R.id.tvQuestionNumber)
            vQuestion = itemview.findViewById(R.id.tvQuestionTitle)
            vAnswer = itemview.findViewById(R.id.tvCorrectAns)
            vCorrect = itemview.findViewById(R.id.tvCorrectAns_list)
            vExplain = itemview.findViewById(R.id.tvExplain_list)
            vitem = itemview.findViewById(R.id.list_item)
            vExpand = itemview.findViewById(R.id.gridlayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_question, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quiz = quizzes[position]

        with(holder){
            tvTitle.text = "Question ${position+1}"
            vQuestion.text = quiz.question
            vAnswer.text = quiz.correct_answer
            vCorrect.text = "Correct Answer: ${quiz.correct_answer}"
            vExplain.text = "Explaination: ${quiz.explanation}"
            vExpand.visibility = if(isExpand[position]) View.VISIBLE else View.GONE

            vitem.setOnClickListener{
                isExpand[position] = !isExpand[position]
                notifyItemChanged(position)
                listener.onItemSelect(quiz)
            }

        }
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }
}