package com.cinetech.ui.screen.registration


import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cinetech.ui.R
import com.cinetech.ui.core.MaskVisualTransformation
import com.cinetech.ui.navigation.Screen
import com.cinetech.ui.screen.registration.model.RegistrationUiEffect
import com.cinetech.ui.theme.paddings
import com.cinetech.ui.theme.spacers
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    onPop: () -> Unit,
    onNavigate: (Screen) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    val invalidNameAnimation = remember {
        Animatable(
            initialValue = 0f,
            typeConverter = Float.VectorConverter
        )
    }

    val invalidUserNameAnimation = remember {
        Animatable(
            initialValue = 0f,
            typeConverter = Float.VectorConverter
        )
    }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            launch {
                when (it) {
                    RegistrationUiEffect.NameInvalid -> {
                        invalidNameAnimation.animateTo(
                            targetValue = 0f,
                            animationSpec = keyframes {
                                durationMillis = 100
                                0f at 0
                                (-30f) at 50
                                0f at 100
                            },
                        )
                    }

                    RegistrationUiEffect.UserNameInvalid -> {
                        invalidUserNameAnimation.animateTo(
                            targetValue = 0f,
                            animationSpec = keyframes {
                                durationMillis = 100
                                0f at 0
                                (-30f) at 50
                                0f at 100
                            },
                        )
                    }

                    is RegistrationUiEffect.ShowToast -> {
                        Toast.makeText(context, context.getString(it.rId), Toast.LENGTH_SHORT).show()
                    }

                    is RegistrationUiEffect.NavigateTo -> {
                        onNavigate(it.screen)
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = { TopBar(onBackClick = onPop) },
        floatingActionButton = { NextFloatingActionButton(isLoading = state.isLoading, onClick = viewModel::registerUser) },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = MaterialTheme.paddings.extraLarge),
        ) {
            TopLabel()
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            PhoneNumberTextField(phone = viewModel.phoneNumber)
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))
            NameTextField(
                modifier = Modifier.graphicsLayer(translationX = invalidNameAnimation.value),
                name = state.name,
                onValueChange = viewModel::onNameChange
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))
            UserNameTextField(
                modifier = Modifier.graphicsLayer(translationX = invalidUserNameAnimation.value),
                userName = state.userName,
                onValueChange = viewModel::onUserNameChange,
                onGo = viewModel::registerUser
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PhoneNumberTextField(
    phone: String,
) {
    val phoneMask = when (phone.length) {
        12 -> {
            "## ### ### ####"
        }

        13 -> {
            "### ### ### ####"
        }

        else -> {
            "#### ### ### ####"
        }
    }

    OutlinedTextField(
        modifier = Modifier.pointerInteropFilter { true },
        value = phone,
        onValueChange = {},
        readOnly = true,
        visualTransformation = MaskVisualTransformation(phoneMask),
        label = { Text(stringResource(R.string.registration_screen_phone_number)) },
    )
}

@Composable
private fun UserNameTextField(
    modifier: Modifier = Modifier,
    userName: String,
    supportingText: Int? = null,
    onValueChange: (String) -> Unit,
    onGo: () -> Unit
) {

    OutlinedTextField(
        modifier = modifier.imePadding(),
        value = userName,
        supportingText = if (supportingText != null) {
            {
                Text(
                    stringResource(supportingText),
                    style = TextStyle(color = MaterialTheme.colorScheme.error)
                )
            }
        } else {
            null
        },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Go
        ),
        keyboardActions = KeyboardActions(
            onGo = { onGo() }
        ),
        label = { Text(stringResource(R.string.registration_screen_user_name)) },
    )
}

@Composable
private fun NameTextField(
    modifier: Modifier = Modifier,
    name: String,
    supportingText: Int? = null,
    onValueChange: (String) -> Unit,
) {

    OutlinedTextField(
        modifier = modifier.imePadding(),
        value = name,
        supportingText = if (supportingText != null) {
            {
                Text(
                    stringResource(supportingText),
                    style = TextStyle(color = MaterialTheme.colorScheme.error)
                )
            }
        } else {
            null
        },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        label = { Text(stringResource(R.string.registration_screen_name)) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {},
        navigationIcon = { IconButton(onBackClick) { Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "") } },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
    )
}

@Composable
private fun NextFloatingActionButton(
    isLoading: Boolean,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = Modifier.imePadding(),
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(25.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        } else {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "")
        }
    }
}


@Composable
private fun TopLabel() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.registration_screen_title),
            style = MaterialTheme.typography.titleMedium
        )
    }
}