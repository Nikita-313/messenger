package com.cinetech.ui.screen.personal_area

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cinetech.domain.model.User
import com.cinetech.ui.R
import com.cinetech.ui.core.MaskVisualTransformation
import com.cinetech.ui.screen.personal_area.model.PersonalAreaUiEffect
import com.cinetech.ui.screen.personal_area.utils.getZodiacSignRId
import com.cinetech.ui.theme.paddings
import com.cinetech.ui.theme.spacers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalAreaScreen(
    viewModel: PersonalAreaViewModel = hiltViewModel(),
    onPop: () -> Unit,
) {

    val state by viewModel.state.collectAsState()

    val singlePhonePikerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = viewModel::onImageSelect
    )
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is PersonalAreaUiEffect.ShowToast -> {
                    Toast.makeText(context, context.getString(it.rId), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            TopBar(
                title = stringResource(R.string.personal_area_screen_title),
                isEditMode = state.isEditModeEnabled,
                onBackClick = onPop,
                isUpdateDataLoading = state.isUpdateDataLoading,
                isShowEdit = !state.isGetDataLoading && !state.isGetDataError,
                onCanselEdit = viewModel::canselEditMode,
                onStartEdit = viewModel::enableEditMode,
                onDoneEdit = viewModel::saveData,
            )
        }
    ) { paddingValues ->

        if (state.isGetDataError) {
            ErrorLoadingData(
                onRefresh = viewModel::getUserDataFromServer,
            )
        } else if (state.isGetDataLoading) {
            LoadingIndicator()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(horizontal = MaterialTheme.paddings.large)
            ) {
                UserImage(
                    userImage = state.userImage,
                    editUserImage = state.newUserImage,
                    isLoading = state.isUpdateDataLoading,
                    isEditMode = state.isEditModeEnabled,
                    onSelectImage = {
                        singlePhonePikerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                )
                Spacer(Modifier.height(MaterialTheme.spacers.large))
                UserInfo(
                    user = state.user,
                    editUser = state.editUser,
                    isEditMode = state.isEditModeEnabled,
                    isLoading = state.isUpdateDataLoading,
                    onDateClick = viewModel::showDatePiker,
                    onAboutChange = viewModel::onAboutChange,
                    onCityChange = viewModel::onCityChange
                )
            }
        }

        if (state.isShowDatePiker) {
            DatePiker(
                onDateSelected = viewModel::onDateSelected,
                onDismiss = viewModel::dismissDatePiker
            )
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(25.dp))
    }
}

@Composable
private fun ErrorLoadingData(
    onRefresh: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.personal_area_screen_get_data))
            IconButton(onClick = onRefresh) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "")
            }
        }
    }
}


@ExperimentalMaterial3Api
@Composable
private fun DatePiker(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })

    DatePickerDialog(
        modifier = Modifier.padding(MaterialTheme.paddings.medium),
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }
            ) {
                Text(text = stringResource(R.string.personal_area_screen_select))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = stringResource(R.string.personal_area_screen_cansel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@Composable
private fun UserImage(
    userImage: ImageBitmap?,
    editUserImage: ImageBitmap?,
    isEditMode: Boolean,
    isLoading: Boolean,
    onSelectImage: () -> Unit,
) {

    val image = if (isEditMode) editUserImage else userImage

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {

            if (image != null) {
                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .border(2.dp, shape = CircleShape, color = MaterialTheme.colorScheme.primary)
                        .border(3.dp, shape = CircleShape, color = MaterialTheme.colorScheme.background),
                    bitmap = image,
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
            } else DefaultImage()

            if (isEditMode && !isLoading)
                FloatingActionButton(
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(45.dp)
                        .align(Alignment.BottomEnd),
                    onClick = onSelectImage,
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


@Composable
private fun DefaultImage() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    title: String,
    isEditMode: Boolean,
    isUpdateDataLoading: Boolean,
    isShowEdit: Boolean,
    onBackClick: () -> Unit,
    onCanselEdit: () -> Unit,
    onStartEdit: () -> Unit,
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
            if (!isUpdateDataLoading) IconButton({}) { Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "", tint = Color.Transparent) }
            if (!isEditMode) IconButton(onBackClick) { Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "") }
            else IconButton(onCanselEdit) { Icon(Icons.Filled.Clear, contentDescription = "") }
        },
        actions = {
            if (isShowEdit) {
                if (isUpdateDataLoading) CircularProgressIndicator(
                    strokeWidth = 2.dp, modifier = Modifier
                        .padding(end = 20.dp)
                        .size(20.dp)
                )
                else if (!isEditMode) IconButton(onStartEdit) { Icon(Icons.Filled.Edit, contentDescription = "") }
                else IconButton(onDoneEdit) { Icon(Icons.Filled.Check, contentDescription = "") }
            }
        }
    )
}

