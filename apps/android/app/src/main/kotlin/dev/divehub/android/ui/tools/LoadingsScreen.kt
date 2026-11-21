package dev.divehub.android.ui.tools

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.ui.DiveHubTopBar
import dev.divehub.android.ui.Formats
import dev.divehub.api.models.ApiDiversIdLoadingsGet200Response
import java.util.Locale

/** The sources the client contract asks a client to link beside the numbers (ADR 0071). */
private data class LoadingSource(val title: Int, val url: String)

private val LOADING_SOURCES = listOf(
    LoadingSource(
        R.string.tools_loadings_source_rules,
        "https://github.com/Fahrner-Labs/dive_hub/blob/main/docs/decisions/0071-current-loadings-estimate.md",
    ),
    LoadingSource(
        R.string.tools_loadings_source_cns,
        "https://shearwater.com/blogs/community/shearwater-and-the-cns-oxygen-clock",
    ),
    LoadingSource(
        R.string.tools_loadings_source_otu,
        "https://www8.garmin.com/manuals/webhelp/GUID-D6BA1847-E20D-4241-B08C-DF0D8B052B28/EN-US/GUID-8179D8C5-35C9-450E-9E61-49180CD82701.html",
    ),
)

@Composable
fun LoadingsRoute(viewModel: LoadingsViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { viewModel.start() }
    LoadingsScreen(state = state, onBack = onBack, onReload = viewModel::reload)
}

/**
 * What the Diver's synced dives leave in them now (FAH-276; ADR 0071): residual N₂, CNS and OTU with DAN's no-fly
 * time beside them. A missing estimate stays missing, a returned zero stays a real zero, and every result says how
 * old the synced dives are — with the profiles-syncing marker while they arrive.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingsScreen(
    state: LoadingsUiState,
    onBack: () -> Unit,
    onReload: () -> Unit,
) {
    Scaffold(
        topBar = {
            DiveHubTopBar(
                title = stringResource(R.string.tools_loadings_title),
                onBack = onBack,
            )
        },
    ) { padding ->
        if (state.loading) {
            Column(
                Modifier.padding(padding).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                val label = stringResource(R.string.loading)
                CircularProgressIndicator(Modifier.semantics { contentDescription = label })
            }
            return@Scaffold
        }
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Notice(stringResource(R.string.tools_notice))
            when {
                state.storeError != null -> {
                    Spacer(Modifier.height(8.dp))
                    Card {
                        Text(
                            stringResource(R.string.tools_store_unreadable),
                            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        TextButton(onClick = onReload) { Text(stringResource(R.string.logbook_retry)) }
                    }
                }
                !state.personalized -> {
                    Spacer(Modifier.height(8.dp))
                    Card {
                        Text(
                            stringResource(R.string.tools_loadings_empty),
                            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
                else -> {
                    Spacer(Modifier.height(12.dp))
                    StalenessLabel(state.syncedAt, state.profilesComplete, R.string.tools_not_fully_synced)
                    val answer = state.answer
                    if (answer == null) {
                        Muted(stringResource(R.string.tools_loadings_nothing))
                    } else {
                        LoadingsAnswer(answer)
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Muted(stringResource(R.string.tools_estimate_short))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LoadingsAnswer(answer: ApiDiversIdLoadingsGet200Response) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val pattern = stringResource(R.string.tools_date_pattern)
    val n2 = answer.n2
    val cns = answer.cns
    val otu = answer.otu
    val noFly = answer.noFly

    Heading(stringResource(R.string.tools_loadings_numbers))
    if (n2 != null) {
        LoadingFact(
            stringResource(R.string.tools_loadings_n2),
            stringResource(
                R.string.tools_loadings_n2_value,
                percent(locale, n2.worstCompartmentPercent),
                Formats.decimal(locale, n2.gfHigh, 0),
            ),
        )
        Muted(stringResource(R.string.tools_loadings_n2_downsampled))
    } else {
        MissingValue(stringResource(R.string.tools_loadings_n2_missing))
    }
    if (cns != null) {
        LoadingFact(
            stringResource(R.string.tools_loadings_cns),
            stringResource(
                R.string.tools_loadings_cns_value,
                percent(locale, cns.percentNow),
                percent(locale, cns.percentAtLastDiveEnd),
            ),
        )
        Muted(stringResource(R.string.tools_loadings_cns_note, cns.halfLifeMinutes))
    } else {
        MissingValue(stringResource(R.string.tools_loadings_cns_missing))
    }
    if (otu != null) {
        LoadingFact(
            stringResource(R.string.tools_loadings_otu),
            Formats.decimal(locale, otu.last24Hours, 1),
        )
    } else {
        MissingValue(stringResource(R.string.tools_loadings_otu_missing))
    }
    if (noFly != null) {
        LoadingFact(stringResource(R.string.tools_loadings_no_fly), "")
        Text(
            stringResource(
                when (noFly.reason) {
                    "several" -> R.string.tools_loadings_no_fly_several
                    "decompression" -> R.string.tools_loadings_no_fly_decompression
                    else -> R.string.tools_loadings_no_fly_single
                },
                dateTime(noFly.until, locale, pattern),
                noFly.hours,
            ),
            style = MaterialTheme.typography.bodyMedium,
        )
        SourceLink(noFly.source.title, noFly.source.url)
    } else {
        MissingValue(stringResource(R.string.tools_loadings_no_fly_missing))
    }

    Spacer(Modifier.height(12.dp))
    HeadingSmall(stringResource(R.string.tools_loadings_basis_title))
    val basis = answer.basis
    val last = basis.lastDiveEndedAt?.let { dateTime(it, locale, pattern) }
    Text(
        when {
            last == null -> stringResource(R.string.tools_loadings_basis_no_dive)
            basis.divesUsed == 0 -> stringResource(R.string.tools_loadings_basis_none, basis.windowHours, last)
            else -> stringResource(R.string.tools_loadings_basis, basis.divesUsed, basis.windowHours, last)
        },
        style = MaterialTheme.typography.bodyMedium,
    )
    if (basis.divesSkippedNoProfile > 0) {
        Text(
            context.resources.getQuantityString(
                R.plurals.tools_loadings_skipped,
                basis.divesSkippedNoProfile,
                basis.divesSkippedNoProfile,
            ),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
    Text(stringResource(R.string.tools_loadings_note), style = MaterialTheme.typography.bodyMedium)
    Spacer(Modifier.height(8.dp))
    Sources()
}

/** A number's name above its value: a long value must not squeeze the name (411 dp). */
@Composable
private fun LoadingFact(label: String, value: String) {
    Column(Modifier.padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.titleSmall)
        if (value.isNotEmpty()) Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun MissingValue(text: String) {
    Text(text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Sources() {
    val uriHandler = LocalUriHandler.current
    Text(stringResource(R.string.tools_loadings_sources), style = MaterialTheme.typography.bodySmall)
    FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        LOADING_SOURCES.forEach { source ->
            Text(
                stringResource(source.title),
                modifier = Modifier
                    .clickable { uriHandler.openUri(source.url) }
                    .padding(vertical = 4.dp, horizontal = 2.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun SourceLink(title: String, url: String) {
    val uriHandler = LocalUriHandler.current
    Text(
        title,
        modifier = Modifier
            .clickable { uriHandler.openUri(url) }
            .padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodySmall,
    )
}

private fun percent(locale: Locale, value: Double): String =
    "${Formats.decimal(locale, value, 1)} %"
