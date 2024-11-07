package com.cinetech.ui.screen.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cinetech.ui.screen.chat.utils.FriendMessageWithTailShape
import com.cinetech.ui.screen.chat.utils.UserMessageWithTailShape
import com.cinetech.ui.theme.paddings

@Composable
fun ChatScreen(
    onPop: () -> Unit,
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopBar(title = "Иван Иванович", onBackClick = onPop)
            LazyColumn(
                modifier = Modifier.weight(1f),
                reverseLayout = true
            ) {

                item {

                    MyMassage("Привет, как погода? ", UserMessageWithTailShape(topEnd = CornerSize(10.dp)))
                    MyMassage("Привет, как погода?",  RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp, topEnd = 15.dp, bottomEnd = 5.dp))

                    FriendMassage("Привет, как погода? Hello, world! Hello, world!")
                    MyMassage("Привет, как погода?")

                    FriendMassage("Привет, как погода? Hello, world! Hello, world!", FriendMessageWithTailShape(topStart = CornerSize(10.dp)))
                    FriendMassage("Привет, как погода? Hello, world! Hello, world!", RoundedCornerShape(topStart = 15.dp, bottomStart = 5.dp, topEnd = 15.dp, bottomEnd = 15.dp))

                    MyMassage("Привет, как погода?", UserMessageWithTailShape(topEnd = CornerSize(10.dp)))
                    MyMassage("Привет, как погода?",  RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp, topEnd = 15.dp, bottomEnd = 5.dp))

                    FriendMassage("Привет, как погода?")
                    MyMassage("Привет, как погода?")

                    FriendMassage("Привет, как погода?", FriendMessageWithTailShape(topStart = CornerSize(10.dp)))
                    FriendMassage("Привет, как погода?", RoundedCornerShape(topStart = 15.dp, bottomStart = 5.dp, topEnd = 15.dp, bottomEnd = 15.dp))

                    MyMassage("Привет, как погода? ", UserMessageWithTailShape(topEnd = CornerSize(10.dp)))
                    MyMassage("Привет, как погода?",  RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp, topEnd = 15.dp, bottomEnd = 5.dp))

                    FriendMassage("Привет, как погода? Hello, world! Hello, world!")
                    MyMassage("Привет, как погода?")

                    FriendMassage("Привет, как погода? Hello, world! Hello, world!", FriendMessageWithTailShape(topStart = CornerSize(10.dp)))
                    FriendMassage("Привет, как погода? Hello, world! Hello, world!", RoundedCornerShape(topStart = 15.dp, bottomStart = 5.dp, topEnd = 15.dp, bottomEnd = 15.dp))

                    MyMassage("Привет, как погода?", UserMessageWithTailShape(topEnd = CornerSize(10.dp)))
                    MyMassage("Привет, как погода?",  RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp, topEnd = 15.dp, bottomEnd = 5.dp))

                    FriendMassage("Привет, как погода?")
                    MyMassage("Привет, как погода?")

                    FriendMassage("Привет, как погода?", FriendMessageWithTailShape(topStart = CornerSize(10.dp)))
                    FriendMassage("Привет, как погода?", RoundedCornerShape(topStart = 15.dp, bottomStart = 5.dp, topEnd = 15.dp, bottomEnd = 15.dp))
                }
            }
            ChatTextField()
        }
    }
}


@Composable
fun ChatTextField() {

    var text by remember { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .imePadding()
            .fillMaxWidth(),
        placeholder = { Text(text = "Сообщение") },
        value = text,
        onValueChange = { text = it },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Send
        ),
        shape = RoundedCornerShape(0.dp),
        trailingIcon = {
            Row {
                IconButton(
                    onClick = {},
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = ""
                        )
                    }
                )
            }
        },
    )
}

@Composable
fun MyMassage(massage: String, shape: Shape = UserMessageWithTailShape()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 10.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Massage(
            massage = massage,
            color = MaterialTheme.colorScheme.primary,
            shape = shape,
        )
    }
}

@Composable
fun FriendMassage(massage: String, shape: Shape = FriendMessageWithTailShape()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 40.dp, start = 10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Massage(
            massage = massage,
            color = MaterialTheme.colorScheme.secondary,
            shape = shape,
        )
    }
}

@Composable
fun Massage(massage: String, color: Color, shape: Shape) {

    Card(
        modifier = Modifier.padding(top = 0.dp, bottom = 5.dp),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
    ) {
        Text(
            massage,
            color = MaterialTheme.colorScheme.onPrimary,
            lineHeight = 20.sp,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    onBackClick: () -> Unit,
    title: String
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = MaterialTheme.paddings.medium),
                text = title,
            )
        },
        navigationIcon = { IconButton(onBackClick) { Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "") } },
    )
}