/*
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.crossdevice.sample.rps.ui.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.crossdevice.sample.rps.R
import com.google.crossdevice.sample.rps.model.GameChoice
import com.google.crossdevice.sample.rps.service.DiscoveryTwoPlayerGameManager
import com.google.crossdevice.sample.rps.service.GameManager
import com.google.crossdevice.sample.rps.ui.compose.screens.TwoPlayerGameScreen
import com.google.crossdevice.sample.rps.ui.theme.CrossDeviceRPSTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Activity for playing a two-player Rock Paper Scissors game with opponent using a "Discovery
 * API"-based GameManager, implemented with Jetpack Compose.
 */
class DiscoveryTwoPlayerActivity : ComponentActivity() {
    private lateinit var gameManager: GameManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        gameManager = DiscoveryTwoPlayerGameManager(this, lifecycleScope)
        
        setContent {
            CrossDeviceRPSTheme {
                DiscoveryTwoPlayerScreen(
                    gameManager = gameManager,
                    onNewIntent = { intent ->
                        handleIntent(intent)
                    }
                )
            }
        }
        
        handleIntent(intent)
    }
    
    override fun onDestroy() {
        // Clean up and stop all connections
        gameManager.disconnect()
        super.onDestroy()
    }
    
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // This will trigger the LaunchedEffect in DiscoveryTwoPlayerScreen
        setIntent(intent)
    }
    
    /** Handles incoming requests to this activity. */
    private fun handleIntent(intent: Intent) {
        if (DiscoveryTwoPlayerGameManager.ACTION_WAKE_UP == intent.action) {
            // Attempt to open up a connection with the participant
            gameManager.acceptGameInvitation(intent)
        }
    }
}

@Composable
private fun DiscoveryTwoPlayerScreen(
    gameManager: GameManager,
    onNewIntent: (intent: Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    // State from GameManager
    val playerName by gameManager.playerName.collectAsState(initial = "")
    val opponentName by gameManager.opponentName.collectAsState(initial = null)
    val status by gameManager.status.collectAsState(initial = "")
    val score by gameManager.score.collectAsState(initial = "")
    val isConnected by gameManager.isConnected.collectAsState(initial = false)
    val isSearching by gameManager.isSearching.collectAsState(initial = false)
    
    // Local state for the current intent
    var currentIntent by remember { mutableStateOf<Intent?>(null) }
    
    // Handle new intents
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // This will trigger whenever the intent changes
                context.packageManager.getLaunchIntentForPackage(context.packageName)?.let { intent ->
                    currentIntent = intent
                    onNewIntent(intent)
                }
            }
        }
    }
    
    // Observe game messages
    LaunchedEffect(Unit) {
        gameManager.messages.collectLatest { message ->
            // Show toast for game messages
            android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
        }
    }
    
    TwoPlayerGameScreen(
        playerName = playerName,
        opponentName = opponentName,
        status = status,
        score = score,
        isConnected = isConnected,
        isSearching = isSearching,
        onFindOpponent = {
            gameManager.findOpponent()
        },
        onDisconnect = {
            gameManager.disconnect()
        },
        onMoveSelected = { choice ->
            gameManager.makeMove(choice)
        },
        modifier = modifier
    )
}
