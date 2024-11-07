package com.cinetech.ui.screen.sms_verification

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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cinetech.ui.R
import com.cinetech.ui.core.MaskVisualTransformation
import com.cinetech.ui.screen.sms_verification.model.SmsVerificationUiEffect
import com.cinetech.ui.theme.paddings
import com.cinetech.ui.theme.spacers

@Composable
fun SmsVerificationScreen(
    viewModel: SmsVerificationViewModel = hiltViewModel(),
    onPop: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val invalidSmsAnimation = remember {
        Animatable(
            initialValue = 0f,
            typeConverter = Float.VectorConverter
        )
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                SmsVerificationUiEffect.SmsCodeInvalid -> {
                    invalidSmsAnimation.animateTo(
                        targetValue = 0f,
                        animationSpec = keyframes {
                            durationMillis = 100
                            0f at 0
                            (-30f) at 50
                            0f at 100
                        },
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = { TopBar(onPop) },
        floatingActionButton = { NextFloatingActionButton(state.isLoading, viewModel::checkSmsCode) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = MaterialTheme.paddings.extraLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopLabel()
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            SmsTextField(
                modifier = Modifier.graphicsLayer(translationX = invalidSmsAnimation.value),
                code = state.smsCode,
                onValueChange = viewModel::onSmsCodeTextChange,
                onGo = viewModel::checkSmsCode,
                supportingText = state.errorTextRId,
            )
        }
    }
}

@Composable
private fun SmsTextField(
    modifier: Modifier = Modifier,
    supportingText: Int?,
    code: String,
    onValueChange: (String) -> Unit,
    onGo: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth().focusRequester(focusRequester),
        value = code,
        supportingText = if (supportingText != null) {
            {
                Text(
                    stringResource(supportingText),
                    style = TextStyle(color = MaterialTheme.colorScheme.error)
                )
            }
        } else { {} },
        onValueChange = onValueChange,
        visualTransformation = MaskVisualTransformation("### ###"),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Go
        ),
        keyboardActions = KeyboardActions(
            onGo = { onGo() }
        ),
        label = { Text(stringResource(R.string.sms_verification_screen_sms_code)) },
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
    )
}

@Composable
private fun NextFloatingActionButton(
    isLoading: Boolean,
    onClick: () -> Unit
) {
    FloatingActionButton(
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
            text = stringResource(R.string.sms_verification_screen_title),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        Text(
            text = stringResource(R.string.sms_verification_screen_support_text),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = { },
        navigationIcon = { IconButton(onBackClick) { Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "") } }
    )
}
