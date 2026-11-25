package dev.divehub.android.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * FCM's entry point when the app is in the background (ADR 0085). Data messages are hints only; the work that fetches
 * and decides whether to notify runs in [PushWorker], because the OS may stop this service as soon as the callback
 * returns. A refreshed token is re-registered the same way.
 */
class DiveHubMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        val hint = PushHint.from(message.data) ?: return
        PushWorker.enqueueHint(this, hint.id)
    }

    override fun onNewToken(token: String) {
        PushWorker.enqueueRegister(this, token)
    }
}
