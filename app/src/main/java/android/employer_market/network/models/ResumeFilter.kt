package android.employer_market.network.models

import android.employer_market.data.constants.Courses

sealed interface ResumeFilter {
    data class BySalary(
        val from: Int = -1,
        val to: Int = Int.MAX_VALUE
    ) : ResumeFilter

    data object None : ResumeFilter
}

