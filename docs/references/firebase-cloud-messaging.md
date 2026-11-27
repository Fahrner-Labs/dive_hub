---
title: Firebase Cloud Messaging (Android)
summary: How the FCM Android SDK is initialized without google-services.json; its messaging client is the default FirebaseApp only, so the runtime-configured app must be that one.
status: living
date: 2026-11-25
url: https://firebase.google.com/docs/cloud-messaging/android/client
---

# Firebase Cloud Messaging (Android)

## What it is
Google's message transport behind Android push. Dive Hub uses it as a hint channel only
([ADR 0085](../decisions/0085-push-notifications-for-native-clients.md)): a high-priority **data** message
`{ v: 1, kind, id }`; the app fetches the named thing through the authenticated API and composes any notification
on the device.

## What we take from it
- `firebase-messaging` and the Firebase BoM, without the `google-services` Gradle plugin or a bundled
  `google-services.json`. `FirebaseApp` is initialized at runtime from the instance's public
  `GET /api/push/config` answer.
- `FirebaseMessagingService.onMessageReceived` for data messages and `onNewToken` for token refreshes.
- Play services is required; without it the app skips registration and keeps polling.

## Notes
- **The messaging client has no public per-app accessor.** In `firebase-messaging` 26.0.2 (BoM 35.2.0),
  `FirebaseMessaging.getInstance()` is the only public `getInstance`; `getInstance(FirebaseApp)` is
  package-private. FCM therefore always uses the **default** `FirebaseApp`. The runtime-initialized app must be the
  default one, and a change of instance has to delete and rebuild it rather than running a second named app
  alongside. This is why [FAH-520](/FAH/issues/FAH-520)'s "named app" wording is implemented as one rebuilt default
  app keyed by the stored instance URL.
- The Firebase client values (`projectId`, `applicationId`, `apiKey`, `gcmSenderId`) are public by design. The
  service-account key that sends is a server secret and never reaches the app
  ([ADR 0085](../decisions/0085-push-notifications-for-native-clients.md)).
- **`applicationId` is the Google App ID, not the Android package name.** `FirebaseOptions.setApplicationId` takes
  the Firebase Android app's ID from `google-services.json` (`client_info.mobilesdk_app_id`, shaped
  `1:1234567890:android:…`). The package name (`app.divehub.android`) is only what the operator registers in the
  console; passing it to `FirebaseOptions` would point the token at an app that does not exist.
