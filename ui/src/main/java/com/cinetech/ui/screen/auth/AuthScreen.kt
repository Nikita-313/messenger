package com.cinetech.ui.screen.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cinetech.ui.R
import com.cinetech.ui.composible.EmojiFlag
import com.cinetech.ui.core.MaskVisualTransformation
import com.cinetech.ui.navigation.Screen
import com.cinetech.ui.screen.auth.model.AuthUiEffect
import com.cinetech.ui.screen.auth.model.Country
import com.cinetech.ui.theme.paddings
import com.cinetech.ui.theme.spacers

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigate: (Screen) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    val invalidPhoneAnimation = remember {
        Animatable(
            initialValue = 0f,
            typeConverter = Float.VectorConverter
        )
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                AuthUiEffect.PhoneNumberInvalid -> {
                    invalidPhoneAnimation.animateTo(
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
        floatingActionButton = {
            AuthFloatingActionButton(onClick = viewModel::sendSmsCode)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(MaterialTheme.paddings.extraLarge)
                .padding(top = MaterialTheme.paddings.xxLarge)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopLabel()
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            PhoneTextField(
                modifier = Modifier.graphicsLayer(translationX = invalidPhoneAnimation.value),
                countryCode = state.countyCode,
                phoneNumber = state.phoneNumber,
                country = state.country,
                onCountryCodeChange = viewModel::onCountyCodeChange,
                onPhoneNumberChange = viewModel::onPhoneNumberChange,
                onSelectCountry = { onNavigate(Screen.SelectCountryCode) },
                onGo =  viewModel::sendSmsCode
            )
        }
    }
}

@Composable
private fun AuthFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "")
    }
}

@Composable
private fun PhoneTextField(
    modifier: Modifier = Modifier,
    countryCode: String,
    phoneNumber: String,
    country: Country?,
    onCountryCodeChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onSelectCountry: () -> Unit,
    onGo:()->Unit
) {

    val countryCodeFocusRequester = remember { FocusRequester() }
    val phoneNumberFocusRequester = remember { FocusRequester() }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        errorBorderColor = Color.Transparent,
        disabledBorderColor = Color.Transparent,
        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        cursorColor = MaterialTheme.colorScheme.onBackground,
        selectionColors = TextSelectionColors(
            backgroundColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.1f),
            handleColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.1f)
        )
    )

    LaunchedEffect(Unit) {
        if (countryCode.isEmpty()) countryCodeFocusRequester.requestFocus()
        else phoneNumberFocusRequester.requestFocus()
    }

    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.border(BorderStroke(2.dp, MaterialTheme.colorScheme.primary), shape = RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .width(75.dp)
                    .focusRequester(countryCodeFocusRequester),
                value = countryCode,
                onValueChange = {
                    onCountryCodeChange(it)
                    if (it.length == 3) phoneNumberFocusRequester.requestFocus()
                },
                singleLine = true,
                prefix = {
                    Text("+")
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions(
                    onGo = { phoneNumberFocusRequester.requestFocus() }
                ),
                colors = textFieldColors,
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.extraSmall))
            VerticalDivider(modifier = Modifier.height(20.dp), color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.extraSmall))
            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .focusRequester(phoneNumberFocusRequester)
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Backspace && phoneNumber.isEmpty()) {
                                onCountryCodeChange(countryCode.dropLast(1))
                                countryCodeFocusRequester.requestFocus()
                            }
                            true
                        },
                    value = phoneNumber,
                    onValueChange = onPhoneNumberChange,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Go
                    ),
                    keyboardActions = KeyboardActions(
                        onGo = { onGo() }
                    ),
                    visualTransformation = MaskVisualTransformation("### ### ####"),
                    suffix = { EmojiFlagAnimation(country, onSelectCountry) },
                    colors = textFieldColors,
                )
            }
        }
        Text(
            modifier = Modifier
                .offset(x = 10.dp, y = (-7).dp)
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = MaterialTheme.paddings.extraSmall),
            text = stringResource(R.string.auth_screen_phone_number),
            style = MaterialTheme.typography.bodySmall
        )
    }


}

@Composable
private fun EmojiFlagAnimation(
    country: Country?,
    onSelectCountry: () -> Unit
) {
    var previousCountry by remember { mutableStateOf<Country?>(null) }
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(country) {
        if (country != null) {
            previousCountry = country
            visible = true
        } else {
            visible = false
        }
    }

    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .clickable(onClick = onSelectCountry),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(
                initialOffsetY = { it }
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { -it }
            ) + fadeOut()
        ) {
            EmojiFlag(previousCountry?.isoName, fontSize = 18.sp)
        }

        AnimatedVisibility(
            visible = !visible,
            enter = slideInVertically(
                initialOffsetY = { it }
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { -it }
            ) + fadeOut()
        ) {
            Text("\uD83C\uDF10", fontSize = 18.sp)
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
            text = stringResource(R.string.auth_screen_title),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        Text(
            text = stringResource(R.string.auth_screen_support_text),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}