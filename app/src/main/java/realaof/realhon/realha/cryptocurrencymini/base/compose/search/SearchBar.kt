package realaof.realhon.realha.cryptocurrencymini.base.compose.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import realaof.realhon.realha.cryptocurrencymini.R
import realaof.realhon.realha.cryptocurrencymini.ui.theme.dimen
import realaof.realhon.realha.cryptocurrencymini.ui.theme.SearchBg

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    state: EditableSearchInputState = rememberEditableSearchInputState(hint = ""),
    onSearchAction: (String) -> Unit = {},
    onValueChange: (String) -> Unit = {},
    onClickedClearText: () -> Unit = {}
) {
    OutlinedTextField(
        value = state.text,
        placeholder = {
            Text(text = stringResource(id = R.string.coin_currency_common_search))
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Coin Icon Searchbar"
            )
        },
        trailingIcon = {
            if (state.text.isNotEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_remove),
                    contentDescription = "Coin Search Remove Icon ",
                    modifier = Modifier
                        .size(dimen.dimen_16)
                        .clickable {
                            state.clearText()
                            onClickedClearText()
                        }
                )
            }
        },
        shape = RoundedCornerShape(dimen.dimen_8),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = SearchBg,
            focusedContainerColor = SearchBg
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchAction.invoke(state.text)
            }
        ),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(dimen.dimen_48),
        onValueChange = { newText ->
            state.updateText(newText)
            onValueChange(newText)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    SearchBar(
        state = rememberEditableSearchInputState(hint = "Coin")
    )
}