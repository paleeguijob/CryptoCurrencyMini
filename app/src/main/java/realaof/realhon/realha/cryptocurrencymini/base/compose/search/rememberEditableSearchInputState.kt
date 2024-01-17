package realaof.realhon.realha.cryptocurrencymini.base.compose.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun rememberEditableSearchInputState(
    hint: String,
    keyword: String,
    onValueChange: (String) -> Unit = {}
) = rememberSaveable(keyword, saver = EditableSearchInputState.Saver) {
    EditableSearchInputState(
        hint = hint,
        keyword = keyword,
        onValueChange = onValueChange
    )
}


class EditableSearchInputState(
    private val hint: String,
    private val keyword: String,
    private val onValueChange: (String) -> Unit = {}
) {
    var text by mutableStateOf(keyword)
        private set

    fun updateText(newText: String) {
        text = newText
        onValueChange(newText)
    }

    fun clearText() {
        text = ""
    }

    val isHint: Boolean
        get() = text == hint

    companion object {
        val Saver: Saver<EditableSearchInputState, *> = listSaver(
            save = { listOf(it.hint, it.keyword) },
            restore = {
                EditableSearchInputState(
                    hint = it[0],
                    keyword = it[1]
                )
            }
        )
    }
}