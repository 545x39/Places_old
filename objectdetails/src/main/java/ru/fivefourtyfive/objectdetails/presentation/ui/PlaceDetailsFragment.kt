package ru.fivefourtyfive.objectdetails.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.fivefourtyfive.objectdetails.R
import ru.fivefourtyfive.objectdetails.databinding.FragmentObjectDetailBinding
import ru.fivefourtyfive.objectdetails.databinding.FragmentObjectDetailBindingImpl
import ru.fivefourtyfive.objectdetails.di.DaggerPlaceDetailsComponent
import ru.fivefourtyfive.objectdetails.presentation.viewmodel.PlaceDetailsViewModel
import ru.fivefourtyfive.objectdetails.presentation.viewmodel.PlaceDetailsViewState
import ru.fivefourtyfive.wikimapper.Wikimapper
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameter.ID
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameters
import ru.fivefourtyfive.wikimapper.di.factory.ViewModelProviderFactory
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.Renderer
import javax.inject.Inject
import kotlin.random.Random
import ru.fivefourtyfive.wikimapper.R as appR

class PlaceDetailsFragment : Fragment(), Renderer<PlaceDetailsViewState> {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var viewModel: PlaceDetailsViewModel

    lateinit var binding: FragmentObjectDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DaggerPlaceDetailsComponent.factory()
            .create((requireActivity().application as Wikimapper).appComponent)
            .inject(this)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_object_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(appR.string.title_objectdetails)
        viewModel = ViewModelProvider(this, providerFactory).get(PlaceDetailsViewModel::class.java)
        binding.viewModel = viewModel
        binding.viewModel?.viewStateLiveData?.observe(viewLifecycleOwner, { render(it) })
        binding.buttonObject.setOnClickListener {
            (arguments?.get(ID) as Int).let {
                viewModel.getPlace(if (Random.nextBoolean()) 18307319 else Random.nextInt(1, 540))
            }
        }
    }

    override fun render(viewState: PlaceDetailsViewState) {
        binding.apply {
            when (viewState) {
                is PlaceDetailsViewState.Error -> {
                    title.text = "Ошибка: " + viewState.message
                }
                is PlaceDetailsViewState.Loading -> {
                    title.text = "Загрузка..."
                }
                is PlaceDetailsViewState.Success ->{
                    viewState.place.let {
                        title.text = it.title
                        description.text = it.description
                        tags.text = TagConverter.convert(it.tags)
                    }
                }
            }
        }
    }
}