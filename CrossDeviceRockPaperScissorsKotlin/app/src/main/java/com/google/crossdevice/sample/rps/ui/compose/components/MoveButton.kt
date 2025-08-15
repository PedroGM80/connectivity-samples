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

package com.google.crossdevice.sample.rps.ui.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.crossdevice.sample.rps.R
import com.google.crossdevice.sample.rps.model.GameChoice

@Composable
fun MoveButtons(
    onMoveSelected: (GameChoice) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MoveButton(
            choice = GameChoice.ROCK,
            onClick = { onMoveSelected(GameChoice.ROCK) },
            enabled = enabled
        )
        
        MoveButton(
            choice = GameChoice.PAPER,
            onClick = { onMoveSelected(GameChoice.PAPER) },
            enabled = enabled
        )
        
        MoveButton(
            choice = GameChoice.SCISSORS,
            onClick = { onMoveSelected(GameChoice.SCISSORS) },
            enabled = enabled
        )
    }
}

@Composable
private fun MoveButton(
    choice: GameChoice,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val (iconRes, textRes) = when (choice) {
        GameChoice.ROCK -> R.drawable.rock to R.string.choice_rock
        GameChoice.PAPER -> R.drawable.paper to R.string.choice_paper
        GameChoice.SCISSORS -> R.drawable.scissors to R.string.choice_scissors
        else -> throw IllegalArgumentException("Unknown game choice")
    }
    
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(id = textRes),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
