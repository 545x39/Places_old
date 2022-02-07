package ru.fivefourtyfive.objectdetails.presentation.ui.view

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import ru.fivefourtyfive.objectdetails.R
import ru.fivefourtyfive.places.R as appR
import ru.fivefourtyfive.places.util.Network.ROOT_URL

@Composable
fun UserPic(path: String? = null) {
    val default = R.drawable.icon_default_userpic
    AndroidView(
        factory = {
            CircleImageView(it).apply {
                when (path.isNullOrBlank() || path == "/img/nofoto_50.png") {
                    true -> setImageResource(default)
                    false -> Glide.with(it)
                        .load("$ROOT_URL$path")
                        .centerCrop()
                        .placeholder(default)
                        .into(this)
                }
                borderWidth = 2
                borderColor = ContextCompat.getColor(it, appR.color.lightest_grey)
            }
        },
        Modifier
            .height(48.dp)
            .width(48.dp)
    )
}