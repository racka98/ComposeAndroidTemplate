package com.apimorlabs.composetemplate.app.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apimorlabs.composetemplate.app.ui.theme.ComposeAndroidTemplateTheme

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAndroidTemplateTheme {
        Greeting("Android")
    }
}
