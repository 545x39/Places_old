package ru.fivefourtyfive.objectdetails.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.animations.DescriptionAnimation
import ru.fivefourtyfive.objectdetails.R
import ru.fivefourtyfive.objectdetails.databinding.FragmentPlaceDetailsBinding
import ru.fivefourtyfive.objectdetails.di.DaggerPlaceDetailsComponent
import ru.fivefourtyfive.objectdetails.presentation.ui.util.SliderBuilder
import ru.fivefourtyfive.objectdetails.presentation.ui.util.TagsBuilder
import ru.fivefourtyfive.objectdetails.presentation.viewmodel.PlaceDetailsViewModel
import ru.fivefourtyfive.objectdetails.presentation.viewmodel.PlaceDetailsViewState
import ru.fivefourtyfive.wikimapper.Wikimapper
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameter.ID
import ru.fivefourtyfive.wikimapper.di.factory.ViewModelProviderFactory
import ru.fivefourtyfive.wikimapper.domain.entity.Location
import ru.fivefourtyfive.wikimapper.domain.entity.Photo
import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.domain.entity.Tag
import ru.fivefourtyfive.wikimapper.presentation.ui.MainActivity
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.Renderer
import javax.inject.Inject
import ru.fivefourtyfive.wikimapper.R as appR

class PlaceDetailsFragment : Fragment(), Renderer<PlaceDetailsViewState> {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var viewModel: PlaceDetailsViewModel

    lateinit var binding: FragmentPlaceDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DaggerPlaceDetailsComponent.factory()
            .create((requireActivity().application as Wikimapper).appComponent)
            .inject(this)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_place_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(appR.string.title_objectdetails)
        viewModel = ViewModelProvider(this, providerFactory).get(PlaceDetailsViewModel::class.java)
        binding.viewModel = viewModel
        binding.viewModel?.viewStateLiveData?.observe(viewLifecycleOwner, { render(it) })
        arguments?.let { viewModel.getPlace(it.getInt(ID)) }
    }

    override fun render(viewState: PlaceDetailsViewState) {
        with(viewState) {
            when (this) {
                is PlaceDetailsViewState.Loading -> switchViews(this)
                is PlaceDetailsViewState.Error -> {
                    switchViews(this)
                    (requireActivity() as MainActivity).showSnackBar("Ошибка: $message")
                }
                is PlaceDetailsViewState.Success -> {
                    switchViews(this)
                    place.let {
                        setMain(it)
                        setLocation(it.location)
                        setTags(it.tags)
                        setPhotos(it.photos)
                    }
                }
            }
        }
    }

    private fun setMain(place: Place) {
        with(place) {
            requireActivity().title = title
            binding.title.text = title
            binding.description.text = description
        }
    }

    private fun setPhotos(photos: List<Photo>?) {
        SliderBuilder(requireContext(), binding.slider)
            .setPresetTransformer(SliderLayout.Transformer.Accordion)
            .setPresetIndicator(SliderLayout.PresetIndicators.Center_Top)
            .setCustomAnimation(DescriptionAnimation())
            .disableAutoCycling(false)
            .stopCyclingWhenTouch(true)
            .buildWith(photos)
    }

    private fun switchViews(state: PlaceDetailsViewState) {
        binding.apply {
            when (state) {
                is PlaceDetailsViewState.Success -> {
                    contentLayout.visibility = VISIBLE
                    progress.visibility = GONE
                }
                is PlaceDetailsViewState.Loading -> {
                    contentLayout.visibility = GONE
                    progress.visibility = VISIBLE
                }
                is PlaceDetailsViewState.Error -> {
                    contentLayout.visibility = GONE
                    progress.visibility = GONE
                }
            }

        }
    }

    private fun setLocation(location: Location?) {
        location?.apply { binding.location.text = "$place, $country" }
    }

    private fun setTags(tags: List<Tag>?) {
        binding.tags.text = TagsBuilder.buildWith(tags)
    }

    override fun onStop() {
        binding.slider.stopAutoCycle()
        super.onStop()
    }
}
