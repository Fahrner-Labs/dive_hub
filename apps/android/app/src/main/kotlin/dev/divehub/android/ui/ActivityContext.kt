package dev.divehub.android.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

/** The Activity behind Compose's context, for the permission question and the camera's system bars. */
internal tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
