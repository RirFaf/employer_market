package android.employer_market.network.models

data class CompanyModel(
    val id:String,
    val userAuthData: UserAuthData,
    val companyName: String,
    val city: String,
)
