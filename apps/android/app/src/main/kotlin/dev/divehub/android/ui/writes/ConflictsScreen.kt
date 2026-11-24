package dev.divehub.android.ui.writes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.ui.DiveHubColors
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.DiveHubTopBar
import dev.divehub.android.ui.Formats
import dev.divehub.android.ui.Spacing
import dev.divehub.android.ui.components.EmptyState
import dev.divehub.android.ui.components.InkButton
import dev.divehub.android.ui.components.QuietButton
import dev.divehub.android.ui.components.Section
import dev.divehub.shared.write.ConflictFieldView
import dev.divehub.shared.write.ConflictSide
import dev.divehub.shared.write.QueuedWriteView
import dev.divehub.shared.write.WriteDropReason
import dev.divehub.shared.write.WriteNotice
import dev.divehub.shared.write.WriteConflictView
import java.util.Locale

/** The queue screen's route: reread the queue on entry, then draw exactly what the shared core holds. */
@Composable
fun ConflictsRoute(
    viewModel: WritesViewModel,
    units: String?,
    onBack: () -> Unit,
    onOpenDive: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { viewModel.refresh() }
    ConflictsScreen(
        state = state,
        units = units,
        onBack = onBack,
        onResolve = viewModel::resolve,
        onRetry = viewModel::retryFailed,
        onDismissNotices = viewModel::dismissNotices,
        onOpenDive = onOpenDive,
    )
}

/**
 * The queue's one screen (ADR 0084 D3, D4; client contract "Offline writes"). Parked conflicts come first, a field
 * at a time, worded "this phone" vs "the server" with the User's own fresh edit preselected and nothing overwritten
 * until they choose. Under them, every entry still waiting for the connection is listed, so "2 changes waiting to
 * sync" has a way to see what waits; a failed entry says so and offers *Try again*.
 *
 * Everything is drawn from [state], so the light/dark and EN/DE shapes have screenshot tests.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConflictsScreen(
    state: WritesUiState,
    units: String?,
    onBack: () -> Unit,
    onResolve: (Long, Map<String, ConflictSide>) -> Unit,
    onRetry: () -> Unit,
    onDismissNotices: () -> Unit,
    onOpenDive: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            DiveHubTopBar(
                title = stringResource(R.string.conflicts_title),
                onBack = onBack,
                stackedTitle = true,
            )
        },
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding).fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            if (state.conflicts.isEmpty() && state.queued.isEmpty() && state.notices.isEmpty()) {
                EmptyState(
                    modifier = Modifier.fillMaxSize().padding(Spacing.s5),
                    title = stringResource(R.string.conflicts_empty_title),
                    message = stringResource(R.string.conflicts_empty_body),
                    action = { QuietButton(stringResource(R.string.back), onClick = onBack) },
                )
            } else {
                ChangesList(state, units, onResolve, onRetry, onDismissNotices, onOpenDive)
            }
        }
    }
}

@Composable
private fun ChangesList(
    state: WritesUiState,
    units: String?,
    onResolve: (Long, Map<String, ConflictSide>) -> Unit,
    onRetry: () -> Unit,
    onDismissNotices: () -> Unit,
    onOpenDive: (String) -> Unit,
) {
    // A field with no choice keeps the preselected "this phone" side; the map only holds explicit moves to the server.
    val selections = remember { mutableStateMapOf<String, ConflictSide>() }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = Spacing.s4,
            end = Spacing.s4,
            top = Spacing.s4,
            bottom = Spacing.s5,
        ),
    ) {
        if (state.actionFailed) {
            item(key = "failed") {
                Text(
                    stringResource(R.string.conflict_resolve_failed),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Spacing.s2)
                        .semantics { liveRegion = LiveRegionMode.Polite },
                    style = DiveHubText.meta,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
        if (state.notices.isNotEmpty()) {
            item(key = "notices") {
                NoticeSection(
                    notices = state.notices,
                    showSeparator = state.actionFailed,
                    onDismiss = onDismissNotices,
                    onOpenDive = onOpenDive,
                )
            }
        }
        itemsIndexed(state.conflicts, key = { _, conflict -> "conflict-${conflict.entryId}" }) { index, conflict ->
            ConflictEntry(
                conflict = conflict,
                units = units,
                showSeparator = index > 0 || state.actionFailed,
                busy = state.busy,
                selection = {
                    selections[selectionKey(conflict.entryId, conflict.serverVersion, it.field)]
                        ?: ConflictSide.ThisPhone
                },
                onSelect = { field, side ->
                    selections[selectionKey(conflict.entryId, conflict.serverVersion, field)] = side
                },
                onResolve = { choices -> onResolve(conflict.entryId, choices) },
            )
        }
        if (state.queued.isNotEmpty()) {
            item(key = "queued") {
                QueuedSection(
                    entries = state.queued,
                    showSeparator = state.conflicts.isNotEmpty() || state.actionFailed,
                    busy = state.busy,
                    onRetry = onRetry,
                )
            }
        }
    }
}

@Composable
private fun ConflictEntry(
    conflict: WriteConflictView,
    units: String?,
    showSeparator: Boolean,
    busy: Boolean,
    selection: (ConflictFieldView) -> ConflictSide,
    onSelect: (String, ConflictSide) -> Unit,
    onResolve: (Map<String, ConflictSide>) -> Unit,
) {
    val useServer = stringResource(R.string.conflict_use_server)
    Section(
        title = diveTitle(conflict.diveNumber),
        showSeparator = showSeparator,
        action = {
            QuietButton(useServer, enabled = !busy, onClick = {
                onResolve(conflict.fields.associate { it.field to ConflictSide.Server })
            })
        },
    ) {
        conflict.fields.forEach { field ->
            FieldDecision(
                field = field,
                units = units,
                selected = selection(field),
                enabled = !busy,
                onSelect = { side -> onSelect(field.field, side) },
            )
        }
        Spacer(Modifier.height(Spacing.s3))
        InkButton(
            text = stringResource(R.string.conflict_keep_selected),
            onClick = { onResolve(conflict.fields.associate { it.field to selection(it) }) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !busy,
        )
    }
}

/**
 * One both-sides-changed field: its name, then two full-row choices so the whole row is the target and TalkBack
 * reads the side, its value and the selected state together.
 */
