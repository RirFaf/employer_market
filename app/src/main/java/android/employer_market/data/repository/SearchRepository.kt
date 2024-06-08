package android.employer_market.data.repository

import android.employer_market.data.constants.TAG
import android.employer_market.network.models.ResumeFilter
import android.employer_market.network.models.ResumeModel
import android.employer_market.network.models.VacancyModel
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore

interface SearchRepository {
    fun changeLiked(
        resumeId: String,
        onFailureAction: () -> Unit
    )

    fun getResumes(
        search: String = "",
        filter: ResumeFilter,
        onSuccessAction: (List<ResumeModel>) -> Unit,
        onFailureAction: () -> Unit
    )

    fun invite(
        vacancyId: String,
        studentId: String,
        onFailureAction: () -> Unit
    )

    fun getMyVacancies(
        onSuccessAction: (List<VacancyModel>) -> Unit,
        onFailureAction: () -> Unit
    ) {
    }
}

class FirebaseSearchRepository() : SearchRepository {

    override fun getMyVacancies(
        onSuccessAction: (List<VacancyModel>) -> Unit,
        onFailureAction: () -> Unit
    ) {
        SMFirebase.getMyVacancies(onSuccessAction, onFailureAction)
    }

    override fun changeLiked(resumeId: String, onFailureAction: () -> Unit) {
        SMFirebase.changeLiked(resumeId, onFailureAction)
    }

    override fun getResumes(
        search: String,
        filter: ResumeFilter,
        onSuccessAction: (List<ResumeModel>) -> Unit,
        onFailureAction: () -> Unit
    ) {
        val partOneResumes:MutableSet<ResumeModel> = mutableSetOf()
        val resumes = ArrayList<ResumeModel>()
        val likesRef =
            Firebase.database.getReference("companyLikes").child(Firebase.auth.currentUser!!.uid)

        val filterRef =
            when (filter) {
                is ResumeFilter.None -> {
                    Firebase.firestore.collection("resumeAndroid")
                }

                is ResumeFilter.BySalary -> {
                    Firebase.firestore.collection("resumeAndroid")
                        .whereGreaterThanOrEqualTo("salary", filter.from.toString())
                        .whereLessThanOrEqualTo("salary", filter.to.toString())
                }
            }

        val searchRef =
            if (search.isNotBlank()) {
                Firebase.firestore.collection("users")
                    .whereEqualTo(
                        "institute",
                        search.replaceFirstChar { it.uppercaseChar() })
            } else {
                Firebase.firestore.collection("users")
            }

        filterRef
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
                searchRef
                    .get()
                    .addOnSuccessListener { documents ->
                        Log.d("MyTag", "size = ${documents.size()}")
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
            filterRef
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
                        .whereEqualTo(
                            "direction",
                            search.replaceFirstChar { it.uppercaseChar() })
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
                                    onSuccessAction(resumes)
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
                .addOnFailureListener {
                    onFailureAction()
                    Log.e(TAG.FIREBASE, it.toString())
                }
        }
    }

    override fun invite(
        vacancyId: String,
        studentId: String,
        onFailureAction: () -> Unit
    ) {
        SMFirebase.invite(vacancyId, studentId, onFailureAction)
    }
}

