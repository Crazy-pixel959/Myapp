package com.example.offlinedownloader

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentTab by remember { mutableStateOf(0) }
            var urlInput by remember { mutableStateOf("") }

            MaterialTheme(colorScheme = darkColorScheme()) {
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentTab == 0,
                                onClick = { currentTab = 0 },
                                icon = { Icon(Icons.Default.Home, null) },
                                label = { Text("Home") }
                            )
                            NavigationBarItem(
                                selected = currentTab == 1,
                                onClick = { currentTab = 1 },
                                icon = { Icon(Icons.Default.Language, null) },
                                label = { Text("Browser") }
                            )
                        }
                    }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        if (currentTab == 0) {
                            DownloadScreen(urlInput) { urlInput = it }
                        } else {
                            BrowserScreen { capturedUrl ->
                                urlInput = capturedUrl
                                currentTab = 0
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DownloadScreen(url: String, onUrlChange: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text("Downloader", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.height(32.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(24.dp)) {
                OutlinedTextField(
                    value = url,
                    onValueChange = onUrlChange,
                    label = { Text("URL Link") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                    Text("Download Now")
                }
            }
        }
    }
}

@Composable
fun BrowserScreen(onUrlCaptured: (String) -> Unit) {
    var webView: WebView? by remember { mutableStateOf(null) }
    BackHandler(enabled = webView?.canGoBack() == true) { webView?.goBack() }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    loadUrl("https://www.google.com")
                    webView = this
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        Button(
            onClick = { webView?.url?.let { onUrlCaptured(it) } },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        ) {
            Text("Grab Link")
        }
    }
}
