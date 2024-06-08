package android.employer_market.data.repository

import android.employer_market.data.constants.TAG
import android.employer_market.network.models.ResumeModel
import android.employer_market.network.models.VacancyModel
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore

interface FavouritesRepository {
    fun getLikedResumes(
        onSuccessAction: (List<ResumeModel>) -> Unit,
        onFailureAction: () -> Unit
    )

    fun invite(
        vacancyId: String,
        studentId: String,
        onFailureAction: () -> Unit
    )

    fun changeLiked(
        resumeId: String,
        onFailureAction: () -> Unit
    )

    fun getMyVacancies(
        onSuccessAction: (List<VacancyModel>) -> Unit,
        onFailureAction: () -> Unit
    )
}

class FirebaseFavouritesRepository() : FavouritesRepository {
    override fun getMyVacancies(
        onSuccessAction: (List<VacancyModel>) -> Unit,
        onFailureAction: () -> Unit
    ) {
        SMFirebase.getMyVacancies(onSuccessAction, onFailureAction)
    }

    override fun getLikedResumes(
        onSuccessAction: (List<ResumeModel>) -> Unit,
        onFailureAction: () -> Unit
    ) {
        val partOneResumes: MutableSet<ResumeModel> = mutableSetOf()
        val resumes = ArrayList<ResumeModel>()
        val likesRef =
            Firebase.database.getReference("companyLikes").child(Firebase.auth.currentUser!!.uid)
        likesRef
            .get()
            .addOnSuccessListener {
                val likesIds = ArrayList<String>()
                for (data in it.children) {
                    likesIds.add(data.value.toString())
                }
                Firebase.firestore.collection("resumeAndroid")
                    .get()
                    .addOnSuccessListener { resumeSnapshot ->
                        for (resume in resumeSnapshot) {
                            partOneResumes.add(
                                ResumeModel(
                                    id = resume.id,
                                    studentId = resume.data["studentId"].toString(),
                                    keySkills = resume.data["keySkills"].toString(),
                                    salary = resume.data["salary"].toString(),
                                )
                            )
                        }
                        Firebase.firestore.collection("users")
                            .get()
                            .addOnSuccessListener { documents ->
                                for (p1Resume in partOneResumes) {
                                    docLoop@ for (doc in documents) {
                                        if (doc.data["id"] == p1Resume.studentId &&
                                            likesIds.contains(p1Resume.id)
                                        ) {
                                            resumes.add(
                                                ResumeModel(
                                                    id = p1Resume.id,
                                                    studentId = p1Resume.studentId,
                                                    keySkills = p1Resume.keySkills,
                                                    salary = p1Resume.salary,
                                                    secondName = doc.data["secondName"].toString(),
                                                    firstName = doc.data["firstName"].toString(),
                                                    patronymicName = doc.data["patronymicName"].toString(),
                                                    birthDate = doc.data["birthDate"].toString(),
                                                    university = doc.data["university"].toString(),
                                                    institute = doc.data["institute"].toString(),
                                                    course = doc.data["course"].toString(),
                                                    phoneNumber = doc.data["phoneNumber"].toString(),
                                                    aboutMe = doc.data["aboutMe"].toString(),
                                                    gender = doc.data["gender"].toString(),
                                                    city = doc.data["city"].toString(),
                                                    direction = doc.data["direction"].toString(),
                                                    liked = true
                                                )
                                            )
                                            break@docLoop
                                        }
                                    }
                                }
                                onSuccessAction(resumes)
                            }
                    }
            }
            .addOnFailureListener {
                onFailureAction()
                Log.e(TAG.FIREBASE, it.stackTraceToString())
            }
    }

    override fun invite(
        vacancyId: String,
        studentId: String,
        onFailureAction: () -> Unit
    ) {
        SMFirebase.invite(vacancyId, studentId, onFailureAction)
    }

    override fun changeLiked(
        resumeId: String,
        onFailureAction: () -> Unit
    ) {
        SMFirebase.changeLiked(resumeId, onFailureAction)
    }
}