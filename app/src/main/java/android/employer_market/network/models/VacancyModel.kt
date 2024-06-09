package android.employer_market.network.models

import android.employer_market.data.constants.ResponseStatus
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VacancyModel(
    val id: String = "",
    val position: String = "",
    val salary: Int = 0,
    val edArea: String = "",
    val formOfEmployment: String = "",
    val requirements: String = "",
    val location: String = "",
    val about: String = "",
) : Parcelable