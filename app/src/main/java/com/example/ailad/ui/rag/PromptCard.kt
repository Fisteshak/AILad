package com.example.ailad.ui.rag


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ailad.R
import com.example.ailad.entities.Prompt

@Composable
fun PromptCard(
    prompt: Prompt,
    onRunClick: (Int) -> Unit,
    onLoadClick: (Int) -> Unit,
    onFavoriteClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .width(320.dp)
                .padding(16.dp) // Add padding for better visual spacing
        ) {
            Text(
                text = prompt.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(3.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.baseline_play_arrow_24
                    ),
                    contentDescription = "Run Prompt",
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .clickable { onRunClick(prompt.id) }
                )
                Image(
                    painter = painterResource(
                        id = R.drawable.variable_insert_24px
                    ),
                    contentDescription = "Load Prompt",
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .clickable { onLoadClick(prompt.id) }
                )

                Spacer(Modifier.weight(1f))

                Image(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .clickable { onEditClick() }
                )

                Image(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .clickable { onDeleteClick() }
                )

                Image(
                    painter = painterResource(
                        id = if (prompt.isFavorite) R.drawable.star_filled_yellow else R.drawable.star_outlined_yellow
                    ),
                    contentDescription = "is favorite",
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable { onFavoriteClick() }
                )
            }
        }
    }


}