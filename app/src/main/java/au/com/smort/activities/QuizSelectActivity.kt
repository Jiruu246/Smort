package au.com.smort.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import au.com.smort.R
import au.com.smort.adapters.ViewPagerAdapter
import au.com.smort.fragments.DashboardFragment
import au.com.smort.fragments.LeaderboardFragment
import au.com.smort.fragments.QuizSelectFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class QuizSelectActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    lateinit var adapter: ViewPagerAdapter
    lateinit var fragments: ArrayList<Fragment>
    val tabTitles = arrayOf("Dashboard", "Quiz", "Rank")
    val tabIcons = arrayOf(R.drawable.ic_nature, R.drawable.ic_question_mark, R.drawable.ic_leadboard)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_select)

        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager2>(R.id.viewPager)
        fragments = arrayListOf(
            DashboardFragment(),
            QuizSelectFragment(),
            LeaderboardFragment()
        )
        adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, fragments)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager, object: TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = tabTitles[position]
                tab.setIcon(tabIcons[position])
            }

        }).attach()

    }

}