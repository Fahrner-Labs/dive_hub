---
title: "ADR 0018: Lucide icons beside text; motion only where it explains"
summary: Icons come from lucide-react (ISC), always decorative next to a visible label; motion is a few short CSS animations (menus, dialogs, notices, the drop overlay) on our tokens, and none at all with reduced motion.
status: accepted
date: 2026-10-03
---

# ADR 0018: Lucide icons beside text; motion only where it explains

## Status
Accepted – 2026-10-03. Partly superseded by [ADR 0064](0064-ui-overhaul.md) (2026-11-02): more motion (skeleton
pulse, content arrival, disclosures, hover colours, phone bottom sheets), and loading placeholders now pulse.

## Context
After the [UI review](../research/2026-10-03-ui-review.md), the project owner asked for icons and
motion (2026-10-03) and chose Lucide over Phosphor. The [design system](../spec/design-system.md)
asks for one bold element (the depth profile) with everything else quiet, and the review's rules
(one h1, no icon-only meaning, `prefers-reduced-motion`) must keep holding. The skills
`suggest-lucide-icons`, `find-animation-opportunities` and `review-animations` were installed for
this work ([skills](../skills.md)).

## Decision
**Icons**
- **Library:** `lucide-react` (ISC), imported by name so only the icons used reach the bundle. One
  set, 24 px grid, 2 px stroke, which matches IBM Plex's weight. Phosphor (MIT, six weights) was the
  alternative; one weight is all we need.
- **Always beside text.** An icon adds recognition to a label and never replaces it. There are no
  icon-only buttons. Icons are `aria-hidden` and rendered through `ui/Icon.tsx` (size 1.25 em,
  `currentColor`).
- **Where:** the main navigation, panel actions (Edit, Import files, More), menu items, the account
  menu, notices (info, success, error), pagination, sort direction, the back link, and controls that
  showed a text glyph before (▾, ▲, ▼). Table row actions stay text only, because a dense table reads
  better without them.
- **Names** are chosen and checked with `suggest-lucide-icons`, against the installed version
  (AGENTS.md).

**Motion**
- **Only where it explains** (gate from `find-animation-opportunities`: frequency, purpose,
  budget, function):

  | What | How | Why |
  |---|---|---|
  | Menus, selects, popovers open | 150 ms, `opacity` 0 → 1, `scale(0.96)` → 1, from the trigger (`--trigger-anchor-point`) | spatial link to the trigger |
  | Dialogs open | overlay fades in over 150 ms; the dialog scales from 0.97 over 200 ms, centred | prevents a jarring change |
  | Notices appear | 200 ms fade, 4 px rise | the result of an action arrives instead of popping in |
  | Drop overlay | 150 ms fade | prevents a jarring change |
  | Pressed buttons | `scale(0.97)`, 120 ms, `--ease-out`; not quiet (text-like) buttons | feedback |

- **Never animated:** page changes, table rows when sorting or paging, the depth profile, tab
  switches, focus moves, loading placeholders. These are frequent, or they're data the User reads.
- **Values:** `--ease-out: cubic-bezier(0.23, 1, 0.32, 1)` and the `--duration-*` tokens. Only
  `transform` and `opacity` animate. Entries animate; exits are instant.
- **Reduced motion:** the durations drop to 0, so nothing moves. This is deliberately stricter than
  the skills' "gentler, not zero". A logbook loses nothing without motion.

