// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import gov.nasa.worldwind.BasicModel
import gov.nasa.worldwind.awt.WorldWindowGLCanvas
import javax.swing.BoxLayout
import javax.swing.JPanel

fun main() = application {
    androidx.compose.ui.window.Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

@Composable
@Preview
fun App() {
    DesktopMaterialTheme {
        Row {
            SwingPanel(
                modifier = Modifier.fillMaxSize().weight(1f),
                factory = {
                    JPanel().apply {
                        layout = BoxLayout(this, BoxLayout.Y_AXIS)
                        val ww = WorldWindowGLCanvas().apply {
                            model = BasicModel()
                        }
                        add(ww)
                    }
                }
            )
            Column(Modifier.width(200.dp).fillMaxWidth()) {
                val textFieldState = remember { mutableStateOf("") }
                val focusManager = LocalFocusManager.current

                TextField(
                    value = textFieldState.value,
                    onValueChange = { textFieldState.value = it },
                    label = { Text("TextField") }
                )

                Spacer(Modifier.height(4.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { focusManager.clearFocus() }
                ) {
                    Text("Clear focus")
                }
            }
        }
    }
}