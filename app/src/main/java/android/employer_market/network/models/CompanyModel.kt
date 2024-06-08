package android.employer_market.network.models

data class CompanyModel(
    val id: String,
    val userAuthData: UserAuthData,
    val name: String,
    val city: String,
    val age: String,
    val profArea: String,
    val about: String
)
