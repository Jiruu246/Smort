package au.com.smort.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import au.com.smort.fragments.DashboardFragment
import au.com.smort.fragments.LeaderboardFragment
import au.com.smort.fragments.QuizSelectFragment


class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val framgents: ArrayList<Fragment>) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return framgents.size
    }

    override fun createFragment(position: Int): Fragment {
        return framgents[position]
    }


}