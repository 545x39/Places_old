package ru.fivefourtyfive.wikimapper.util

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.dynamicfeatures.DynamicInstallMonitor
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

object InstallObserver {
    fun observeInstall(lifecycleOwner: LifecycleOwner, activity: Activity, splitInstallManager:SplitInstallManager, installMonitor: DynamicInstallMonitor) {
        /////
        val REQUEST_CODE_INSTALL_CONFIRMATION = 2
        //
        installMonitor.status.observe(lifecycleOwner,
            { state ->
                when (state.status()) {
                    SplitInstallSessionStatus.INSTALLED -> {
                        //Модуль установлен, можно дёрнуть навигацию к нему.

                    }
                    SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                        //Большие модули требуют подтверждения установки от пользователя, нужно вызвать специальный диалог (а можно поступить и по-своему)
                        splitInstallManager.startConfirmationDialogForResult(
                            state,// состояние
                            activity,//активность, в которой будет дёргаться onActivityResult()
                            REQUEST_CODE_INSTALL_CONFIRMATION//код запроса, который свалится в onActivityResult()
                        )
                    }
                    SplitInstallSessionStatus.FAILED -> {
                        //Установка облажалась сама
                    }
                    SplitInstallSessionStatus.CANCELED -> {
                        //Установка отменена пользователем.
                    }
                    else -> {
                    }
                }
                if (state.hasTerminalStatus()) {
                    installMonitor.status.removeObservers(lifecycleOwner) //Отписаться.
                }
            })
    }
}