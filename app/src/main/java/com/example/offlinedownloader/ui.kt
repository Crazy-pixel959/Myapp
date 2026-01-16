
package com.example.offlinedownloader.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    var tab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf("Home","Downloads","Settings").forEachIndexed { i, t ->
                    NavigationBarItem(
                        selected = tab == i,
                        onClick = { tab = i },
                        label = { Text(t) },
                        icon = {}
                    )
                }
            }
        }
    ) { p ->
        Column(Modifier.padding(p).padding(16.dp)) {
            when(tab) {
                0 -> Home()
                1 -> Text("Download Queue")
                2 -> Text("Settings")
            }
        }
    }
}

@Composable
fun Home() {
    var url by remember { mutableStateOf("") }
    OutlinedTextField(
        value = url,
        onValueChange = { url = it },
        label = { Text("Paste link") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(16.dp))
    Button(onClick = {}) { Text("Download") }
}
