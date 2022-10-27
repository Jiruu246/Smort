package au.com.smort.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import au.com.smort.R
import au.com.smort.activities.Login
import au.com.smort.activities.QuizTakingActivity
import com.google.firebase.auth.FirebaseAuth

class DashboardFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val auth = FirebaseAuth.getInstance()

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        view.findViewById<Button>(R.id.btnLogOut).setOnClickListener{
            auth.signOut()
            val intent = Intent(activity, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return view
    }

}