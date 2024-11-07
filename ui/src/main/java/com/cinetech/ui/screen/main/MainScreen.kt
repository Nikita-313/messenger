package com.cinetech.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cinetech.ui.R
import com.cinetech.ui.theme.paddings
import com.cinetech.ui.theme.spacers

@Composable
fun MainScreen() {
    Scaffold(
        topBar = { TopBar(onNavigateClick = {}) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            items(20) {
                ChatItem(
                    title = "Инван Иванович",
                    message = "Как погода сегодня? Как погода сегодня? Как погода сегодня?",
                    date = "04.11.204",
                    notReadMessageCount = 4560,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
private fun ChatItem(
    title: String,
    message: String,
    date: String,
    notReadMessageCount: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = MaterialTheme.paddings.small, horizontal = MaterialTheme.paddings.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier.width(70.dp),
                    text = date,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = message,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (notReadMessageCount != 0)
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary)
                            .padding(vertical = 3.dp, horizontal = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = notReadMessageCount.toString(),
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimary, fontSize = 9.sp)
                        )
                    }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    onNavigateClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = MaterialTheme.paddings.medium),
                text = stringResource(R.string.main_screen_title),
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateClick,
                modifier = Modifier
                    .padding(start = MaterialTheme.paddings.medium)
                    .border(2.dp, shape = CircleShape, color = MaterialTheme.colorScheme.primary)
                    .border(3.dp, shape = CircleShape, color = MaterialTheme.colorScheme.background)
                    .size(40.dp)
            ) {
                Icon(
                    modifier = Modifier.size(27.dp).offset(y = (4).dp),
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

