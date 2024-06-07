package android.employer_market.data.repository

import android.employer_market.data.constants.TAG
import android.employer_market.network.models.ResumeFilter
import android.employer_market.network.models.ResumeModel
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore

interface SearchRepository {
    fun changeLiked(
        vacancyId: String,
        onFailureAction: () -> Unit
    )

    fun getResumes(
        search: String = "",
        filter: ResumeFilter,
        onSuccessAction: (List<ResumeModel>) -> Unit,
        onFailureAction: () -> Unit
    )

    fun respond(
        vacancyId: String,
        companyId: String,
        onFailureAction: () -> Unit
    )

}

class FirebaseSearchRepository() : SearchRepository {
    override fun changeLiked(vacancyId: String, onFailureAction: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getResumes(
        search: String,
        filter: ResumeFilter,
        onSuccessAction: (List<ResumeModel>) -> Unit,
        onFailureAction: () -> Unit
    ) {
        val partOneResumes = ArrayList<ResumeModel>()
        val resumes = ArrayList<ResumeModel>()
        val likesRef =
            Firebase.database.getReference("companyLikes").child("FfeGJdIJMsSqjjsqa0YwnF8YGkN2")

        val filterRef =
//            when (filter) {
//                is VacancyFilter.None -> {
                    Firebase.firestore.collection("resumeAndroid")
//                }
//
//                is VacancyFilter.BySalary -> {
//                    Firebase.firestore.collection("vacancy")
//                        .whereGreaterThanOrEqualTo("salary", filter.from.toString())
//                        .whereLessThanOrEqualTo("salary", filter.to.toString())
//                }
//            }

        val searchRef =
//            if (search.isNotBlank()) {
//                filterRef
//                    .whereEqualTo(
//                        "companyName",
//                        search.replaceFirstChar { it.uppercaseChar() })
//            } else {
                filterRef
//            }
//
        searchRef
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
                                if (doc.data["id"] == p1Resume.studentId) {
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
                                        )
                                    )
                                    break@docLoop
                                }
                            }
                        }
                        onSuccessAction(resumes)
                        likesRef
                            .get()
                            .addOnSuccessListener {
                                for (resume in resumes) {
                                    innerLoop@ for (data in it.children) {
                                        if (data.value.toString() == resume.id) {
                                            resume.liked = true
                                            break@innerLoop
                                        }
                                    }
                                }
                                if (search.isBlank()) {
                                    onSuccessAction(resumes)
                                }
                            }
                            .addOnFailureListener {
                                Log.e(TAG.FIREBASE, it.stackTraceToString())
                            }
                    }
                    .addOnFailureListener {
                        onFailureAction()
                        Log.e(TAG.FIREBASE, it.toString())
                    }
            }
            .addOnFailureListener { onFailureAction() }

        if (search.isNotBlank()) {
//            filterRef
//                .whereEqualTo(
//                    "position",
//                    search.replaceFirstChar { it.uppercaseChar() }).get()
//                .addOnSuccessListener { documents ->
//                    for (doc in documents) {
//                        vacancies.add(
//                            VacancyModel(
//                                id = doc.data["id"].toString(),
//                                company = CompanyModel(
//                                    id = doc.data["companyId"].toString(),
//                                    name = doc.data["companyName"].toString(),
//                                ),
//                                edArea = doc.data["edArea"].toString(),
//                                formOfEmployment = doc.data["formOfEmployment"].toString(),
//                                location = doc.data["location"].toString(),
//                                position = doc.data["position"].toString(),
//                                requirements = doc.data["requirements"].toString(),
//                                salary = doc.data["salary"].toString().toInt(),
//                                about = doc.data["about"].toString().ifEmpty { " " }
//                            )
//                        )
//                    }
//                    likesRef
//                        .get()
//                        .addOnSuccessListener {
//                            for (vacancy in vacancies) {
//                                innerLoop@ for (data in it.children) {
//                                    if (data.value.toString() == vacancy.id) {
//                                        vacancy.liked = true
//                                        break@innerLoop
//                                    }
//                                }
//                            }
//                            onSuccessAction(vacancies)
//                        }
//                        .addOnFailureListener {
//                            Log.e(TAG.FIREBASE, it.stackTraceToString())
//                        }
//                }
//                .addOnFailureListener {
//                    onFailureAction()
//                    Log.e(TAG.FIREBASE, it.toString())
//                }
        }
    }

    override fun respond(vacancyId: String, companyId: String, onFailureAction: () -> Unit) {
        TODO("Not yet implemented")
    }

}
