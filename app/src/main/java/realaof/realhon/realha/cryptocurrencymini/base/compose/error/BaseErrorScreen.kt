package realaof.realhon.realha.cryptocurrencymini.base.compose.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import realaof.realhon.realha.cryptocurrencymini.R
import realaof.realhon.realha.cryptocurrencymini.base.anotation.preivew.DarkModePreview
import realaof.realhon.realha.cryptocurrencymini.base.anotation.preivew.LightModePreview
import realaof.realhon.realha.cryptocurrencymini.ui.theme.DustyGray
import realaof.realhon.realha.cryptocurrencymini.ui.theme.MineShaft
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Orange
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen

@Composable
fun BaseErrorScreen(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    title: String,
    message: String
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        icon?.apply {
            Image(
                imageVector = Icons.Filled.ErrorOutline,
                colorFilter = ColorFilter.tint(Orange),
                contentDescription = "Error Icon $title"
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.W700,
                color = MineShaft,
            ),
            modifier = Modifier
                .padding(top = dimen.dimen_16)
                .padding(horizontal = dimen.dimen_16)
                .clearAndSetSemantics {
                    contentDescription = "Error, Title : $title"
                }
        )

        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.W400,
                color = DustyGray
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = dimen.dimen_12, bottom = dimen.dimen_16)
                .padding(horizontal = dimen.dimen_16)
                .clearAndSetSemantics {
                    contentDescription = "Error, Message: $message"
                }
        )
    }
}

@DarkModePreview
@Composable
private fun BaseErrorScreenPreviewDarkMode() {
    BaseErrorScreen(
        title = stringResource(id = R.string.coin_currency_common_search_error_title),
        message = stringResource(id = R.string.coin_currency_common_search_error_message)
    )
}

@LightModePreview
@Composable
private fun BaseErrorScreenPreviewLightMode() {
    BaseErrorScreen(
        title = stringResource(id = R.string.coin_currency_common_search_error_title),
        message = stringResource(id = R.string.coin_currency_common_search_error_message)
    )
}