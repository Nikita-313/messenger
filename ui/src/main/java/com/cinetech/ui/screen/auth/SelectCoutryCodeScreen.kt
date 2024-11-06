package com.cinetech.ui.screen.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cinetech.ui.R
import com.cinetech.ui.composible.EmojiFlag
import com.cinetech.ui.screen.auth.model.Country
import com.cinetech.ui.theme.paddings
import com.cinetech.ui.theme.spacers

@Composable
fun SelectCountryCodeScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onPop: () -> Unit,
) {

    val countries = viewModel.countries.values.toList()

    Scaffold(
        topBar = { TopBar(onPop) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(countries.size) {
                CountyItem(
                    country = countries[it],
                    onClickCountry = { country ->
                        viewModel.onSelectCountry(country)
                        onPop()
                    }
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(R.string.select_country_code_title)) },
        navigationIcon = { IconButton(onBackClick) { Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "") } }
    )
}

@Composable
private fun CountyItem(
    country: Country,
    onClickCountry: (Country) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickCountry(country) }
            .padding(horizontal = MaterialTheme.paddings.extraLarge, vertical = MaterialTheme.paddings.medium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(0.9f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EmojiFlag(country.isoName, fontSize = 18.sp)
            Spacer(Modifier.width(MaterialTheme.spacers.medium))
            Text(country.name, maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.titleSmall)
        }
        Row(
            modifier = Modifier.weight(0.2f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "+" + country.phoneCode,
                maxLines = 1, color = MaterialTheme.colorScheme.primary
            )
        }
    }
}