@Composable
private fun UserInfo(
    user: User?,
    editUser: User?,
    isEditMode: Boolean,
    isLoading: Boolean,
    onDateClick: () -> Unit,
    onAboutChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
) {

    val userShowData = if (isEditMode) editUser else user

    if (userShowData == null) return

    val phoneMask = when (userShowData.phone.length) {
        11 -> "# ### ### ####"
        12 -> "## ### ### ####"
        else -> "### ### ### ####"
    }

    Column {
        Text(
            text = stringResource(R.string.personal_area_screen_user_info),
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
        )
        Spacer(Modifier.height(MaterialTheme.spacers.medium))

        InfoItem(
            title = stringResource(R.string.personal_area_screen_phone),
            value = "+${MaskVisualTransformation(phoneMask).filter(AnnotatedString(userShowData.phone)).text}",
            onValueChange = {},
            editable = false,
        )

        Spacer(Modifier.height(MaterialTheme.spacers.small))

        InfoItem(
            title = stringResource(R.string.personal_area_screen_nik_name),
            value = userShowData.username,
            onValueChange = {},
            editable = false,
        )

        Spacer(Modifier.height(MaterialTheme.spacers.small))

        InfoItem(
            title = stringResource(R.string.personal_area_screen_about),
            value = userShowData.status,
            onValueChange = onAboutChange,
            editable = isEditMode && !isLoading,
        )

        Spacer(Modifier.height(MaterialTheme.spacers.small))

        InfoItem(
            title = stringResource(R.string.personal_area_screen_city),
            value = userShowData.city,
            onValueChange = onCityChange,
            editable = isEditMode && !isLoading,
        )

        Spacer(Modifier.height(MaterialTheme.spacers.small))

        val zodiac = getZodiacSignRId(userShowData.birthday)
        val zodiacFormat = if (zodiac == null) "" else "(${stringResource(zodiac)})"

        DateItem(
            title = "${stringResource(R.string.personal_area_screen_birthday)} $zodiacFormat",
            value = userShowData.birthday,
            onClick = onDateClick,
            editable = isEditMode && !isLoading,
        )
    }
}


@Composable
private fun DateItem(
    title: String,
    value: String?,
    editable: Boolean,
    onClick: () -> Unit
) {

    Text(
        text = title,
        style = MaterialTheme.typography.bodySmall
    )
    Spacer(Modifier.height(MaterialTheme.spacers.small))
    CustomTextField(
        modifier = Modifier.clickable(
            enabled = editable,
            onClick = onClick
        ),
        value = value,
        onValueChange = {},
        enable = editable,
        readOnly = true
    )
}

@Composable
private fun InfoItem(
    title: String,
    value: String?,
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
    modifier: Modifier = Modifier,
    value: String?,
    onValueChange: (String) -> Unit,
    enable: Boolean,
    readOnly: Boolean = false,
) {
    Box {
        if (value == null && !enable) {
            Text(
                text = stringResource(R.string.personal_area_screen_no_data),
                style = MaterialTheme.typography.titleMedium
            )
        }

        if (readOnly) {
            Text(
                modifier = modifier
                    .height(25.dp)
                    .fillMaxWidth(),
                text = value ?: "",
                style = MaterialTheme.typography.titleMedium
            )
        } else {
            BasicTextField(
                modifier = modifier
                    .height(25.dp)
                    .fillMaxWidth(),
                value = value ?: "",
                onValueChange = onValueChange,
                enabled = enable,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text,
                ),
                textStyle = MaterialTheme.typography.titleMedium,
            )
        }
        if (enable) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }
}

