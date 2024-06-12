package android.employer_market.data.repository

import android.employer_market.data.constants.TAG
import android.employer_market.network.models.ChatModel
import android.employer_market.network.models.MessageModel
import android.employer_market.network.models.ResumeModel
import android.employer_market.network.models.StudentModel
import android.employer_market.network.models.VacancyModel
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore

interface ResponsesRepository {
    fun addChat(
        vacancyId: String,
        companyId: String,
        studentId: String,
    )

    fun getResponses(
        onSuccessAction: (List<Pair<ResumeModel, VacancyModel>>) -> Unit,
        onFailureAction: () -> Unit
    )

    fun changeResponseStatus(
        studentId: String,
        vacancyId: String,
        status: String,
        onSuccessAction: () -> Unit,
        onFailureAction: () -> Unit
    )
}

class FirebaseResponsesRepository() : ResponsesRepository {
    override fun changeResponseStatus(
        studentId: String,
        vacancyId: String,
        status: String,
        onSuccessAction: () -> Unit,
        onFailureAction: () -> Unit
    ) {
        val responsesRef = Firebase.database.getReference("responses")
        responsesRef
            .child(studentId)
            .get()
            .addOnSuccessListener {
                var vacancyKey = ""
                for (data in it.children) {
                    if (data.child("vacancyId").value.toString() == vacancyId) {
                        vacancyKey = data.key.toString()
                    }
                }
                responsesRef
                    .child(studentId)
                    .child(vacancyKey)
                    .child("status")
                    .setValue(status)
                    .addOnSuccessListener {
                        onSuccessAction()
                    }
                    .addOnFailureListener { exc ->
                        onFailureAction()
                        Log.e("MyTag", exc.stackTraceToString())
                    }
            }
            .addOnFailureListener {
                onFailureAction()
                Log.e("MyTag", it.stackTraceToString())
            }
    }

    override fun addChat(
        vacancyId: String,
        companyId: String,
        studentId: String
    ) {
        val chatsRef = Firebase.database.getReference("messages1")
        val vacancyRef =
            Firebase.firestore.collection("vacancy")
                .document(vacancyId)
        val studentRef = Firebase.firestore.collection("users").whereEqualTo("id", studentId)
        var alreadyAdded = false
        chatsRef
            .get()
            .addOnSuccessListener {
                for (data in it.children) {
                    if (data.child("chatId").value == studentId + companyId + vacancyId) {
                        alreadyAdded = true
                        break
                    }
                }
                if (!alreadyAdded) {
                    vacancyRef
                        .get()
                        .addOnSuccessListener { vacancySnapshot ->
                            studentRef
                                .get()
                                .addOnSuccessListener { studentSnapshot ->
                                    chatsRef
                                        .push()
                                        .setValue(
                                            ChatModel(
                                                vacancyId = vacancyId,
                                                vacancyName = vacancySnapshot.data!!["position"].toString(),
                                                companyId = companyId,
                                                companyName = vacancySnapshot.data!!["companyName"].toString(),
                                                studentId = studentId,
                                                studentName = "${studentSnapshot.documents.last().data!!["secondName"].toString()} " +
                                                        "${studentSnapshot.documents.last().data!!["firstName"].toString()} " +
                                                        studentSnapshot.documents.last().data!!["patronymicName"].toString(),
                                                messages = listOf(
                                                    MessageModel(
                                                        "Здравстуйте!",
                                                        Firebase.auth.currentUser!!.uid,
                                                        System.currentTimeMillis().toString()
                                                    ),
                                                )
                                            )
                                        )
                                }
                        }
                }
            }
            .addOnFailureListener {
                Log.e(TAG.FIREBASE, it.stackTraceToString())
            }
    }

    override fun getResponses(
        onSuccessAction: (List<Pair<ResumeModel, VacancyModel>>) -> Unit,
        onFailureAction: () -> Unit
    ) {
        val vacanciesIds = ArrayList<String>()
        val usersIds = ArrayList<String>()
        val resumes = ArrayList<ResumeModel>()
        val vacancies = ArrayList<VacancyModel>()
        val responses = ArrayList<Pair<ResumeModel, VacancyModel>>()
        val responseStatuses = ArrayList<String>()

        val responsesRef = Firebase.database.getReference("responses")
        val usersRef = Firebase.firestore.collection("users")
        val resumesRef = Firebase.firestore.collection("resumeAndroid")
        val vacancyRef = Firebase.firestore.collection("vacancy")

        responsesRef
            .get()
            .addOnSuccessListener { responsesSnapshot ->
                for (user in responsesSnapshot.children) {
                    for (responseData in user.children) {
                        usersIds.add(user.key.toString())
                        vacanciesIds.add(responseData.child("vacancyId").value.toString())
                        responseStatuses.add(responseData.child("status").value.toString())
                    }
                }
                usersRef
                    .get()
                    .addOnSuccessListener { usersSnapshot ->
                        resumesRef
                            .get()
                            .addOnSuccessListener { resumesSnapshot ->
                                for (id in usersIds.indices) {
                                    for (userData in usersSnapshot) {
                                        if (userData.data["id"] == usersIds[id]) {
                                            for (resumeData in resumesSnapshot) {
                                                if (resumeData.data["studentId"] == userData.data["id"]) {
                                                    resumes.add(
                                                        ResumeModel(
                                                            id = resumeData.id,
                                                            studentId = resumeData.data["studentId"].toString(),
                                                            keySkills = resumeData.data["keySkills"].toString(),
                                                            salary = resumeData.data["salary"].toString(),
                                                            secondName = userData.data["secondName"].toString(),
                                                            firstName = userData.data["firstName"].toString(),
                                                            patronymicName = userData.data["patronymicName"].toString(),
                                                            birthDate = userData.data["birthDate"].toString(),
                                                            university = userData.data["university"].toString(),
                                                            institute = userData.data["institute"].toString(),
                                                            course = userData.data["course"].toString(),
                                                            phoneNumber = userData.data["phoneNumber"].toString(),
                                                            aboutMe = userData.data["aboutMe"].toString(),
                                                            gender = userData.data["gender"].toString(),
                                                            city = userData.data["city"].toString(),
                                                            direction = userData.data["direction"].toString(),
                                                            responseStatus = responseStatuses[id]
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                vacancyRef
                                    .get()
                                    .addOnSuccessListener { vacanciesSnapshot ->
                                        for (id in vacanciesIds) {
                                            for (vacancyData in vacanciesSnapshot) {
                                                if (id == vacancyData.id) {
                                                    vacancies.add(
                                                        VacancyModel(
                                                            id = vacancyData.id,
                                                            edArea = vacancyData.data["edArea"].toString(),
                                                            formOfEmployment = vacancyData.data["formOfEmployment"].toString(),
                                                            location = vacancyData.data["location"].toString(),
                                                            position = vacancyData.data["position"].toString(),
                                                            requirements = vacancyData.data["requirements"].toString(),
                                                            salary = vacancyData.data["salary"].toString()
                                                                .ifBlank { "0" }
                                                                .toInt(),
                                                            about = vacancyData.data["about"].toString()
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                        Log.d("MyTag", "${resumes.size} ${vacancies.size}")
                                        for (id in vacancies.indices) {
                                            responses.add(Pair(resumes[id], vacancies[id]))
                                        }
                                        onSuccessAction(responses)
                                    }
                            }
                    }
            }
    }
}

