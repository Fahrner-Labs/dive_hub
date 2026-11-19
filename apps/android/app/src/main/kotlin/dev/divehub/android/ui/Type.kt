package dev.divehub.android.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import dev.divehub.android.R

val PoppinsFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
)

private fun TextStyle.inPoppins(): TextStyle = copy(fontFamily = PoppinsFamily)

val DiveHubTypography = Typography().run {
    copy(
        displayLarge = displayLarge.inPoppins(),
        displayMedium = displayMedium.inPoppins(),
        displaySmall = displaySmall.inPoppins(),
        headlineLarge = headlineLarge.inPoppins(),
        headlineMedium = headlineMedium.inPoppins(),
        headlineSmall = headlineSmall.inPoppins(),
        titleLarge = titleLarge.inPoppins(),
        titleMedium = titleMedium.inPoppins(),
        titleSmall = titleSmall.inPoppins(),
        bodyLarge = bodyLarge.inPoppins(),
        bodyMedium = bodyMedium.inPoppins(),
        bodySmall = bodySmall.inPoppins(),
        labelLarge = labelLarge.inPoppins(),
        labelMedium = labelMedium.inPoppins(),
        labelSmall = labelSmall.inPoppins(),
    )
}
