// Incident.kt
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Incident(
    val id: String? = null,
    val title: String,
    val category: IncidentCategory = IncidentCategory.OTHER,
    val severity: IncidentSeverity = IncidentSeverity.LOW,
    val address: String? = null,
    val upvotes: Int = 0,
    @SerialName("created_date")
    val createdDate: String? = null
)

enum class IncidentCategory {
    THEFT,
    ASSAULT,
    HARASSMENT,
    VANDALISM,
    SUSPICIOUS_ACTIVITY,
    NATURAL_DISASTER,
    FIRE,
    ACCIDENT,
    OTHER
}

enum class IncidentSeverity {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}