package realaof.realhon.realha.cryptocurrencymini.base.compose.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Orange

@Composable
fun BaseLoading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = Orange
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BaseLoadingPreview() {
    BaseLoading()
}