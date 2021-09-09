package ru.fivefourtyfive.objectdetails.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.compose.foundation.layout.Column
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
import ru.fivefourtyfive.objectdetails.presentation.ui.view.Comment
import ru.fivefourtyfive.objectdetails.presentation.viewmodel.PlaceDetailsViewModel
import ru.fivefourtyfive.objectdetails.presentation.viewmodel.PlaceDetailsViewState
import ru.fivefourtyfive.wikimapper.Wikimapper
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameter.ID
import ru.fivefourtyfive.wikimapper.di.factory.ViewModelProviderFactory
import ru.fivefourtyfive.wikimapper.domain.entity.*
import ru.fivefourtyfive.wikimapper.presentation.ui.MainActivity
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.Renderer
import ru.fivefourtyfive.wikimapper.util.Network.ROOT_URL
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
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.menu_place_details, menu)

    override fun onPrepareOptionsMenu(menu: Menu) {
        //TODO check preferences whether slide show mode is enabled and set checkbox accordingly.
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //<editor-fold defaultstate="collapsed" desc="INNER METHODS">
        fun share(uriString: String) =
            runCatching { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uriString)))}

        fun shareLink() {
            with(viewModel.viewStateLiveData.value) {
                if (this is PlaceDetailsViewState.Success) {
                    share("$ROOT_URL/${place.id}/${place.languageIso}")
                }
            }
        }

        fun shareCoordinates() {
            with(viewModel.viewStateLiveData.value) {
                if (this is PlaceDetailsViewState.Success) {
                    place.location?.apply { share("geo:$lat,$lon?q=$lat,$lon") }
                }
            }
        }

        fun switchSlideShow() {
            item.isChecked = !item.isChecked
            when (item.isChecked) {
                true -> binding.slider.startAutoCycle()
                false -> binding.slider.stopAutoCycle()
            }
        }
        //</editor-fold>

        when (item.itemId) {
            R.id.action_share_link -> shareLink()
            R.id.action_share_coordinates -> shareCoordinates()
            R.id.action_slide_show -> switchSlideShow()
        }
        return super.onOptionsItemSelected(item)
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
                        setComments(it.comments)
                    }
                }
            }
        }
    }

    private fun setComments(comments: List<Comment>?) {
        val count = "Комментариев: ${if (comments.isNullOrEmpty()) 0 else comments.size}"
        binding.commentsCount.text = count
        binding.commentsLayout.setContent { Column { comments?.map { Comment(it) } } }
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
            .disableAutoCycling(true)
            .setDuration(4500)
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
