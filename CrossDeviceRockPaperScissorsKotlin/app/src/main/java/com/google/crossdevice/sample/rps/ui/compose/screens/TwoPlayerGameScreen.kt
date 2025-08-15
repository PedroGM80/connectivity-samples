
package com.google.crossdevice.sample.rps.ui.compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Estado de la partida
                GameStatus(
                    playerName = playerName,
                    opponentName = opponentName,
                    status = status,
                    modifier = Modifier.fillMaxWidth()
                )

                // Separa para centrar verticalmente el marcador
                Spacer(modifier = Modifier.weight(1f))

                // Marcador
                Text(
                    text = score,
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                // Botones de movimiento
                MoveButtons(
                    onMoveSelected = onMoveSelected,
                    enabled = isConnected && !isSearching,
                    modifier = Modifier.fillMaxWidth()
                )

                // Botones de acción
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
                                id = if (isSearching)
                                    R.string.status_searching
                                else
                                    R.string.action_find_opponents
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

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
