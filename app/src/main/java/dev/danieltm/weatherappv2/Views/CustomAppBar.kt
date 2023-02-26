package dev.danieltm.weatherappv2.Views

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.danieltm.weatherappv2.R

@Composable
fun CustomAppBar(city: String){
    Box(modifier = Modifier
        .fillMaxWidth()
    ){
        TopAppBar(
            backgroundColor = colorResource(R.color.status_bar_app_bar),
            elevation = 0.dp,
            title = {
                Text(text = city, color = Color.White, fontSize = 20.sp)
            },
            actions = {
                AppBarActions()
            },
            modifier = Modifier.height(70.dp)
        )
    }
}

@Composable
fun AppBarActions(){
    SearchAction()
    ShareAction()
    MoreAction()
}

@Composable
fun SearchAction(){
    val context = LocalContext.current
    IconButton(
        onClick = {
            Toast.makeText(context, "Search Clicked!", Toast.LENGTH_SHORT).show()
        }
    ) {
        Icon(imageVector = Icons.Filled.Search, contentDescription = "search_icon", tint = Color.White)
    }
}

@Composable
fun ShareAction(){
    val context = LocalContext.current
    IconButton(
        onClick = {
            Toast.makeText(context, "Share Clicked!", Toast.LENGTH_SHORT).show()}
    ) {
        Icon(imageVector = Icons.Filled.Share, contentDescription = "share_icon", tint = Color.White)
    }
}

@Composable
fun MoreAction(){
    val context = LocalContext.current
    IconButton(
        onClick = {
            Toast.makeText(context, "More Clicked!", Toast.LENGTH_SHORT).show()}
    ) {
        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "more_icon", tint = Color.White)
    }
}

@Preview
@Composable
fun CustomAppBarPreview(){
    CustomAppBar(city = "City")
}