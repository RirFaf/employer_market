package android.employer_market.network.models

import android.employer_market.data.constants.ResponseStatus
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VacancyModel(
    val id: String = "-1",
    val position: String = "-1",
    val salary: Int = -1,
    val edArea: String = "-1",
    val formOfEmployment: String = "-1",
    val requirements: String = "-1",
    val location: String = "-1",
    val about: String = "",
) : Parcelable