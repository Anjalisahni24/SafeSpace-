// QuickActions.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun QuickActions(
    navController: NavController
) {

    val actions = listOf(
        QuickAction("/sos", "SOS Alert", "Emergency help", ActionType.SOS),
        QuickAction("/safety-map", "Safety Map", "View risk zones", ActionType.SAFETY_MAP),
        QuickAction("/report", "Report", "File incident", ActionType.REPORT),
        QuickAction("/contacts", "Contacts", "Emergency list", ActionType.CONTACTS),
        QuickAction("/safety-map", "Police Station", "Find nearest", ActionType.POLICE),
        QuickAction("/safety-map", "Disaster Alert", "Check warnings", ActionType.DISASTER),
    )

    Column {
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Grid Layout
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            actions.chunked(2).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowItems.forEach { action ->
                        QuickActionItem(
                            action = action,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                navController.navigate(action.route)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionItem(
    action: QuickAction,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val (bgColor, iconBgColor, icon) = getActionStyle(action.type)

    Card(
        modifier = modifier
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            // Icon Box
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(iconBgColor, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = action.label,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = action.description,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.alpha(0.7f)
            )
        }
    }
}

// --- Styling Logic ---

@Composable
fun getActionStyle(type: ActionType): Triple<Color, Color, String> {
    return when (type) {

        ActionType.SOS -> Triple(
            MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.error.copy(alpha = 0.2f),
            "🚨"
        )

        ActionType.SAFETY_MAP -> Triple(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            "📍"
        )

        ActionType.REPORT -> Triple(
            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
            "📄"
        )

        ActionType.CONTACTS -> Triple(
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
            "📞"
        )

        ActionType.POLICE -> Triple(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
            "🧭"
        )

        ActionType.DISASTER -> Triple(
            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
            "⚡"
        )
    }
}