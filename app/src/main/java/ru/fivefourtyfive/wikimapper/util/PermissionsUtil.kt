package ru.fivefourtyfive.wikimapper.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionsUtil {

    fun isPermissionNotGranted(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    }

    fun isBatchPermissionsGranted(context: Context, permissions: Array<String>): Boolean {
        permissions.map { if (isPermissionNotGranted(context, it)) return false }
        return true
    }

    fun getNotGrantedPermissions(context: Context, permissions: Array<String>): Array<String> {
        arrayListOf<String>().apply {
            permissions.map {
                if (isPermissionNotGranted(context, it)) {
                    add(it)
                }
            }
            return toTypedArray()
        }
    }
}