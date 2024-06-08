package android.employer_market.view_model.event

sealed interface ProfileEvent {
    data object UpdateCompanyInfo:ProfileEvent
    data class SetName(val input:String):ProfileEvent
    data class SetAge(val input:String):ProfileEvent
    data class SetProfArea(val input:String):ProfileEvent
    data class SetAbout(val input:String):ProfileEvent
    data class SetCity(val input:String):ProfileEvent
}