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

package com.google.crossdevice.sample.rps.ui.compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.crossdevice.sample.rps.R
import com.google.crossdevice.sample.rps.model.GameChoice
import com.google.crossdevice.sample.rps.ui.compose.components.GameStatus
import com.google.crossdevice.sample.rps.ui.compose.components.MoveButtons

@Composable
fun TwoPlayerGameScreen(
    playerName: String,
    opponentName: String?,
    status: String,
    score: String,
    isConnected: Boolean,
    isSearching: Boolean,
    onFindOpponent: () -> Unit,
    onDisconnect: () -> Unit,
    onMoveSelected: (GameChoice) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold { paddingValues ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Game Status
                GameStatus(
                    playerName = playerName,
                    opponentName = opponentName,
                    status = status,
                    modifier = Modifier.fillMaxWidth()
                )

                // Score Display
                Text(
                    text = score,
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 16.dp)
                )

                // Move Buttons
                MoveButtons(
                    onMoveSelected = onMoveSelected,
                    enabled = isConnected && !isSearching,
                    modifier = Modifier.fillMaxWidth()
                )

                // Action Buttons
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onFindOpponent,
                        enabled = !isConnected && !isSearching,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text(
                            text = stringResource(
                                id = if (isSearching) R.string.status_searching 
                                    else R.string.action_find_opponents
                            )
                        )
                    }

                    Button(
                        onClick = onDisconnect,
                        enabled = isConnected || isSearching,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    ) {
                        Text(text = stringResource(R.string.action_disconnect))
                    }
                }
            }
        }
    }
}
