package au.com.smort.fragments

import android.content.Intent
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import au.com.smort.R
import au.com.smort.activities.Login
import au.com.smort.activities.QuizTakingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class DashboardFragment : Fragment() {

    private lateinit var imgAvatar: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnLogOut: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        //UI
        imgAvatar = view.findViewById(R.id.imgAvt)
        tvName = view.findViewById(R.id.tvUserName)
        tvEmail = view.findViewById(R.id.tvEmail)

//        imgAvatar.setBackgroundResource() = user.photoUrl.toString()

        btnLogOut = view.findViewById(R.id.btnLogOut)

        btnLogOut.setOnClickListener{
            auth.signOut()
            val intent = Intent(activity, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return view
    }

}