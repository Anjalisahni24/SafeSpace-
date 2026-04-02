// Alert.kt
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Alert(
    val id: String? = null,
    val title: String,
    val message: String,
    val severity: AlertSeverity = AlertSeverity.INFO,
    @SerialName("created_date")
    val createdDate: String? = null
)