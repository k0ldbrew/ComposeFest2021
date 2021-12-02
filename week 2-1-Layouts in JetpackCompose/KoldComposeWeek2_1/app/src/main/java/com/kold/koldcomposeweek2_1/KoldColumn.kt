package com.kold.koldcomposeweek2_1

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kold.koldcomposeweek2_1.ui.theme.KoldComposeWeek2_1Theme

/**
 * 전달받은 Dp에서 첫번째줄 높이만큼 뺀 값을 padding으로 정한다.
 * */
fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        // 먼저 composable 크기를 측정한다. (한번만 가능)
        val placeable = measurable.measure(constraints)

        // Check the composable has a first baseline
        // FirstBaseline: the baseline of a first line.
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)

        // Height of the composable with padding - first baseline
        val firstBaseline = placeable[FirstBaseline]
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            // Where the composable gets placed
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Preview(showBackground = true, widthDp = 320)
@Composable
fun TextWithPaddingToBaselinePreview() {
    KoldComposeWeek2_1Theme {
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun TextWithNormalPaddingPreview() {
    KoldComposeWeek2_1Theme {
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }
}

/**
 * Column의 내부 동작방식을 이해한다.
 * */
@Composable
fun KoldColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        var yPosition = 0

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun Content(modifier: Modifier = Modifier) {
    KoldColumn(modifier.padding(8.dp)) {
        Text("MyOwnColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }
}
