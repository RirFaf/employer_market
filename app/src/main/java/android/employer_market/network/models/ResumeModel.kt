package android.employer_market.network.models

import android.employer_market.data.constants.ResponseStatus

data class ResumeModel(
    var id:String = "-1",
    var studentId:String = "-1",
    var salary:String = "-1",
    var keySkills:String = "-1",
    var secondName: String = "-1",
    var firstName: String = "-1",
    var patronymicName: String = "-1",
    var birthDate: String = "-1",
    var university: String = "-1",
    var institute: String = "-1",
    var course: String = "-1",
    var phoneNumber: String = "-1",
    var aboutMe: String = "-1",
    var gender: String = "-1",
    var city: String = "-1",
    var direction: String = "-1",
    var liked: Boolean = false,
    var responseStatus:String = ResponseStatus.EMPTY

)
