package com.kold.koldcomposeweek1

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kold.koldcomposeweek1.ui.theme.KoldComposeWeek1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KoldComposeWeek1Theme {
                KoldApp()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
       CardContent(name = name)
    }
}

@Composable
private fun CardContent(name: String) {
    val expanded = rememberSaveable { mutableStateOf(false) }
//    val extraPadding by animateDpAsState(
//        targetValue = if (expanded.value) 48.dp else 0.dp,
//        animationSpec = spring(                             // 용수철 효과
//            dampingRatio = Spring.DampingRatioMediumBouncy, // 진동감폭(?)
//            stiffness = Spring.StiffnessLow                 // 뻣뻣함(?)
//        )
//    )

    Row(
        modifier = Modifier
            .padding(24.dp)
            .animateContentSize(
                animationSpec = spring(                             // 용수철 효과
                    dampingRatio = Spring.DampingRatioMediumBouncy, // 진동감폭(?)
                    stiffness = Spring.StiffnessLow                 // 뻣뻣함(?)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
//                .padding(bottom = extraPadding.coerceAtLeast(0.dp))     // 값을 강제함. (coerce)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name,
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold)
            )

            if (expanded.value) {
                Text(
                    text = ("Composem ipsum color sit lazy, pading theme elit, sed do bouncy.").repeat(4)
                )
            }
        }

        IconButton(
            onClick = { expanded.value = !expanded.value },
        ) {
            Icon(
                imageVector = if (expanded.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded.value) {
                    stringResource(id = R.string.show_less)
                } else {
                    stringResource(id = R.string.show_more)
                }
            )
        }
    }
}

@Composable
private fun KoldApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    if (shouldShowOnboarding) {
        OnboardingScreen {
            shouldShowOnboarding = false
        }
    } else {
        Greetings()
    }
}

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name="DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    KoldComposeWeek1Theme {
        Greetings()
    }
}

@Composable
fun OnboardingScreen(onClick: (() -> Unit)) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onClick
            ) {
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    KoldComposeWeek1Theme {
        KoldApp()
    }
}