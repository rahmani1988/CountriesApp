package com.reza.systemdesign.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.reza.countriesapp.ui.theme.CountriesAppTheme
import com.reza.systemdesign.ui.util.Constants


@Composable
fun LoadingItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(Modifier.testTag(Constants.UiTags.ProgressIndicator.customName))
    }
}

@Preview
@Composable
private fun LoadingItemPreview() {
    CountriesAppTheme {
        LoadingItem()
    }
}