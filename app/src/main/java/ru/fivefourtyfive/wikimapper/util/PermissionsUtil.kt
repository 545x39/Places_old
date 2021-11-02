package ru.fivefourtyfive.wikimapper.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionsUtil {

    fun isPermissionGranted(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED

    fun arePermissionsGranted(context: Context, permissions: Array<String>): Boolean {
        permissions.map { if (!isPermissionGranted(context, it)) return false }
        return true
    }

    fun getNotGrantedPermissions(context: Context, permissions: Array<String>) =
        arrayListOf<String>().apply {
            permissions.map { isPermissionGranted(context, it).ifFalse { add(it) } }
        }.toTypedArray()
}