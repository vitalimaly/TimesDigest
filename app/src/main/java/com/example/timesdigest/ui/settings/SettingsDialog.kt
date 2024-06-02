package com.example.timesdigest.ui.settings

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.timesdigest.R.string
import com.example.timesdigest.model.DarkModeConfig
import com.example.timesdigest.model.DarkModeConfig.DARK
import com.example.timesdigest.model.DarkModeConfig.LIGHT
import com.example.timesdigest.model.DarkModeConfig.SYSTEM
import com.example.timesdigest.ui.theme.TimesDigestTheme
import com.example.timesdigest.ui.theme.supportsDynamicColors

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val settingsUiState by viewModel.uiState.collectAsStateWithLifecycle()
    SettingsDialog(
        settingsUiState = settingsUiState,
        onChangeDynamicColorsPreference = viewModel::setDynamicColorsPreference,
        onChangeDarkModeConfig = viewModel::setDarkModePreference,
        onDismiss = onDismiss,
    )
}

@Composable
fun SettingsDialog(
    settingsUiState: SettingsUiState,
    supportDynamicColors: Boolean = supportsDynamicColors(),
    onChangeDynamicColorsPreference: (useDynamicColors: Boolean) -> Unit,
    onChangeDarkModeConfig: (darkModeConfig: DarkModeConfig) -> Unit,
    onDismiss: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    AlertDialog(
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(string.settings),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            HorizontalDivider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                when (settingsUiState) {
                    SettingsUiState.Loading -> {
                        Text(
                            text = stringResource(string.loading),
                            modifier = Modifier.padding(vertical = 16.dp),
                        )
                    }
                    is SettingsUiState.Success -> {
                        SettingsPanel(
                            settings = settingsUiState.settings,
                            supportDynamicColors = supportDynamicColors,
                            onChangeDynamicColorsPreference = onChangeDynamicColorsPreference,
                            onChangeDarkMode = onChangeDarkModeConfig,
                        )
                    }
                }
                HorizontalDivider(Modifier.padding(top = 8.dp))
                LinksPanel()
            }
        },
        confirmButton = {
            Text(
                text = stringResource(string.ok),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() },
            )
        },
    )
}

@Composable
private fun SettingsPanel(
    settings: UserEditableSettings,
    supportDynamicColors: Boolean,
    onChangeDynamicColorsPreference: (useDynamicColors: Boolean) -> Unit,
    onChangeDarkMode: (darkModeConfig: DarkModeConfig) -> Unit,
) {
    if (supportDynamicColors) {
        SettingsDialogSectionTitle(text = stringResource(string.settings_use_dynamic_color_title))
        Column(Modifier.selectableGroup()) {
            SettingsDialogThemeChooserRow(
                text = stringResource(string.yes),
                selected = settings.useDynamicColors,
                onClick = { onChangeDynamicColorsPreference(true) },
            )
            SettingsDialogThemeChooserRow(
                text = stringResource(string.no),
                selected = !settings.useDynamicColors,
                onClick = { onChangeDynamicColorsPreference(false) },
            )
        }
    }
    SettingsDialogSectionTitle(text = stringResource(string.settings_dark_mode_title))
    Column(Modifier.selectableGroup()) {
        SettingsDialogThemeChooserRow(
            text = stringResource(string.settings_dark_mode_system_default),
            selected = settings.darkModeConfig == SYSTEM,
            onClick = { onChangeDarkMode(SYSTEM) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(string.settings_dark_mode_light),
            selected = settings.darkModeConfig == LIGHT,
            onClick = { onChangeDarkMode(LIGHT) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(string.settings_dark_mode_dark),
            selected = settings.darkModeConfig == DARK,
            onClick = { onChangeDarkMode(DARK) },
        )
    }
}

@Composable
private fun SettingsDialogSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

@Composable
fun SettingsDialogThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LinksPanel() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        val context = LocalContext.current
        val uriHandler = LocalUriHandler.current

        TextButton(
            onClick = {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Check out this app - Times Digest")
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            },
        ) {
            Text(text = stringResource(string.settings_feedback))
        }
        TextButton(
            onClick = { uriHandler.openUri("https://www.google.com/") }, // terms of use link
        ) {
            Text(text = stringResource(string.settings_terms_of_use))
        }
        TextButton(
            onClick = { uriHandler.openUri("https://www.google.com/") }, // privacy policy link
        ) {
            Text(text = stringResource(string.settings_privacy_policy))
        }
    }
}

@Preview
@Composable
private fun PreviewSettingsDialog() {
    TimesDigestTheme(useDynamicColors = true) {
        SettingsDialog(
            settingsUiState = SettingsUiState.Success(
                UserEditableSettings(
                    darkModeConfig = SYSTEM,
                    useDynamicColors = false,
                ),
            ),
            supportDynamicColors = true,
            onChangeDynamicColorsPreference = {},
            onChangeDarkModeConfig = {},
            onDismiss = {},
        )
    }
}

@Preview
@Composable
private fun PreviewSettingsDialogLoading() {
    TimesDigestTheme(useDynamicColors = true) {
        SettingsDialog(
            settingsUiState = SettingsUiState.Loading,
            supportDynamicColors = true,
            onChangeDynamicColorsPreference = {},
            onChangeDarkModeConfig = {},
            onDismiss = {},
        )
    }
}
