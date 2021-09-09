package ru.fivefourtyfive.objectdetails.presentation.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.fivefourtyfive.wikimapper.domain.entity.Comment
import java.text.SimpleDateFormat
import java.util.*
import ru.fivefourtyfive.wikimapper.R as appR


@Composable
fun Comment(comment: Comment) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(0.dp, 6.dp, 0.dp, 0.dp)
    ) {
        UserPic(comment.userPhoto)
        Column(
            Modifier
                .fillMaxSize()
                .padding(6.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Text(
                comment.name ?: "",
                color = colorResource(id = appR.color.green),
                fontWeight = FontWeight.Bold
            )
            comment.date?.let {
                Text(
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date(it * 1000)),
                    color = colorResource(id = appR.color.light_green),
                    fontSize = 12.sp
                )
            }
            ContentText(text = comment.message)
            Divider()
        }
    }
}
