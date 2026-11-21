package dev.divehub.android.ui

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop

/** How long a burst of store writes settles before open screens reread it. */
internal const val WRITE_BURST_MS = 250L

/**
 * The sync revision as screens follow it: the value already on screen is dropped, and the bumps one sync's phases
 * write in a burst still into one emission, so a single gesture costs one reread rather than a cascade.
 */
@OptIn(FlowPreview::class)
internal fun Flow<Long>.followStoreWrites(): Flow<Long> = drop(1).debounce(WRITE_BURST_MS)
