package ru.fivefourtyfive.places.framework.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.dynamicfeatures.DynamicExtras
import androidx.navigation.dynamicfeatures.DynamicInstallMonitor
import androidx.navigation.fragment.findNavController
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import kotlinx.coroutines.Dispatchers
import ru.fivefourtyfive.places.util.InstallObserver

open class NavFragment: Fragment() {

    lateinit var splitInstallManager: SplitInstallManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splitInstallManager = SplitInstallManagerFactory.create(requireContext())
    }

    protected fun navigate(resId: Int, args: Bundle? = null) {
        Dispatchers.Unconfined
        DynamicInstallMonitor().also {
            findNavController().navigate(resId, args, null, DynamicExtras(it))
            InstallObserver.observeInstall(
                viewLifecycleOwner,
                requireActivity(),
                splitInstallManager,
                it
            )
        }
    }
}