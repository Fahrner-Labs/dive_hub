---
title: Android design system
summary: The web design system translated to the native Android client: 4 dp spacing, a Poppins type scale, one comfortable density, surfaces, components, dark theme and touch rules.
status: living
date: 2026-11-21
---

# Android design system

The Android client is the same product and the same visual identity as the web client, on a phone. This brief
translates [the web design system](design-system.md) into Android and Compose terms; it is binding for every Android
screen. [ADR 0064](../decisions/0064-ui-overhaul.md) fixes the look (framed sheet, ink button, water blue only for
links, focus and data) and [ADR 0073](../decisions/0073-android-app-in-monorepo-kotlin-compose.md) fixes the stack
(Kotlin, Jetpack Compose, Material 3). Material 3 is the base, not the look: the rules below replace its default
colour, type, shape and density.

Code today: `apps/android/app/src/main/kotlin/dev/divehub/android/ui/Theme.kt` (colour) and `Type.kt` (type).
The token and component work adds `Spacing.kt` (spacing) and `ui/components/` (the shared components); the brief
names them where they are planned. The palette and brand do not change here.

## Direction

**Subject:** a logbook that a family, club or dive centre hosts for itself. Its main job is reading and keeping dive
records: dense numbers, profiles, lists.

**One sheet, the water face and the profile as the signature.** On Android the scrolling content is the white sheet;
the frame is the background behind the top and bottom bars and the system bars. Sections are parts of the sheet, not
cards. The depth profile is the one bold element. The diver-down flag is the brand mark. The primary button is ink
(near-black, inverted in the dark); the accent colour means only "link, focus or data". One typeface, Poppins.

Checked against generic defaults: no Material purple, no default pill-shaped buttons, no identical elevated cards,
no gradients as decoration (only the depth profile), no monospace for data (Poppins figures, right-aligned), no
all-caps labels.

## Spacing and density

### The 4 dp grid

All spacing is a step on the same 4 dp grid as the web's `--space-*` scale. The token number is the web token's
number, so a value is found the same way in both codebases.

| Token | dp | Web token | Use |
|---|---|---|---|
| `Spacing.s1` | 4 | `--space-1` | hairline gaps, icon-to-label, tight pairs |
| `Spacing.s2` | 8 | `--space-2` | heading to content, chip gaps, between adjacent targets |
| `Spacing.s3` | 12 | `--space-3` | row vertical padding, control inner padding |
| `Spacing.s4` | 16 | `--space-4` | screen gutter, field gaps, section padding on a phone |
| `Spacing.s5` | 24 | `--space-5` | above a section hairline, dialog and sheet padding, empty states |
| `Spacing.s6` | 32 | `--space-6` | large separation, tablet gutter |
| `Spacing.s7` | 48 | `--space-7` | a page's largest separation, empty-state blocks |

Rules:

- Every padding, margin, gap and spacer comes from `Spacing`; a screen does not use a literal `.dp` for spacing.
- Fixed dimensions that are not spacing — a 24 dp icon, a chart height, a label column — are named constants in
  that component. They are not `Spacing` steps and do not drift into other components.
- The screen gutter is `s4` (16 dp). A dialog or bottom sheet pads `s5` (24 dp). A section puts `s5` above its
  hairline and `s2` between its heading and its content. A row keeps `s3` (12 dp) above and below.
- Content is not squeezed to fit. If a screen needs more room, it pages or scrolls.

**One comfortable density — binding for every Android screen.** There is no compact mode, density setting or
per-screen tightening. List, table and fact rows keep `s3` (12 dp) vertical padding; a screen that wants more rows
pages, scrolls or splits into another screen. This is the web rule from ADR 0019: a logbook is read, not scanned in a
spreadsheet.

## Type

Poppins, one family, weights 400, 500 and 600. The web's six sizes are the Android scale. Material's default type
scale is not used.

