package android.employer_market.network.models

data class StudentModel(
    val id: String,
    val secondName: String,
    val firstName: String,
    val patronymicName: String,
    var birthDate: String,
    var university: String,
    var institute: String,
    var course: String,
    var phoneNumber: String,
    var aboutMe: String,
    var gender: String,
    var city: String,
    var direction: String
)



