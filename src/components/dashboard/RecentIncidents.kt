// RecentIncidents.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Composable
fun RecentIncidents(
    incidents: List<Incident> = emptyList(),
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Recent Incidents",
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = Modifier
                        .clickable { navController.navigate("/safety-map") },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "View all",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(" ➜", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (incidents.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No recent incidents reported",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

            } else {

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    incidents.take(5).forEachIndexed { index, incident ->
                        IncidentItem(incident = incident)
                    }
                }
            }
        }
    }
}

@Composable
fun IncidentItem(incident: Incident) {

    val icon = getCategoryIcon(incident.category)
    val severityColor = getSeverityColor(incident.severity)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {

        Text(
            text = icon,
            fontSize = 20.sp,
            modifier = Modifier.padding(end = 8.dp)
        )

        Column(modifier = Modifier.weight(1f)) {

            Text(
                text = incident.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                AssistChip(
                    onClick = {},
                    label = { Text(incident.severity.name.lowercase()) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = severityColor.copy(alpha = 0.1f)
                    )
                )

                incident.address?.let {
                    Text(
                        text = "📍 $it",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                Text(
                    text = "⏱ ${formatIncidentDate(incident.createdDate)}",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.alpha(0.7f)
                )

                Text(
                    text = "👍 ${incident.upvotes}",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.alpha(0.7f)
                )
            }
        }
    }
}

// --- Helpers ---

fun getCategoryIcon(category: IncidentCategory): String {
    return when (category) {
        IncidentCategory.THEFT -> "🔓"
        IncidentCategory.ASSAULT -> "⚠️"
        IncidentCategory.HARASSMENT -> "🚫"
        IncidentCategory.VANDALISM -> "💥"
        IncidentCategory.SUSPICIOUS_ACTIVITY -> "👁️"
        IncidentCategory.NATURAL_DISASTER -> "🌊"
        IncidentCategory.FIRE -> "🔥"
        IncidentCategory.ACCIDENT -> "🚗"
        IncidentCategory.OTHER -> "📋"
    }
}

@Composable
fun getSeverityColor(severity: IncidentSeverity): Color {
    return when (severity) {
        IncidentSeverity.LOW -> MaterialTheme.colorScheme.secondary
        IncidentSeverity.MEDIUM -> MaterialTheme.colorScheme.tertiary
        IncidentSeverity.HIGH -> MaterialTheme.colorScheme.primary
        IncidentSeverity.CRITICAL -> MaterialTheme.colorScheme.error
    }
}

fun formatIncidentDate(date: String?): String {
    return try {
        date?.let {
            val parsed = OffsetDateTime.parse(it)
            val formatter = DateTimeFormatter.ofPattern("MMM d, h:mm a")
            parsed.format(formatter)
        } ?: "Recently"
    } catch (e: Exception) {
        "Recently"
    }
}