| Role | Size / line | Weight | Use | Material slot |
|---|---|---|---|---|
| `hero` | 32 / 40 sp | 600 | a Dive's headline facts, a screen's one big number | `displaySmall` |
| `title` | 28 / 36 sp | 600 | a screen title where there is no top app bar; an empty-state title | `headlineLarge` |
| `section` | 20 / 28 sp | 600 | a section heading, a top app bar title | `titleLarge` |
| `body` | 16 / 24 sp | 400 | body, inputs, fact values | `bodyLarge` |
| `bodyStrong` | 16 / 24 sp | 500 | a row title, an emphasised value | `titleMedium` |
| `label` | 14 / 20 sp | 500 | buttons, field labels, chips | `labelLarge` |
| `metaStrong` | 12 / 16 sp | 500 | counts, badges, a short state | `labelMedium` |
| `meta` | 12 / 16 sp | 400 | dates, supporting text, secondary detail | `labelSmall` |

Rules:

- Every Material slot is set. `displayLarge` and `displayMedium` alias `hero`; `headlineMedium` and
  `headlineSmall` alias `title` and `section`; `titleSmall` aliases `bodyStrong`; `bodyMedium` aliases `body`;
  `bodySmall` aliases `meta`. No slot falls back to a Material default. Screens use the semantic styles
  (`DiveHubText.*`), not Material slot names.
- The sizes are the web's, not Material's defaults: `hero` 32 (not `displaySmall` 36), `title` 28 (not
  `headlineLarge` 32), `section` 20 (not `titleLarge` 22). A screen title is `title`, never a Material slot name.
- Large headings use −0.01em tracking. Line height belongs to the style; a screen does not override it.
- 700 is not part of the scale. Emphasis is 600 plus size, not a fourth weight. Do not use Poppins Bold for UI
  text.
- Numbers in data (depths, durations, times, counts, temperatures, pressures, SAC and profile samples) line up by
  being end-aligned in their column. Never a monospace font. Poppins, the one family, has no tabular (`tnum`)
  feature in its 400/500/600 builds, so `fontFeatureSettings = "tnum"` is a no-op and there is no tabular helper.
  If a Poppins build that carries `tnum` is bundled, apply it at the data call sites (`FactRow` value, `DiveRow`
  figures), not to all body text.
- Body text is 16 sp. The smallest UI text is 12 sp (`meta`) and is never used for body text or inputs. This keeps
  the web's floors and keeps German legible.
- Muted is a colour, not a size. A lead sentence stays at `body`; `meta` is for secondary text only.
- Use `sp` for text. Support font scale up to 200% without clipping: a row grows, it does not truncate a label to
  fit.
- **At 150 % and above, text takes the full width instead of keeping a two-column geometry.** A top app bar title
  stacks under its icons, left-aligned to the gutter; a `FactRow` puts its value under its label, start-aligned.
  Below 150 % the Dive title stays stable — normally centred, but a title longer than the band it has clamps toward
  the leading side (2026-11-25 amendment) — and facts keep their label/value columns.
  The run of text grows instead of breaking inside a word ([ADR 0018](../decisions/0018-icons-and-motion.md),
  2026-11-21 amendment).

## Shape and surfaces

### Radius by hierarchy

| Token | dp | Use |
|---|---|---|
| `Radius.sm` | 2 | small marks, the diver-down flag's inner detail, hairline ends |
| `Radius.control` | 8 | buttons, text fields, selects, segmented controls, the profile chart's frame |
| `Radius.box` | 10 | boxes inside the sheet: a notice, attention, an inset block |
| `Radius.sheet` | 16 | the sheet where its corner is visible, dialogs, bottom sheets |
| fully round | 50% | chips, badges, avatars, a floating count pill |

No 12 dp, 14 dp or 20 dp radii. Material's fully rounded button is replaced with `Radius.control`; a chip stays a
pill because it is a chip.

### Surfaces

Adapted from the web's "one sheet, panels as sections divided by hairlines":

