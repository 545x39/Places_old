package ru.fivefourtyfive.objectdetails.presentation.ui.view

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import ru.fivefourtyfive.wikimapper.R

@Composable
fun ContentText(text: String?){
//    SelectionContainer {
        Text(
            text ?: "",
            color = colorResource(id = R.color.grey),
            fontSize = 14.sp
        )
//    }
}