package au.com.smort.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import au.com.smort.R
import au.com.smort.interfaces.AnswerSelectedListener
import au.com.smort.models.Quiz

class QuestionAnswer : Fragment() {

    lateinit var listener: AnswerSelectedListener

    private var quiz: Quiz? = null
    private var index: Int = 0
    lateinit var viewProgress: TextView
    lateinit var viewQuestion: TextView
    lateinit var btnAnswer1: Button
    lateinit var btnAnswer2: Button
    lateinit var btnAnswer3: Button
    lateinit var btnAnswer4: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as AnswerSelectedListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement " + AnswerSelectedListener::class.java.name)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            quiz = it.getParcelable("quiz")
            index = it.getInt("crntQ")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_question_answer, container, false)

        //initialize UI elements
        viewProgress = view.findViewById(R.id.tvProgress)
        viewQuestion = view.findViewById(R.id.tvQuestion)
        btnAnswer1 = view.findViewById(R.id.btnAns1)
        btnAnswer2 = view.findViewById(R.id.btnAns2)
        btnAnswer3 = view.findViewById(R.id.btnAns3)
        btnAnswer4 = view.findViewById(R.id.btnAns4)

        //populate data
        index.let {
            viewProgress.text = "Question ${index+1}"
        }

        quiz?.let { q ->
            viewQuestion.text = q.question

            q.all_answers?.get(0)?.let { ans ->
                with(btnAnswer1) {
                    text = ans.text
                    setOnClickListener {
                        listener.onAnswerSelected(ans.isCorrect)
                    }
                }
            }

            q.all_answers?.get(1)?.let { ans ->
                with(btnAnswer2) {
                    text = ans.text
                    setOnClickListener {
                        listener.onAnswerSelected(ans.isCorrect)
                    }
                }
            }

            q.all_answers?.get(2)?.let { ans ->
                with(btnAnswer3) {
                    text = ans.text
                    setOnClickListener {
                        listener.onAnswerSelected(ans.isCorrect)
                    }
                }
            }

            q.all_answers?.get(3)?.let { ans ->
                with(btnAnswer4) {
                    text = ans.text
                    setOnClickListener {
                        listener.onAnswerSelected(ans.isCorrect)
                    }
                }
            }

        }


        return view
    }

}