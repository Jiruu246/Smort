package au.com.smort.interfaces

import au.com.smort.models.Quiz

interface QandASelectedListenter {

    fun onItemSelect(quiz: Quiz)
}