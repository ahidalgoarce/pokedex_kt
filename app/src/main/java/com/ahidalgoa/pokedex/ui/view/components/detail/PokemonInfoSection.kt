package com.ahidalgoa.pokedex.ui.view.components.detail

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ahidalgoa.pokedex.domain.model.PokemonDetail
import com.ahidalgoa.pokedex.ui.view.common.TypeChip
import com.ahidalgoa.pokedex.ui.view.common.typeColors
@Composable
fun PokemonInfoSection(pokemon: PokemonDetail) {
    val mediaPlayer = remember(pokemon.id) { MediaPlayer() }
    var isPlaying by remember { mutableStateOf(false) }

    val mainTypeColor = pokemon.types.firstOrNull()
        ?.let { typeColors[it.lowercase()] }
        ?: MaterialTheme.colorScheme.primary

    DisposableEffect(pokemon.id) {
        mediaPlayer.setOnCompletionListener {
            isPlaying = false
            it.reset()
        }
        onDispose { mediaPlayer.release() }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🟢 Nombre
            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🟢 Types + Cry
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                pokemon.types.forEach { type ->
                    TypeChip(type = type)
                }

                IconButton(onClick = {
                    pokemon.cryUrl?.let { url ->
                        try {
                            if (isPlaying) {
                                mediaPlayer.stop()
                                mediaPlayer.reset()
                                isPlaying = false
                            } else {
                                mediaPlayer.setDataSource(url)
                                mediaPlayer.prepareAsync()
                                mediaPlayer.setOnPreparedListener {
                                    it.start()
                                    isPlaying = true
                                }
                            }
                        } catch (e: Exception) {
                            isPlaying = false
                            mediaPlayer.reset()
                        }
                    }
                }) {
                    Icon(
                        imageVector = if (isPlaying)
                            Icons.Filled.Clear
                        else
                            Icons.Default.PlayArrow,
                        contentDescription = "Play Cry",
                        tint = mainTypeColor,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = pokemon.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MeasurementCard(
                    label = "WEIGHT",
                    value = "${pokemon.weight} kg",
                    modifier = Modifier.weight(1f)
                )
                MeasurementCard(
                    label = "HEIGHT",
                    value = "${pokemon.height} m",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}