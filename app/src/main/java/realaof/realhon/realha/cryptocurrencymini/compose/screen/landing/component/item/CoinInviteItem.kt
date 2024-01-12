package realaof.realhon.realha.cryptocurrencymini.compose.screen.landing.component.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import realaof.realhon.realha.cryptocurrencymini.R
import realaof.realhon.realha.cryptocurrencymini.ui.theme.Onahau
import realaof.realhon.realha.cryptocurrencymini.ui.theme.SkyLight
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen

@Composable
fun CoinInviteItem(
    modifier: Modifier = Modifier,
    onClickedItemToShared: (String) -> Unit = {}
) {
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Onahau
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClickedItemToShared(
                    context.getString(R.string.coin_currency_coin_item_earn_coin_text)
                )
            }
    ) {
        Row(
            modifier = Modifier
                .padding(
                    vertical = dimen.dimen_22,
                    horizontal = dimen.dimen_16
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(dimen.dimen_40)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_gift),
                    contentDescription = "Coin Invite Icon",
                    modifier = Modifier
                        .size(dimen.dimen_22)
                )
            }
            Spacer(modifier = Modifier.width(dimen.dimen_16))
            TextInline(
                text = stringResource(id = R.string.coin_currency_coin_item_earn_coin_text),
                inlineText = stringResource(id = R.string.coin_currency_coin_item_invite_your_friend),
                style = MaterialTheme.typography.titleMedium,
                inLineTextStyle = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp,
                    color = SkyLight
                ),
                placeholder = Placeholder(
                    width = 9.em,
                    height = 21.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextBottom
                )
            )
        }
    }
}

@Composable
fun TextInline(
    modifier: Modifier = Modifier,
    text: String,
    text2: String? = "",
    inlineText: String,
    placeholder: Placeholder,
    style: TextStyle = TextStyle.Default,
    inLineTextStyle: TextStyle = TextStyle.Default
) {
    val id = "inlineContent"
    val stringText = buildAnnotatedString {
        append(text)
        appendInlineContent(id = id, inlineText)
        append(text2)
    }

    val inlineContent = mapOf(
        Pair(
            id,
            InlineTextContent(placeholder) {
                Text(
                    text = it,
                    style = inLineTextStyle,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = dimen.dimen_2)
                )
            }
        )
    )

    BasicText(
        text = stringText,
        inlineContent = inlineContent,
        style = style,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun CoinInviteItemPreview() {
    CoinInviteItem(
        onClickedItemToShared = {

        }
    )
}