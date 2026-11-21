package dev.divehub.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import dev.divehub.android.R
import dev.divehub.android.ui.components.SkeletonBar
import dev.divehub.android.ui.components.rememberSkeletonAlpha
import kotlinx.coroutines.delay

/** At 150 % and above a title that no longer fits beside the icons stacks under them, left-aligned to the gutter. */
private const val StackedTitleFontScale = 1.5f

/** A title's skeleton bar, sized in the final title's line box so the bar never changes the bar's height. */
private val TitleSkeletonWidth = 120.dp

/** ADR 0064's pause before a skeleton replaces a blank slot; the page's own skeleton waits the same 150 ms. */
private const val SkeletonDelayMs = 150L

/** The skeleton's handle for the arrival test; the bar itself stays decorative for TalkBack. */
internal const val TopBarTitleSkeletonTag = "top_bar_title_skeleton"

/**
 * The one app bar every screen draws (FAH-335): the screen's title and, on a pushed screen, a real back arrow. A
 * top-level place passes no [onBack] because the bottom bar is where it leaves; its subscreens are reached from its
 * own body, never from text buttons in the bar.
 *
 * [centerTitle] is for a screen whose title is the subject of the page rather than a section label; the Dive page
 * centres it between its leading back arrow and its trailing neighbour chevrons (ADR 0018, 2026-11-20 amendment).
 * [stackedTitle] moves that title to its own full-width row at 150 % and above, left-aligned to the gutter, because a
 * long title such as "Tauchgang 42" no longer fits between the icons at 200 % and would otherwise break inside a word.
 * [titleModifier] carries a screen's own semantics on the title, such as the heading role. [colors] is for a screen
 * that follows the brief's frame tone (the background behind the bars) rather than Material's surface.
 *
 * [loading] keeps the title's slot in the final layout while the data is still arriving: a skeleton bar in the title's
 * measured line box, after ADR 0064's 150 ms, so neither the bar's height nor its arrival moves the page (FAH-366
 * critique).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiveHubTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    centerTitle: Boolean = false,
    stackedTitle: Boolean = false,
    loading: Boolean = false,
    titleModifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    actions: @Composable RowScope.() -> Unit = {},
) {
    val navigationIcon: @Composable () -> Unit = {
        if (onBack != null) {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.back),
                )
            }
        }
    }
    if (stackedTitle && LocalDensity.current.fontScale >= StackedTitleFontScale) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(colors.containerColor)
                .statusBarsPadding(),
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                CompositionLocalProvider(LocalContentColor provides colors.navigationIconContentColor) {
                    navigationIcon()
                }
                Spacer(Modifier.weight(1f))
                CompositionLocalProvider(LocalContentColor provides colors.actionIconContentColor) {
                    Row(verticalAlignment = Alignment.CenterVertically, content = actions)
                }
            }
            Box(Modifier.fillMaxWidth().padding(horizontal = Spacing.s4, vertical = Spacing.s2)) {
                if (loading) {
                    TitleSkeleton()
                } else {
                    Text(
                        title,
                        modifier = titleModifier.fillMaxWidth(),
                        style = DiveHubText.section,
                        color = colors.titleContentColor,
                    )
                }
            }
        }
    } else if (centerTitle) {
        CenterAlignedTopAppBar(
            title = {
                if (loading) {
                    TitleSkeleton()
                } else {
                    Text(title, modifier = titleModifier)
                }
            },
            navigationIcon = navigationIcon,
            actions = actions,
            colors = colors,
        )
    } else {
        TopAppBar(
            title = {
                if (loading) {
                    TitleSkeleton()
                } else {
                    Text(title, modifier = titleModifier)
                }
            },
            navigationIcon = navigationIcon,
            actions = actions,
            colors = colors,
        )
    }
}

/**
 * The title's placeholder: it holds the final title's measured line box (font padding included), stays blank for
 * ADR 0064's 150 ms, then shows the skeleton bar. Measuring the box rather than using the type scale's line height
 * keeps the stacked top bar from growing when the real title arrives (FAH-403).
 */
@Composable
private fun TitleSkeleton() {
    val density = LocalDensity.current
    val measurer = rememberTextMeasurer()
    val lineBox = with(density) { measurer.measure(" ", style = DiveHubText.section).size.height.toDp() }
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(SkeletonDelayMs)
        visible = true
    }
    Box(Modifier.width(TitleSkeletonWidth).height(lineBox)) {
        if (visible) {
            SkeletonBar(
                Modifier
                    .fillMaxSize()
                    .testTag(TopBarTitleSkeletonTag),
                height = lineBox,
                alpha = rememberSkeletonAlpha("top bar title"),
            )
        }
    }
}
