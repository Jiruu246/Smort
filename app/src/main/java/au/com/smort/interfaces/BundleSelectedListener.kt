package au.com.smort.interfaces

import au.com.smort.models.QuizBundle

interface BundleSelectedListener {
    fun onBundleSelected(bundle: QuizBundle)
}