package ru.fivefourtyfive.wikimapper.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import ru.fivefourtyfive.wikimapper.R
import ru.fivefourtyfive.wikimapper.util.Permissions.PERMISSIONS
import ru.fivefourtyfive.wikimapper.presentation.util.PermissionsUtil
import ru.fivefourtyfive.wikimapper.presentation.util.PermissionsUtil.getNotGrantedPermissions

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

    fun showSnackBar(message: String?) {
        message?.let {
            Snackbar.make(findViewById(R.id.main_content), it, Snackbar.LENGTH_LONG)
                .show()
        }
    }
}