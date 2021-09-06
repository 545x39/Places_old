package ru.fivefourtyfive.objectdetails.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import ru.fivefourtyfive.objectdetails.R
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameter.ID
import ru.fivefourtyfive.wikimapper.R as appR

class ObjectDetailsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_object_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(appR.string.title_objectdetails)
        view.findViewById<Button>(R.id.button_object).setOnClickListener {
        Toast.makeText(requireContext(), "ID passed: " + arguments?.get(ID), LENGTH_SHORT).show()
        }

    }
}