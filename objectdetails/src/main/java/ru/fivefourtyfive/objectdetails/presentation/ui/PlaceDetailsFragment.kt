package ru.fivefourtyfive.objectdetails.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.compose.foundation.layout.Column
import androidx.core.app.ShareCompat
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
import ru.fivefourtyfive.objectdetails.presentation.viewmodel.PlaceEvent
import ru.fivefourtyfive.wikimapper.Places
import ru.fivefourtyfive.wikimapper.framework.datasource.implementation.remote.util.Parameter.ID
import ru.fivefourtyfive.wikimapper.di.factory.ViewModelProviderFactory
import ru.fivefourtyfive.wikimapper.domain.dto.CommentDTO
import ru.fivefourtyfive.wikimapper.domain.dto.PhotoDTO
import ru.fivefourtyfive.wikimapper.framework.presentation.ui.MainActivity
import ru.fivefourtyfive.wikimapper.framework.presentation.abstraction.Renderer
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
            .create((requireActivity().application as Places).appComponent)
            .inject(this)
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_place_details, container, false)
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
        menu.findItem(R.id.action_slide_show).isChecked = binding.viewModel!!.slideshow()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    //TODO При добавлении данных об организациях сделать недостающие варианты раскладыки.
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //<editor-fold defaultstate="collapsed" desc="INNER METHODS">
        fun share(uriString: String) =
            runCatching { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uriString))) }

        fun shareLink() {
            with(viewModel.viewStateLiveData.value) {
                if (this is PlaceDetailsViewState.Success) {
                    ShareCompat.IntentBuilder(requireActivity())
                        .setType("text/plain")
                        .setChooserTitle(title)
                        .setText("$ROOT_URL/$id/$languageIso")
                        .createChooserIntent().apply {
                            if (resolveActivity(requireActivity().packageManager) != null) {
                                startActivity(this)
                            }
                        }
                }
            }
        }

        fun showOnWebsite() {
            with(viewModel.viewStateLiveData.value) {
                if (this is PlaceDetailsViewState.Success) {
                    share("$ROOT_URL/$id/$languageIso")
                }
            }
        }

        fun shareCoordinates() {
            with(viewModel.viewStateLiveData.value) {
                if (this is PlaceDetailsViewState.Success) {
                    share("geo:$lat,$lon?q=$lat,$lon")
                }
            }
        }

        fun switchSlideShow() {
            item.isChecked = !item.isChecked
            viewModel.handleEvent(PlaceEvent.SetSlideshow(item.isChecked))
            when (item.isChecked) {
                true -> binding.slider.startAutoCycle()
                false -> binding.slider.stopAutoCycle()
            }
        }
        //</editor-fold>

        when (item.itemId) {
            R.id.action_share_link -> shareLink()
            R.id.action_share_coordinates -> shareCoordinates()
            R.id.action_show_on_website -> showOnWebsite()
            R.id.action_slide_show -> switchSlideShow()
            //TODO remove
            R.id.action_show_on_map -> test()
        }
        return super.onOptionsItemSelected(item)
    }

    //TODO remove
    private fun test(){

    }

    override fun render(viewState: PlaceDetailsViewState) {
        with(viewState) {
            binding.contentLayout.visibility = contentVisibility
            binding.progress.visibility = progressVisibility
            when (this) {
                is PlaceDetailsViewState.Loading -> {
                }
                is PlaceDetailsViewState.Error -> {
                    (requireActivity() as MainActivity).showSnackBar(message)
                }
                is PlaceDetailsViewState.Success -> {
                    setMain(title, description)
                    setLocation(location)
                    setTags(tags)
                    setPhotos(photos)
                    setComments(comments)
                }
            }
        }
    }

    private fun setComments(comments: List<CommentDTO>) {
        String.format(getString(appR.string.comments_count), comments.size)
            .let { binding.commentsCount.text = it }
        binding.commentsLayout.setContent { Column { comments.map { Comment(it) } } }
    }

    private fun setMain(title: String, description: String) {
        requireActivity().title = title
        binding.title.text = title
        binding.description.text = description
    }

    private fun setPhotos(photos: List<PhotoDTO>) {
        SliderBuilder(requireContext(), binding.slider)
            .setPresetTransformer(SliderLayout.Transformer.Accordion)
            .setPresetIndicator(SliderLayout.PresetIndicators.Center_Top)
            .setCustomAnimation(DescriptionAnimation())
            .enableAutoCycling(binding.viewModel?.slideshow() ?: false)
            .setDuration(4000)
            .stopCyclingWhenTouch(true)
            .buildWith(photos)
    }

    private fun setLocation(location: String) {
        binding.location.text = location
    }

    private fun setTags(tags: List<String>) {
        binding.tags.text = TagsBuilder.buildWith(tags)
    }

    override fun onStop() {
        binding.slider.stopAutoCycle()
        super.onStop()
    }

}
