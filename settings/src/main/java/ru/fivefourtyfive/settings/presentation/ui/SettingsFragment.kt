package ru.fivefourtyfive.settings.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import ru.fivefourtyfive.settings.R
import ru.fivefourtyfive.places.R as appR

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(appR.string.settings)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {}
}