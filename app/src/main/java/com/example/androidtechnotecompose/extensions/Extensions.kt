package com.example.androidtechnotecompose.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState


//Modifier onClick 클릭 효과 제거
fun Modifier.noRippleClickable(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick.invoke()
    }
}

//List<Color> 셔플 확장
fun List<Color>.shuffleColors(): List<Color> {
    val copyColors = this.shuffled()
    return if (this[0] == copyColors[0]) {
        shuffleColors()
    } else {
        copyColors
    }
}

@OptIn(ExperimentalPermissionsApi::class)
fun getTextToShowGivenPermissions(
    permissions: List<PermissionState>,
    shouldShowRationale: Boolean
): String {
    val revokedPermissionsSize = permissions.size
    if (revokedPermissionsSize == 0) return ""

    val textToShow = StringBuilder()

    for (i in permissions.indices) {
        textToShow.append(permissions[i].permission)
        when {
            revokedPermissionsSize > 1 && i == revokedPermissionsSize - 2 -> {
                textToShow.append("\n")
            }

            i == revokedPermissionsSize - 1 -> {
                textToShow.append(" ")
            }

            else -> {
                textToShow.append(", ")
            }
        }
    }
    textToShow.append(if (revokedPermissionsSize == 1) "\n권한이" else "\n권한들이")
    textToShow.append(
        if (shouldShowRationale) {
            " 필요합니다.\n앱이 제대로 작동하려면 모두 승인해 주세요."
        } else {
            " 거부되었습니다.\n권한이 없으면 작동할 수 없습니다."
        }
    )
    return textToShow.toString()
}