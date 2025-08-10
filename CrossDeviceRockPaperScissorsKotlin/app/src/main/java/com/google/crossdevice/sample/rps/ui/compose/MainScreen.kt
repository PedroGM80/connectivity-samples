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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.crossdevice.sample.rps.R

@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            GameButton(
                text = stringResource(id = R.string.action_two_player_discovery_api),
                onClick = { navController.navigate("discovery_two_player") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            GameButton(
                text = stringResource(id = R.string.action_two_player_sessions_api),
                onClick = { navController.navigate("sessions_two_player") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            GameButton(
                text = stringResource(id = R.string.action_single_player_sessions_api),
                onClick = { navController.navigate("sessions_single_player") }
            )
            
            // Disabled as per original implementation
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { /* Do nothing */ },
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.action_multiplayer_sessions_api))
            }
        }
    }
}

@Composable
private fun GameButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }
}

private val Modifier.fillMaxWidth: Modifier
    get() = this.then(Modifier.fillMaxWidth())
