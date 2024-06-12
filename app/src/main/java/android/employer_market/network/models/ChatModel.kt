package android.employer_market.network.models

data class ChatModel(
    val vacancyId: String,
    val vacancyName: String,
    val studentId: String,
    val studentName: String,
    val companyId: String,
    val companyName: String,
    val chatId: String = studentId+companyId+vacancyId,
    val messages: List<MessageModel> = listOf(),
)