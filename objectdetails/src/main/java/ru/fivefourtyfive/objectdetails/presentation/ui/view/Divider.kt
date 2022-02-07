package ru.fivefourtyfive.objectdetails.presentation.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import ru.fivefourtyfive.places.R

@Composable
fun Divider(){
    androidx.compose.material.Divider(
        color = colorResource(id = R.color.lightest_grey),
        thickness = 1.dp,
        modifier = Modifier.padding(0.dp, 2.dp, 0.dp, 2.dp)
    )
}