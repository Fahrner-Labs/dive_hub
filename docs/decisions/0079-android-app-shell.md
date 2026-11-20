---
title: "ADR 0079: Android app shell — Navigation Compose with type-safe routes"
summary: The Android app replaces its hand-rolled rememberSaveable/when navigation with androidx.navigation:navigation-compose and Serializable routes. The session state machine still chooses between instance, signin and main; Navigation renders that state and owns the back stack.
status: proposed
date: 2026-11-20
---

# ADR 0079: Android app shell

## Status
Proposed – 2026-11-20. Implements task T2 of the Android v0.1.0 plan on the Paperclip issue
[FAH-316](https://paperclip.privatenode.de/FAH/issues/FAH-316), from the accepted plan
[FAH-312](https://paperclip.privatenode.de/FAH/issues/FAH-312) §4. The owner accepts it by merging its pull request:
`docs/decisions/**` is CODEOWNERS-protected.

## Context
The whole Android UI graph is hand-rolled in `apps/android/app/src/main/kotlin/dev/divehub/android/ui/DiveHubApp.kt`:
`rememberSaveable` flags, a `when` switch, and three manual `BackHandler`s, plus a hoisted `toolPage` flag added for
one nested flow ([FAH-276](https://paperclip.privatenode.de/FAH/issues/FAH-276)). It works, but every new screen adds
ad-hoc state; there are no deep links, no type safety, and the back rules (ADR 0042) live in comments. The next two
tasks add a settings destination and a bottom bar (T3) and a diagnostics section (T4), which would multiply that
state.

The app is Kotlin 2.3 + Compose (ADR 0073) with a KMP `:shared` module (ADR 0075). Navigation is a UI-shell concern,
not shared logic.

## Decision

### D1 — Navigation Compose with type-safe routes
Use `androidx.navigation:navigation-compose` (2.10.x) and its type-safe API: every destination is a
`@Serializable` route (`data object` or `data class` with route arguments), registered with `composable<T>()` inside
a `NavHost`. The app module applies the Kotlin serialization plugin. Neighbour navigation on a Dive, the logbook
page and the open tool page are route arguments or saved state, so Navigation restores them after process death.

### D2 — The graph
```
root NavHost
├── instance          (InstanceForm — no instance configured)
├── signin            (SignInForm — instance set, no session)
└── main              (signed-in nested graph)
    ├── logbook       (start destination)
    ├── tools         (ToolsRoute incl. gas plan / lead / loadings pages)
    └── dive/{id}     (dive detail; pushed above logbook or the tool page that opened it)
```

The Dive is a destination of `main`, not nested under `logbook`: a Dive is opened from the logbook and from a tool
page, and its back stack entry's origin is what decides where back returns. A neighbour replaces the Dive entry, so
the back stack keeps one Dive above that origin. T3's bottom bar is a `Scaffold` inside `main` and the settings destination joins it; neither changes the shell's
top level. T4 adds content to settings only.

### D3 — The session state machine still owns the top level
`MainViewModel.state` (loading / updateRequired / signed-in user / instance URL) decides whether `instance`,
`signin` or `main` is shown. `sessionDestination(state)` maps it to a route; a `LaunchedEffect` navigates when that
route differs from the current one. Loading and the UpdateRequired gate are full-screen overlays above the graph,
not destinations — the graph stays composed so its restored back stack survives the initial load. Sign-out wipes the
per-User view models and the top-level navigation pops the whole back stack to `signin`; no separate pop call is
needed.

### D4 — Back semantics (ADR 0042) stay
- Back from a Dive returns to the logbook **on the page the Dive is on**: before popping, the Dive destination
  writes `LogbookPaging.pageOf(neighbours.position)` into the logbook entry's `SavedStateHandle`.
- A Dive opened from a tool returns to that tool page: the origin is simply the previous back stack entry.
- A tool page (gas plan, lead, loadings) is pushed above the gas page, so back returns to gas; back from gas returns
  to the logbook, and back from a start destination exits.
- One `BackHandler` remains, on the Dive destination, because the page write must happen before the pop. Everything
  else is the platform's back stack.

### D5 — Why not keep it, and why not Decompose/Voyager
Keeping the hand-rolled state means growing it again for settings and the bottom bar. Decompose and Voyager add a
third-party abstraction the KMP plan does not need yet, with their own back stacks and lifecycle. Navigation Compose
is first-party, type-safe, gives saved-state restoration for free, and is the path the Jetpack ecosystem documents.

## Consequences
- New screens are a route plus a `composable` block; the bottom bar and settings in T3 are additive.
- The app gains one ABI-sized dependency (Navigation) and the serialization plugin on `:app`; `:shared` is untouched.
- Process death restores the Dive id, the logbook page and the open tool page, not just the top-level screen.
- Deep links are available by adding `deepLinks` to the routes; none are defined yet, so addresses stay app-internal.
- Tests can exercise the shell with stand-in screens: the graph, back stack and restoration no longer require a
  session or a store. Roborazzi screenshot tests still render screens directly and stay unchanged.
- The logged-in graph is still not a separate task/activity; the session remains the single source of truth.
