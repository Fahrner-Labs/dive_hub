package dev.divehub.android.ui.scan

import androidx.camera.core.CameraInfoUnavailableException
import androidx.camera.core.CameraUnavailableException
import androidx.camera.core.InitializationException
import java.util.concurrent.ExecutionException
import org.junit.Assert.assertEquals
import org.junit.Test

/** The three camera sentences have to match the cause: a refused permission is not a busy camera (client contract). */
class CameraFailureTest {
    @Test
    fun `a permission failure is blocked rather than busy`() {
        assertEquals(CameraFailure.Blocked, cameraFailure(SecurityException("not allowed")))
        assertEquals(CameraFailure.Blocked, cameraFailure(ExecutionException(SecurityException("not allowed"))))
    }

    @Test
    fun `no camera or a camera that cannot open is missing`() {
        assertEquals(CameraFailure.Missing, cameraFailure(CameraInfoUnavailableException("none")))
        assertEquals(CameraFailure.Missing, cameraFailure(InitializationException("no camera")))
    }

    @Test
    fun `a camera held elsewhere is busy`() {
        assertEquals(CameraFailure.Busy, cameraFailure(CameraUnavailableException(CameraUnavailableException.CAMERA_IN_USE)))
        assertEquals(CameraFailure.Busy, cameraFailure(IllegalStateException("unexpected")))
    }
}
