package com.cinetech.ui.screen.personal_area

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import com.cinetech.domain.model.User
import com.cinetech.ui.R
import com.cinetech.ui.theme.paddings
import com.cinetech.ui.theme.spacers

@Composable
fun PersonalAreaScreen(
    onPop: () -> Unit
) {
    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            TopBar(
                title = stringResource(R.string.personal_area_screen_title),
                isEditMod = false,
                onBackClick = onPop,
                onCanselEdit = {},
                onDoneEdit = {}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = MaterialTheme.paddings.large)
        ) {
            UserImage(isEditMod = false)
            Spacer(Modifier.height(MaterialTheme.spacers.large))
            Info(isEditMod = false)
        }
    }
}

@Composable
private fun UserImage(
    isEditMod: Boolean,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .clickable { }
                    .border(2.dp, shape = CircleShape, color = MaterialTheme.colorScheme.primary)
                    .border(3.dp, shape = CircleShape, color = MaterialTheme.colorScheme.background)
            ) {
                Icon(
                    modifier = Modifier
                        .size(150.dp)
                        .offset(y = (30).dp),
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            if (isEditMod)
                FloatingActionButton(
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(45.dp)
                        .align(Alignment.BottomEnd),
                    onClick = {},
                    containerColor = MaterialTheme.colorScheme.tertiary,
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(R.drawable.add_image_photo),
                        contentDescription = ""
                    )
                }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    title: String,
    isEditMod: Boolean,
    onBackClick: () -> Unit,
    onCanselEdit: () -> Unit,
    onDoneEdit: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = MaterialTheme.paddings.medium),
                text = title,
            )
        },
        navigationIcon = {
            if (!isEditMod) IconButton(onBackClick) { Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "") }
            else IconButton(onCanselEdit) { Icon(Icons.Filled.Clear, contentDescription = "") }
        },
        actions = {
            if (!isEditMod) IconButton(onBackClick) { Icon(Icons.Filled.Edit, contentDescription = "") }
            else IconButton(onDoneEdit) { Icon(Icons.Filled.Check, contentDescription = "") }
        }
    )
}

@Composable
private fun Info(
    user: User? = null,
    isEditMod: Boolean,
) {
    Column {
        Text(
            text = stringResource(R.string.personal_area_screen_user_info),
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
        )
        Spacer(Modifier.height(MaterialTheme.spacers.medium))

        InfoItem(
            title = stringResource(R.string.personal_area_screen_phone),
            value = "+7 979 555 5555",
            onValueChange = {},
            editable = false,
        )

        Spacer(Modifier.height(MaterialTheme.spacers.small))

        InfoItem(
            title = stringResource(R.string.personal_area_screen_nik_name),
            value = "Nikita_t_me",
            onValueChange = {},
            editable = false,
        )

        Spacer(Modifier.height(MaterialTheme.spacers.small))

        InfoItem(
            title = stringResource(R.string.personal_area_screen_about),
            value = "Вабба лабба даб даб!",
            onValueChange = {},
            editable = isEditMod,
        )

        Spacer(Modifier.height(MaterialTheme.spacers.small))

        InfoItem(
            title = stringResource(R.string.personal_area_screen_city),
            value = "Севастополь",
            onValueChange = {},
            editable = isEditMod,
        )

        Spacer(Modifier.height(MaterialTheme.spacers.small))

        InfoItem(
            title = "${stringResource(R.string.personal_area_screen_birthday)} (Козерог)",
            value = "11.01.1999",
            onValueChange = {},
            editable = isEditMod,
        )
    }
}

@Composable
private fun InfoItem(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    editable: Boolean
) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodySmall
    )
    Spacer(Modifier.height(MaterialTheme.spacers.small))
    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        enable = editable,
    )
}


@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    enable: Boolean
) {
    Box {
        BasicTextField(
            modifier = Modifier
                .height(25.dp)
                .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            enabled = enable,
            singleLine = true,
            textStyle = MaterialTheme.typography.titleMedium,
        )
        if (enable) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }
}