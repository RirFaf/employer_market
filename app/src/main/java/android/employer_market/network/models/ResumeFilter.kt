package android.employer_market.network.models

import android.employer_market.data.constants.Courses

sealed interface ResumeFilter {
    data class ByCourse(
        val from: String = Courses.bachelors1,
        val to: String = Courses.masters2
    ) : ResumeFilter

    data object None : ResumeFilter
}

