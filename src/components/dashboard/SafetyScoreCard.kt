// SafetyScoreCard.kt
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SafetyScoreCard(
    score: Int = 72,
    trend: Int = 5,
    areaName: String = "Your Area"
) {

    val colors = getScoreColors(score)

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        Box(modifier = Modifier.padding(16.dp)) {

            // Background glow (blurred circle)
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .offset(x = 80.dp, y = (-40).dp)
                    .background(colors.bg.copy(alpha = 0.5f), shape = RoundedCornerShape(100.dp))
                    .blur(60.dp)
            )

            Column {

                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        Text(
                            text = "Safety Rating",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = areaName,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.alpha(0.7f)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(colors.bg, RoundedCornerShape(12.dp))
                            .padding(8.dp)
                    ) {
                        Text("🛡️", fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Animated Score
                val scale by animateFloatAsState(
                    targetValue = 1f,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                    label = "scoreScale"
                )

                Row(verticalAlignment = Alignment.Bottom) {

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .graphicsLayer(scaleX = scale, scaleY = scale)
                            .background(colors.bg, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = score.toString(),
                            fontSize = 28.sp,
                            color = colors.text
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.padding(bottom = 4.dp)) {

                        Text(
                            text = getScoreLabel(score),
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.text
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 4.dp)
                        ) {

                            val trendUp = trend > 0

                            Text(
                                text = if (trendUp) "📈" else "📉",
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = "${kotlin.math.abs(trend)}% from last week",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (trendUp)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- Helpers ---

data class ScoreColors(
    val bg: Color,
    val text: Color,
    val ring: Color
)

@Composable
fun getScoreColors(score: Int): ScoreColors {
    return when {
        score >= 80 -> ScoreColors(
            bg = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f),
            text = MaterialTheme.colorScheme.secondary,
            ring = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
        )

        score >= 60 -> ScoreColors(
            bg = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.15f),
            text = MaterialTheme.colorScheme.tertiary,
            ring = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)
        )

        score >= 40 -> ScoreColors(
            bg = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            text = MaterialTheme.colorScheme.primary,
            ring = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
        )

        else -> ScoreColors(
            bg = MaterialTheme.colorScheme.error.copy(alpha = 0.15f),
            text = MaterialTheme.colorScheme.error,
            ring = MaterialTheme.colorScheme.error.copy(alpha = 0.3f)
        )
    }
}

fun getScoreLabel(score: Int): String {
    return when {
        score >= 80 -> "Safe Zone"
        score >= 60 -> "Moderate"
        score >= 40 -> "Caution"
        else -> "High Risk"
    }
}