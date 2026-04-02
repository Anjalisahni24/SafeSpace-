
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.*
import java.time.format.DateTimeFormatter
import java.time.OffsetDateTime

@Composable
fun AlertsFeed(
    alerts: List<Alert> = emptyList()
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            if (alerts.isEmpty()) {
                Text(
                    text = "Active Alerts",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🛡️",
                        fontSize = 40.sp,
                        modifier = Modifier.alpha(0.5f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No active alerts in your area",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "You're in a safe zone",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

            } else {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Active Alerts",
                        style = MaterialTheme.typography.titleMedium
                    )

                    AssistChip(
                        onClick = {},
                        label = { Text("${alerts.size} active") }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    itemsIndexed(alerts) { index, alert ->
                        AlertItem(alert = alert, index = index)
                    }
                }
            }
        }
    }
}

@Composable
fun AlertItem(alert: Alert, index: Int) {

    val (backgroundColor, borderColor, icon) = getSeverityConfig(alert.severity)

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {

            Text(
                text = icon,
                fontSize = 18.sp,
                modifier = Modifier.padding(end = 8.dp)
            )

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = alert.title,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = alert.message,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    modifier = Modifier.alpha(0.8f)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "⏱",
                        fontSize = 12.sp,
                        modifier = Modifier.alpha(0.6f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = formatDate(alert.createdDate),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.alpha(0.6f)
                    )
                }
            }
        }
    }
}

// --- Helpers ---

@Composable
fun getSeverityConfig(severity: AlertSeverity): Triple<Color, Color, String> {
    return when (severity) {
        AlertSeverity.INFO -> Triple(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.primary,
            "🛡️"
        )

        AlertSeverity.WARNING -> Triple(
            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.tertiary,
            "⚠️"
        )

        AlertSeverity.DANGER -> Triple(
            MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.error,
            "⚠️"
        )

        AlertSeverity.CRITICAL -> Triple(
            MaterialTheme.colorScheme.error.copy(alpha = 0.15f),
            MaterialTheme.colorScheme.error,
            "⚡"
        )
    }
}

fun formatDate(date: String?): String {
    return try {
        date?.let {
            val parsed = OffsetDateTime.parse(it)
            val formatter = DateTimeFormatter.ofPattern("MMM d, h:mm a")
            parsed.format(formatter)
        } ?: "Just now"
    } catch (e: Exception) {
        "Just now"
    }
}