**Review** (`review-animations`, 2026-10-03): approved. Two fixes applied: the press transition uses
`--ease-out` instead of the weak built-in `ease-out`, and quiet buttons no longer scale. Kept
deliberately: the drop overlay only fades (a backdrop has no trigger to grow from); notices use
keyframes (they don't stack quickly); menus opened with the keyboard animate too (occasional, 150 ms).

## Consequences
- One more runtime dependency (`lucide-react`). Upgrades are checked against renamed icons
  (`trash-2` is already gone from the latest catalogue).
- A browser test checks that animations run with motion allowed and that nothing moves with reduced
  motion. `source-rules.test.ts` keeps text glyphs (▾ ▲ ▼) and icon imports outside `ui/Icon.tsx`
  out of the pages.

## Amendment (2026-11-02): two icon-only controls inside another control
"No icon-only buttons" keeps holding, with two exceptions, both a platform convention sitting inside the thing they act
on, both with a full accessible name:
- **A dialog's close button** (✕ in its title bar, "Close dialog"), as built since the dialogs; Escape and Cancel do the same.
- **A password field's eye** (`PasswordField`, [backlog 0016](../backlog/0016-password-visibility-toggle.md), asked for by
  the owner): inside the field, named "Show: <field label>", its state in `aria-pressed` (the name stays, as the
  WAI-ARIA toggle button pattern asks), the icon switching between eye and eye-off.

Anything else that wants an icon alone needs its own amendment. `source-rules.test.ts` keeps password fields on
`PasswordField`.

## Amendment (2026-11-20): icon-only navigation in the Android dive header
The native Android client's dive page keeps its navigator in one top app bar ([FAH-344](/FAH/issues/FAH-344), from the
2026-11-20 direction): a back arrow at the start, the Dive's title centred, and previous/next chevrons as trailing
actions. They are icon-only, as the platform's top-app-bar convention, each with a full accessible name: the back arrow
"Back", the chevrons "Previous dive" / "Next dive", saying "(unavailable)" and disabled at the ends of the list. The web
client keeps its icons beside text; this exception is the Android top bar only. The swipe's spring-back at the ends is
the gesture's end-stop feedback, not a page-change transition.

## Amendment (2026-11-21): large-text layout in the Android dive page
At 150 % font scale and above, the Android dive page gives text the full width instead of keeping a two-column
geometry: the top app bar's title moves to its own row under the navigation icons, left-aligned to the gutter, and a
`FactRow` puts its value under its label, start-aligned. Both keep their normal type styles; the text grows and never
breaks inside a word. Below 150 % the Dive title stays stable between the icons — normally centred, but a title longer
than the band it has clamps toward the leading side (2026-11-25 amendment) — and facts keep their label/value
columns. This is a layout rule only: the motion and icon decisions above still stand.

## Amendment (2026-11-22): icon-only sign-out in the Android settings account row
The native Android client's settings page puts sign-out on the "Signed in as" row as a trailing icon button
([FAH-426](/FAH/issues/FAH-426), from the 2026-11-22 RC feedback): the exit icon (`ExitToApp` from the core icon
set), named "Sign out", tinted like the instance row's edit icon. The row itself stays not clickable; only the icon
opens the unchanged confirm dialog that says signing out removes the session and the local data. Sign-out is rare and
destructive, so the dialog stays; the label the icon replaces was redundant with its accessible name and that
confirmation. This exception is the Android settings account row only, and the web client keeps its icons beside text.

## Amendment (2026-11-25): the Android dive page's edit pencil in its top app bar
The native Android client's dive page puts the way into the editor on its top app bar as a trailing pencil icon
([FAH-501](/FAH/issues/FAH-501), from the 2026-11-25 RC feedback): named "Edit", shown only when the server takes
writes, with a 48 dp target. It reverses the FAH-366 critique's quiet inline placement: writing is a primary action on
a writable client, and in the field the inline button disappeared into the scrolling facts and moved with them. The bar
still holds the title and navigation; this is the one main action it carries, because the page's subject is a single
Dive rather than a section. The web client keeps its icons beside text.

The goldens' critique ([FAH-506](/FAH/issues/FAH-506)) then pinned two details: the pencil's slot is reserved at every
writability and while loading, so the title never moves when the server turns out writable, and the pencil keeps `s2`
(8 dp) from the neighbour chevrons because a tap on the boundary must page or edit, never the wrong one. A long
German title ("Tauchgang 42") is clamped toward the leading side of the title band instead of the screen's centre;
the invariant is a stable title, not a centred one.
