package ru.fivefourtyfive.wikimapper.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import ru.fivefourtyfive.wikimapper.R
import ru.fivefourtyfive.wikimapper.util.Permissions.PERMISSIONS
import ru.fivefourtyfive.wikimapper.util.PermissionsUtil
import ru.fivefourtyfive.wikimapper.util.PermissionsUtil.getNotGrantedPermissions

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        requestPermissions()
    }

    private fun requestPermissions() {
        if (!PermissionsUtil.isBatchPermissionsGranted(this, PERMISSIONS)) {
            val notGranted: Array<String> = getNotGrantedPermissions(this, PERMISSIONS)
            ActivityCompat.requestPermissions(this, notGranted, 1234)
        }
    }
}