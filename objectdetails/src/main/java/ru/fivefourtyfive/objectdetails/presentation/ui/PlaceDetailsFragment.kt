package ru.fivefourtyfive.objectdetails.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.fivefourtyfive.objectdetails.R
import ru.fivefourtyfive.objectdetails.di.DaggerPlaceDetailsComponent
import ru.fivefourtyfive.objectdetails.presentation.viewmodel.PlaceDetailsViewModel
import ru.fivefourtyfive.wikimapper.Wikimapper
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameter.ID
import ru.fivefourtyfive.wikimapper.di.factory.ViewModelProviderFactory
import javax.inject.Inject
import kotlin.random.Random
import ru.fivefourtyfive.wikimapper.R as appR

class PlaceDetailsFragment : Fragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var viewModel: PlaceDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DaggerPlaceDetailsComponent.factory()
            .create((requireActivity().application as Wikimapper).appComponent).inject(this)
        return inflater.inflate(R.layout.fragment_object_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(appR.string.title_objectdetails)
        viewModel = ViewModelProvider(this, providerFactory).get(PlaceDetailsViewModel::class.java)
        view.findViewById<Button>(R.id.button_object).setOnClickListener {
            (arguments?.get(ID) as Int)?.let {
//                viewModel.getPlace(Random.nextInt(1, 540))
                viewModel.getPlace(18307319)
                Toast.makeText(requireContext(), "ID passed: " + it, LENGTH_SHORT)
                    .show()
            }

        }

    }
}