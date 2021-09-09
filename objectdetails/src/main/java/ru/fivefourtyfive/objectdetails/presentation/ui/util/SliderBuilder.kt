package ru.fivefourtyfive.objectdetails.presentation.ui.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.animations.SliderAnimationInterface
import com.glide.slider.library.slidertypes.TextSliderView
import com.glide.slider.library.tricks.ViewPagerEx
import ru.fivefourtyfive.wikimapper.domain.entity.Photo

class SliderBuilder(private val context: Context, private val layout: SliderLayout) {

    fun setPresetTransformer(transformer: SliderLayout.Transformer) =
        apply { layout.setPresetTransformer(transformer) }

    fun setPresetIndicator(indicator: SliderLayout.PresetIndicators) =
        apply { layout.setPresetIndicator(indicator) }

    fun setCustomAnimation(animation: SliderAnimationInterface) =
        apply { layout.setCustomAnimation(animation) }

    fun addOnPageChangeListener(listener: ViewPagerEx.OnPageChangeListener) =
        apply { layout.addOnPageChangeListener(listener) }

    fun disableAutoCycling(disable: Boolean) = apply { if (disable) layout.stopAutoCycle() }

    fun stopCyclingWhenTouch(stop: Boolean) = apply { layout.stopCyclingWhenTouch(stop) }

    fun setDuration(duration: Long) = this.apply { layout.setDuration(duration) }

    @SuppressLint("CheckResult")
    fun buildWith(photos: List<Photo>?) {
        if (!photos.isNullOrEmpty()) layout.visibility = View.VISIBLE
        val requestOptions = RequestOptions()
        requestOptions.centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
        //.placeholder(R.drawable.placeholder)
        //.error(R.drawable.placeholder);
        photos?.map { photo ->
            photo.apply {
                //There is also a DefaultSliderView, which cannot display any text.
                TextSliderView(context)
                    .image(url1280)
                    .description("$userName, $timeString")
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true).apply {
                        layout.addSlider(this@apply)
                    }
            }
        }
    }
}