- The app is one sheet on the frame. The scrolling content is `surface`; the frame is `background`. The top app bar
  and bottom bar sit on the frame, not on a second sheet.
- A `Section` is a part of the sheet, not a box. It has no card, outline, elevation or horizontal padding of its own.
  It is a heading, its content, and a hairline with `s5` above it, separating it from the section before. The first
  section on a screen has no hairline. The screen's content column owns the `s4` gutter.
- A box inside the sheet is one tone step (`surface-sunken`) with `Radius.box`. Something waiting for the User uses
  `accent-soft` (`Section attention`). Do not nest boxes: a box inside a box is a new section or a plain row.
- Elevation is only for things floating above the page: dialogs, bottom sheets and popovers. Sections, bars and rows
  are flat. Do not use `Card` or `ElevatedCard` for a section.
- On a phone the sheet is edge-to-edge; its 16 dp radius is not drawn on a full-bleed screen. It appears on dialogs,
  bottom sheets and larger windows where the sheet's edge is visible.
- A hairline is 1 dp of `border` and is decorative. Anything that marks a control's boundary uses `border-strong`.
  Never use `border` for a control outline.

## Colour and dark theme

The palette is fixed by ADR 0064; this section only maps it to Android roles. The full semantic token list is the
web's `tokens.css`; `DiveHubColors` mirrors every token the app uses. Dark mode follows the system and is a
near-neutral with a faint cool tint, not navy.

| Role | Light | Dark | Web token |
|---|---|---|---|
| Frame | `#EEF3F6` | `#0B0D0F` | `--color-bg` |
| Sheet | `#FFFFFF` | `#131619` | `--color-surface` |
| Sunken box | `#F4F7F9` | `#1A1E22` | `--color-surface-sunken` |
| Text | `#0E1A23` | `#E9EEF2` | `--color-text` |
| Muted text | `#52626F` | `#A1ACB5` | `--color-text-muted` |
| Hairline | `#E1E7EC` | `#22272C` | `--color-border` |
| Control border | `#6E8497` | `#6B7782` | `--color-border-strong` |
| Primary (ink) | `#0E1A23` | `#E9EEF2` | `--color-primary` |
| On primary | `#FFFFFF` | `#0B0D0F` | `--color-on-primary` |
| Accent | `#0F5E8C` | `#62B6E6` | `--color-accent` |
| On accent | `#FFFFFF` | `#0B0D0F` | `--color-on-accent` |
| Accent soft | `#E1EEF6` | `#14303F` | `--color-accent-soft` |
| Danger | `#B3122B` | `#FF7A7A` | `--color-danger` |
| On danger | `#FFFFFF` | `#0B0D0F` | `--color-on-danger` |
| Danger soft | `#FBE9EC` | `#3A1620` | `--color-danger-soft` |
| Success | `#2A7150` | `#5CC593` | `--color-success` |
| Success soft | `#E5F3EC` | `#10301F` | `--color-success-soft` |
| Focus | `#0F5E8C` | `#62B6E6` | `--color-focus` |
| Brand flag | `#C8102E` | `#C8102E` | `--color-brand-flag` |

**The ink button and the accent are different roles.** The web's `--color-primary` is ink (the filled button), and
`--color-accent` is water blue (links, focus, data). The current `Theme.kt` maps Material's `primary` slot to the
accent, which does not match ADR 0064's ink primary. This brief treats that as a role-mapping correction, not a
palette change. In this brief:

- `ColorScheme.primary` / `onPrimary` are the ink button and its text.
- `secondary` / `onSecondary` carry the accent and its text; `primaryContainer` / `secondaryContainer` carry
  `accent-soft` for selected controls.
- Our own components read `DiveHubColors.accent`, `.focus`, `.border` and `.success`. A Material widget that would
  colour a link or a focus ring with `primary` is re-coloured or replaced.
- The brand flag (`tertiary` today) is used only by `BrandMark`, never as a UI accent.

