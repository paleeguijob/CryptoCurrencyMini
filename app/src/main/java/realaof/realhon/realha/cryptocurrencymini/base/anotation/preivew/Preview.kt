package realaof.realhon.realha.cryptocurrencymini.base.anotation.preivew

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@DarkModePreview()
annotation class DarkModePreview

@Preview(showBackground = true)
@LightModePreview()
annotation class LightModePreview
