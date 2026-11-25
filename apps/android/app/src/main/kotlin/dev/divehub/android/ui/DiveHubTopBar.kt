package dev.divehub.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.divehub.android.R
import dev.divehub.android.ui.components.SkeletonBar
import dev.divehub.android.ui.components.SkeletonDelayMs
import dev.divehub.android.ui.components.rememberSkeletonAlpha
import kotlinx.coroutines.delay

/** A title's skeleton bar, sized in the final title's line box so the bar never changes the bar's height. */
private val TitleSkeletonWidth = 120.dp

/**
 * The stacked title reserves two lines at 150 % and above. A Dive's title is either "Dive 42" or its start date, and the
 * date wraps to two lines at 200 % on every phone width (the German one too at 150 % on a narrow phone). Reserving the
 * wrapped height while the data is still arriving keeps the bar from growing when the title lands; a one-line title
 * simply has room for a line under it (FAH-415).
 */
private const val StackedTitleLines = 2

/** The skeleton's handle for the arrival test; the bar itself stays decorative for TalkBack. */
internal const val TopBarTitleSkeletonTag = "top_bar_title_skeleton"

/** The stacked bar's icon row, so a test can check that an empty state's bar ends with it. */
internal const val TopBarStackedIconsTag = "top_bar_stacked_icons"

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
    if (stackedTitle && LocalDensity.current.fontScale >= LargeTextFontScale) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(colors.containerColor)
                .statusBarsPadding(),
        ) {
            Row(Modifier.fillMaxWidth().testTag(TopBarStackedIconsTag), verticalAlignment = Alignment.CenterVertically) {
                CompositionLocalProvider(LocalContentColor provides colors.navigationIconContentColor) {
                    navigationIcon()
                }
                Spacer(Modifier.weight(1f))
                CompositionLocalProvider(LocalContentColor provides colors.actionIconContentColor) {
                    // The normal bar's icon row insets from the edge; match it so the stacked bar is not edge-to-edge
                    // against the guttered title below (FAH-506 critique).
                    Row(
                        modifier = Modifier.padding(end = Spacing.s1),
                        verticalAlignment = Alignment.CenterVertically,
                        content = actions,
                    )
                }
            }
            // An error or offline empty state has no title to reserve: the bar ends with its icon row instead of
            // standing on a blank two-line slot (FAH-427).
            if (loading || title.isNotEmpty()) {
                val lineBox = titleLineHeight()
                Box(Modifier.fillMaxWidth().padding(horizontal = Spacing.s4, vertical = Spacing.s2)) {
                    if (loading) {
                        TitleSkeleton(lineBox = lineBox, lines = StackedTitleLines)
                    } else {
                        // The slot keeps the wrapped height while the title settles, so a one-line title never
                        // shrinks the bar and a wrapped one never grows it (FAH-415). Centre the title and the
                        // skeleton in that slot so an unused line reads as padding (FAH-427).
                        Box(
                            Modifier.fillMaxWidth().heightIn(min = lineBox * StackedTitleLines),
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            Text(
                                title,
                                modifier = titleModifier.fillMaxWidth(),
                                style = DiveHubText.section,
                                color = colors.titleContentColor,
                            )
                        }
                    }
                }
            }
        }
    } else if (centerTitle) {
        CenterAlignedTopAppBar(
            title = {
                val lineBox = titleLineHeight()
                if (loading) {
                    TitleSkeleton(lineBox = lineBox, lines = 1)
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
                val lineBox = titleLineHeight()
                if (loading) {
                    TitleSkeleton(lineBox = lineBox, lines = 1)
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

/** The section style's measured line box (font padding included), the unit a title's slot is reserved in. */
@Composable
private fun titleLineHeight(): Dp {
    val density = LocalDensity.current
    val measurer = rememberTextMeasurer()
    return remember(measurer, density) {
        with(density) { measurer.measure(" ", style = DiveHubText.section).size.height.toDp() }
    }
}

/**
 * The title's placeholder: it holds [lines] of the final title's measured line box, stays blank for ADR 0064's
 * 150 ms, then shows the skeleton bar in the first line. Measuring the box rather than using the type scale's line
 * height keeps the stacked top bar from growing when the real title arrives (FAH-403, FAH-415).
 */
@Composable
private fun TitleSkeleton(lineBox: Dp, lines: Int) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(SkeletonDelayMs)
        visible = true
    }
    Box(
        Modifier.width(TitleSkeletonWidth).height(lineBox * lines),
        contentAlignment = Alignment.CenterStart,
    ) {
        if (visible) {
            SkeletonBar(
                Modifier
                    .fillMaxWidth()
                    .testTag(TopBarTitleSkeletonTag),
                height = lineBox,
                alpha = rememberSkeletonAlpha("top bar title"),
            )
        }
    }
}