Other rules:

- **Contrast.** The web's measured WCAG 2.2 AA pairs are the gate; the same hex values give the same ratios. Text on
  sheet, muted text, accent on sheet, text on primary, text on danger and control borders are re-measured if a value
  changes. A new palette step is derived with Adobe Leonardo, never invented. The web's `contrast.test.ts` is the
  model; the Android token object gets a unit test for the same pairs when it lands.
- **Dark is not a tint of light.** Use the dark column, including the dark danger (`#FF7A7A`), success (`#5CC593`)
  and accent (`#62B6E6`). Never use a light-mode hex on a dark surface.
- **Data colours are web tokens, not new colours.** The depth ramp is `#8FD3E8` → `#0F5E8C` in the light and
  `#62B6E6` → `#1B4867` in the dark. Ascent by speed is `#9A7400`, `#C2570C`, `#8A2F0A` in the light and
  `#E6C04A`, `#F29A4A`, `#FFB9A3` in the dark. The logbook's `ProfileSketch` is wrong in both places: its ascent
  constants are `#E0A100`, `#E06A00`, `#C81E1E`, and its depth fill reads `primaryContainer` → `primary`, so today the
  deep end only looks right because `primary` is the accent, while the shallow end (`accent-soft`, `#E1EEF6`) is
  already wrong. The depth gradient and the ascent colours both move onto these tokens in the same pass, before or
  with the ink `primary` remap ([how this brief lands](#how-this-brief-lands)).
- **System bars.** The status bar and navigation bar use the frame colour; icon appearance follows the scheme. The
  app draws edge to edge; the bars' backgrounds may extend under the system bars, content does not.
- **No hardcoded colour.** A `Color(0x…)` outside `Theme.kt` is a bug. A screen reads `MaterialTheme.colorScheme` or
  `DiveHubColors`.
- **No Material default surfaces.** Set the `surfaceContainer*` roles to the sheet and sunken tones and turn off the
  surface tint; a `Card`, `BottomSheet` or dialog must not introduce a third tone.
- **Success has no Material slot.** It is an extension today (`successColor()`); use `DiveHubColors.success` once
  the token object exists.

## Components

These live in `apps/android/app/src/main/kotlin/dev/divehub/android/ui/components/` once the component work lands;
until then the screens keep their current local composables. A screen then uses these, not a hand-rolled row,
heading or empty state. The table maps each one to the web component it translates.

| Android component | Web counterpart | Rules |
|---|---|---|
| `Section` | `Panel` | A titled section of the sheet: heading, optional action, content. A hairline with `s5` above separates it from the section before. No card, outline or elevation. `attention` paints it with `accent-soft` for something waiting for the User; a single form uses the same section with a narrow content width. An action ends on the column's outer edge while it shares the heading's line, and starts the line below once it wraps; the heading's measured width and the action's 48 dp target decide it. |
| `FactRow` | facts in `PageHeader`, table rows | A label–value line: label in `label` and muted, value in `body` and end-aligned. An edited mark and a note sit under the value in `meta`. Vertical padding `s3`. At 150 % and above the value starts under the label, so neither column has to break inside a word. |
| `DiveRow` | `DiveList` | The logbook's row: title in `bodyStrong`, aligned depth, duration and facts, participant avatars. The whole row is the target; month headings sit above the rows. |
| `EmptyState` | the empty and not-found rule | Says why it is empty and offers the way on. No dead end. The action is a `Button` or a quiet `TextButton`. |
| `Button` | `Button` | `primary` is ink; `secondary` is the sheet tone with a control border (outlined) on the button role, the same `label` type and 40 dp visual height as the primary; `quiet` is text-like; `danger` confirms a destructive action. A field-like action that opens a picker or adds a line to a form (`FieldButton`) keeps `body` type and a 48 dp visible height. A 48 dp target; `small` may look 40 dp but keeps the 48 dp target. One primary per section. No Material pill. |
| `IconButton` | the icon-only exceptions | Icon-only is the exception, and this brief owns the Android ones: the top app bar's back and previous/next are platform navigation, alongside a dialog's close, a password field's eye from the ADR 0018 amendment, the logbook's filter action, the settings account row's sign-out, the Dive page's edit action (the editor's pencil, from the FAH-501 reversal of the FAH-366 placement), and the live scanner's close and torch actions over the camera. Full accessible names and a 48 dp target. The Dive page keeps `s2` between its neighbour chevrons and the pencil, because a tap on a boundary must not page instead of edit. Chevrons say "(unavailable)" and are disabled at the ends. A selected-filter count sits on the filter action as a `Badge`, never colour alone; it counts the filters the Diver selected, and when a server-only filter cannot be applied offline the sheet's note says the list shows the stored Dives without it. |
| `TextField` | `TextField` | Label, supporting text and error. `body` 16 sp, `Radius.control`, a visible focus ring. No floating-label-only field. |
| `NumberField` | `NumberField` | Numeric keyboard, unit suffix, the UI language's format, value committed on blur. Figures end-aligned. |
| `PasswordField` | `PasswordField` | A field with an eye toggle inside it, a full accessible name and autofill intact. Never a plain password field. |
| `SearchField` | `SearchList` | A search field with results under it, the typed part marked, a status line, and a way on when nothing matches. Picking a row saves. |
| `ChoiceRow` | `Checkbox`, `RadioGroup`, `Switch` | A full-row target with label and supporting text. A switch takes effect at once; a checkbox is a form choice; radios are for up to five fixed options. 48 dp. |
| `FilterChip` | `ToggleChip` | A pill with its count, pressed with a check mark and `selected`, never colour alone. The group wraps; a zero count is not offered unless pressed. |
| `Badge` | `Badge` | A short state: neutral, success or danger. States never use the accent colour. |
| `Avatar` | `Avatar` | Initials in one of three tones that tell people apart and mean nothing. Decorative: the name stands beside it as text. |
| `Notice` | `Notice` | What just happened: info, success or danger, announced once. One line and an action. |
| `Dialog` | `Dialog`, `ConfirmDialog` | `Radius.sheet`, a scrim, focus kept inside, a close button with a name. A destructive action asks first and says what will happen. |
| `BottomSheet` | `ActionMenu` on a phone | Secondary actions in a sheet: 16 dp top corners, 48 dp rows, a hairline, a drag handle. The panel keeps only its main action. The logbook's filter panel is the other use: a heading with wrapped `FilterChip`s, padded `s5`, scrolling when the content is taller than the sheet, with no rows or hairline. |
| `TopAppBar` | the web shell's top bar | Frame tone, flat, no shadow. Title in `section` (20 sp); a screen without a top bar uses `title` (28 sp). Navigation icons on the start; the main action does not live here, except the Dive page's edit pencil as a trailing action (ADR 0018, 2026-11-25 amendment). The Dive page keeps its title stable between the back arrow and the neighbour chevrons (ADR 0018, 2026-11-20 amendment): the pencil's slot is reserved whether or not the server takes writes, and while the page loads, so writability never moves the title. A long title such as the German "Tauchgang 42" is clamped toward the leading side of that band rather than staying on the screen's centre; at 150 % and above the title stacks under the icons, left-aligned to the gutter (ADR 0018, 2026-11-21 amendment). A stacked title reserves two lines and sits centred in them, so the one line it does not use reads as padding: a Dive with no number is titled by its start date, which wraps on a phone, and the bar must not change height when the title arrives. An empty state without a title (loading is not one: it keeps the slot for its skeleton) has no title row at all, so an error or offline bar ends at its icon row. |
| `BottomBar` | the web shell's bottom bar | The signed-in top-level places (Logbook, Tools, Settings today), frame tone, flat, labels always visible. |
| `Skeleton` | `Skeleton` | Flat bars in the final layout, shown after 150 ms. No "Loading…". A hidden label and a busy state for TalkBack; content fades in over the bars. |
| `ProfileChart` | the depth profile | The one bold element: depth ramp, ascent colours, a text summary and a "profile as a table" view. Scrubbable, with the scrub readout as text. |
| `SyncLine` | the web's live-arrivals and sync rules | A quiet `meta` line for syncing, offline or cached. No badge, connection dot or toast. |
| `BrandMark` | `BrandMark` | The diver-down flag. The mark only; never a UI accent. |
| `Icon` | `Icon` | One set, 24 dp, 2 dp stroke where drawn, beside a visible label except the named exceptions. Decorative icons are hidden from TalkBack. |

The web table's `Form`, `PageHeader`, `Table`, `Disclosure`, `CopyField`, `Select`, `TextArea`, `DateTimeField` and
`ErrorBoundary` have no Android component yet; they are deferred until a screen needs them and are then built from
the primitives above (a form is a `Section` with a narrow content width, a page header is the top app bar or the
screen's `title`, and a table becomes `FactRow`/`DiveRow` lines or a card per row at phone width).

## Rules every Android screen follows

1. **One title per screen.** It is the top app bar's title or the one `Section` heading that names the screen. It
   is marked as a heading for TalkBack. Headings inside a section go one level down.
2. **One sheet, sections not cards.** Sections are separated by hairlines and `s5`; no `Card`, no outline, no
   elevation. A box is one tone step and never nests.
3. **Tokens only.** Spacing from `Spacing`, type from `DiveHubText`, colour from `DiveHubColors` (or the Material
   role that carries it). No literal `.dp` for spacing, no `TextStyle` left at a Material default, no `Color(0x…)`.
4. **One comfortable density.** Rows keep `s3`; no compact mode and no tightening to fit. Page, scroll or split.
5. **Touch targets are 48 × 48 dp.** A control may look smaller but keeps a 48 dp target; adjacent targets keep
   `s2` (8 dp) between them; a whole row that opens a detail is one target. The top app bar's back and
   previous/next pair are platform navigation and sit flush; any other action placed beside them, such as the Dive
   page's edit pencil, keeps the `s2`.
6. **One main action per section.** The rest go behind an overflow menu or a bottom sheet. A destructive action is
   never the primary action of a section.
7. **States are designed.** A screen that loads data shows skeleton bars in the final layout, not a centred
   spinner; a determinate background sync may keep a progress indicator. An empty or not-found screen says why and
   offers the way on; an error says what happened and what to do.
8. **Destructive actions ask first.** Split, revoke, disable, sign out everywhere and demoting yourself use a
   confirmation that says what will happen. An easily undone action offers Undo instead.
9. **Dark mode follows the system.** There is no separate dark layout and no in-app theme switch. Both schemes are
   checked for every screen.
10. **Text scales to 200%.** Use `sp`; let rows and headings grow; never fix a text container's height or truncate a
    label to fit.
11. **Everything works at 320 dp wide.** No horizontal scroll. Long names, e-mail addresses and device serials wrap
    or truncate with the full value reachable in the detail. Test 320, 360, 390 and 412 dp.
12. **TalkBack is part of the screen.** Every control has a name, role and state; a field has its label and hint; a
    notice is announced; a heading is a heading; decorative art is hidden.

## Touch and phone rules

The web's Touch section is the model, adapted to Android:

- **Safe areas.** Draw edge to edge, then pad content with `WindowInsets.safeDrawing` for the bars and the cutout,
  and `imePadding()` on a form. No text or control sits under the status bar, a notch or the gesture bar. A background
  may extend under a bar; content may not.
- **Keyboard.** A form scrolls the focused field above the keyboard. Set `KeyboardOptions` for the field's type
  (number, decimal, e-mail, password) and its `imeAction` (Next, Done, Search, Send). A field is never hidden behind
  the keyboard, and editable text is never below `body` 16 sp.
- **Back.** The system back gesture and predictive back work. A top bar's back arrow is named "Back" and does what
  the system back does. Unsaved edits use the leave guard, not a surprise confirmation on every back.
- **Reach.** The main action sits in the bottom half of the screen or in the bottom bar, within a thumb's reach. The
  top bar holds the title and navigation, not the main action. A destructive action sits at the bottom of its sheet.
- **Scrolling.** One vertical scroll per screen; the logbook keeps pull-to-refresh. No nested vertical scroll. The
  content scrolls beneath the bars and is padded by the insets, so no content is obscured.
- **No hover, no tooltip.** Press feedback is immediate (ripple or pressed state); information is text, never a
  long-press tooltip. Control labels do not select on a long press; content text stays selectable.
- **Motion.** Platform transitions, 120–200 ms, only where they explain where something came from. Respect the
  system's reduced-motion setting; no essential information depends on motion. Pages, rows, the profile and focus
  never animate.
- **Hardware.** Touch feel, the keyboard, insets and predictive back are checked on a real phone. Emulation does not
  show them.

## Writing, localization and units

The web brief's [Writing](design-system.md#writing), [Localization](design-system.md#localization) and
[Units](design-system.md#units) sections are binding for the Android client too. In addition:

- Every user-facing string is an Android string resource (`res/values/strings.xml`, `values-de/strings.xml`); no
  hardcoded user text in a composable. German uses informal **du**.
- API refusals are shown by their `code`, translated under `errors.*`, `import.reason.*` and `import.errorCode.*`.
- Dates, numbers and units come from the shared formatting code, not from `String.format` in a screen. The unit
  system is the User's preference; unset follows the region.
- A button says what happens; the result uses the same verb. Sentence case. Errors say what happened and what to
  do, and do not apologize.

## Accessibility baseline

WCAG 2.2 AA, as on the web, plus the Android platform rules:

- Visible focus on everything interactive, with a `focus`-coloured indicator on both schemes.
- 48 × 48 dp touch targets; 200% font scale; no sideways scroll at 320 dp.
- TalkBack names, roles and states on every control; headings marked; errors and notices announced; decorative
  icons hidden.
- Contrast measured for both schemes; control boundaries at least 3:1.
- Reduced motion respected; nothing moves that carries meaning.

## Checking the look

Roborazzi goldens are the regression floor, not the design review. For every Android UI change:

- Update only the affected goldens, never bulk re-record. Re-record after an intended visual change is the
  sanctioned flow (`apps/android/README.md`).
- Put the updated goldens through a Design Consultant critique before the PR, as the Android design-critique gate
  requires.
- Check every screen at 320, 360, 390 and 412 dp, in light and dark, in English and German, with crowded data (long
  names and e-mail addresses) and at 200% font scale.
- Check touch, the keyboard, insets and back on a real phone before calling it done.

## How this brief lands

This is a specification, not a big-bang rewrite. The Android code moves onto it as its screens are touched:

- `Theme.kt`: the ink `primary`, the accent as its own role, the border, success and surface-container roles, the data
  colours, and no hardcoded colour outside the file.
- `Type.kt`: the scale above and every Material slot set.
- `Spacing.kt`: `s1`…`s7` on the 4 dp grid.
- `ui/components/`: `Section`, `FactRow`, `DiveRow`, `EmptyState` first, then the other components as a screen
  needs them.
- Existing screens: replace hardcoded `dp` with `Spacing`, Material default text with `DiveHubText`, and direct
  colours with the token roles, as each screen is redesigned or next touched. The logbook's `ProfileSketch` depth
  gradient and ascent colours move onto the depth and ascent tokens in the same pass, before or with the ink
  `primary` remap.
- No new UI library, no change to the palette or the brand, and no ADR: ADR 0064 and ADR 0073 already govern this
  work.
