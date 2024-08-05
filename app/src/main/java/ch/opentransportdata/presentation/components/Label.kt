package ch.opentransportdata.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Composable
fun Label(
    modifier: Modifier = Modifier,
    type: LabelType = LabelType.GREEN,
    icon: ImageVector? = null,
    text: String? = null,
) {
    val contentColor = when (type) {
        LabelType.GREEN -> Color.Green
        LabelType.BLUE -> MaterialTheme.colorScheme.primary
        LabelType.RED -> MaterialTheme.colorScheme.error
        LabelType.ORANGE -> Color(0xFFFFA500)
    }

    Surface(
        modifier = modifier,
        shape = CircleShape,
        border = BorderStroke(1.dp, contentColor),
        color = contentColor.copy(alpha = 0.1f),
        contentColor = contentColor
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.let {
                Icon(
                    modifier = Modifier
                        .padding(end = if (text.isNullOrBlank()) 0.dp else 4.dp)
                        .size(12.dp),
                    imageVector = it,
                    contentDescription = null,
                )
            }
            text?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


enum class LabelType {
    GREEN,
    BLUE,
    RED,
    ORANGE,
}

@PreviewLightDark
@Composable
private fun BadgeLabelGreenWithIconPreview() {
    OJPAndroidSDKTheme {
        Label(
            type = LabelType.GREEN,
            icon = Icons.Default.ThumbUp,
            text = "Congratulation"
        )
    }
}

@PreviewLightDark
@Composable
private fun BadgeLabelGreenWithoutIconPreview() {
    OJPAndroidSDKTheme {
        Label(
            type = LabelType.GREEN,
            text = "Congratulation"
        )
    }
}

@PreviewLightDark
@Composable
private fun OnlyIconPreview() {
    OJPAndroidSDKTheme {
        Label(
            type = LabelType.BLUE,
            icon = Icons.Default.DateRange
        )
    }
}