@Composable
private fun FieldDecision(
    field: ConflictFieldView,
    units: String?,
    selected: ConflictSide,
    enabled: Boolean,
    onSelect: (ConflictSide) -> Unit,
) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val imperial = Formats.imperial(units, locale)
    Column(Modifier.fillMaxWidth().padding(top = Spacing.s2)) {
        Text(
            conflictFieldLabel(field.field),
            style = DiveHubText.label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        ConflictChoice(
            name = stringResource(R.string.conflict_this_phone),
            value = conflictValueText(context, field.field, field.local, locale, imperial, field.localAction),
            selected = selected == ConflictSide.ThisPhone,
            enabled = enabled,
            tag = "conflict_${field.field}_this_phone",
            onSelect = { onSelect(ConflictSide.ThisPhone) },
        )
        ConflictChoice(
            name = stringResource(R.string.conflict_server),
            value = conflictValueText(context, field.field, field.server, locale, imperial),
            selected = selected == ConflictSide.Server,
            enabled = enabled,
            tag = "conflict_${field.field}_server",
            onSelect = { onSelect(ConflictSide.Server) },
        )
    }
}

@Composable
private fun ConflictChoice(
    name: String,
    value: String,
    selected: Boolean,
    enabled: Boolean,
    tag: String,
    onSelect: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .testTag(tag)
            .minimumInteractiveComponentSize()
            .selectable(selected = selected, enabled = enabled, role = Role.RadioButton, onClick = onSelect)
            .padding(vertical = Spacing.s2),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
    ) {
        RadioButton(selected = selected, onClick = null, enabled = enabled)
        Column(Modifier.weight(1f)) {
            Text(name, style = DiveHubText.bodyStrong)
            Text(value, style = DiveHubText.meta, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

/** Edits a replay had to drop because their thing is gone; named instead of silently vanishing (D3 step 4). */
@Composable
private fun NoticeSection(
    notices: List<WriteNotice>,
    showSeparator: Boolean,
    onDismiss: () -> Unit,
    onOpenDive: (String) -> Unit,
) {
    val context = LocalContext.current
    val dismiss = stringResource(R.string.notice_dismiss)
    val openDive = stringResource(R.string.notice_open_dive)
    Section(
        title = stringResource(R.string.queued_notices_title),
        showSeparator = showSeparator,
        action = { QuietButton(dismiss, onClick = onDismiss) },
    ) {
        notices.forEachIndexed { index, notice ->
            if (index > 0) Spacer(Modifier.height(Spacing.s2))
            Text(
                noticeText(context, notice),
                style = DiveHubText.body,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            notice.mergedInto?.let { id ->
                QuietButton(openDive, onClick = { onOpenDive(id) })
            }
        }
    }
}

private fun noticeText(context: android.content.Context, notice: WriteNotice): String = when {
    notice.mergedInto != null && notice.mergedIntoNumber != null ->
        context.getString(R.string.notice_merged_into, notice.mergedIntoNumber)
    notice.mergedInto != null -> context.getString(R.string.notice_merged)
    notice.reason == WriteDropReason.Forbidden -> context.getString(R.string.notice_forbidden)
    notice.reason == WriteDropReason.Gone -> context.getString(R.string.notice_gone)
    else -> context.getString(R.string.notice_deleted)
}

/** What waits for the connection or a retry, with enough to recognise the edit without opening it. */
@Composable
private fun QueuedSection(
    entries: List<QueuedWriteView>,
    showSeparator: Boolean,
    busy: Boolean,
    onRetry: () -> Unit,
) {
    val context = LocalContext.current
    val retryLabel = stringResource(R.string.pending_changes_retry)
    Section(
        title = stringResource(R.string.queued_changes_title),
        showSeparator = showSeparator,
        action = {
            if (entries.any { it.failed }) {
                QuietButton(retryLabel, enabled = !busy, onClick = onRetry)
            }
        },
    ) {
        entries.forEachIndexed { index, entry ->
            if (index > 0) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = Spacing.s2),
                    color = DiveHubColors.border,
                )
            }
            Column(Modifier.fillMaxWidth().padding(vertical = Spacing.s1)) {
                Text(diveTitle(entry.diveNumber), style = DiveHubText.bodyStrong)
                val fields = entry.fields
                    .map { conflictFieldLabel(context, it) }
                    .distinct()
                    .joinToString(", ")
                if (fields.isNotEmpty()) {
                    Text(fields, style = DiveHubText.meta, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                if (entry.failed) {
                    Text(
                        stringResource(R.string.queued_change_not_sent),
                        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                        style = DiveHubText.meta,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }
    }
}

@Composable
private fun diveTitle(number: Int?): String = number?.let {
    stringResource(R.string.conflict_dive_title, it)
} ?: stringResource(R.string.conflict_dive_unnamed)

/** Keyed by the server version too: a re-parked entry with a new base must not inherit an old choice. */
private fun selectionKey(entryId: Long, serverVersion: Long, field: String): String =
    "$entryId/$serverVersion/$field"
