package com.amir.google_map.util


import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun RequestPermission(
    permission: String,
    onPermissionGranted: () -> Unit
) {
    val context = LocalContext.current

    // بررسی وضعیت permission قبل از درخواست
    val granted = ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED

    if (granted) {
        onPermissionGranted()
        return
    }

    // launcher برای درخواست permission
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    // اجرای درخواست permission یکبار
    LaunchedEffect(Unit) {
        launcher.launch(permission)
    }
}


///////////////////////////////////////////////////////////////////////////////////////
