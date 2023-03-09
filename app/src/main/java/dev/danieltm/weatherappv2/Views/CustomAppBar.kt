package dev.danieltm.weatherappv2.Views

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.danieltm.weatherappv2.R
import dev.danieltm.weatherappv2.utilities.TrailingIconState

@Composable
fun CustomTopAppBar(onSearchClicked: () -> Unit){

}

@Composable
fun DefaultTopAppBar(city: String, onSearchClicked: () -> Unit)
{
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
                AppBarActions(
                    onSearchClicked = onSearchClicked
                )
            },
            modifier = Modifier.height(70.dp)
        )
    }
}

@Composable
fun AppBarActions(
    onSearchClicked: () -> Unit
){
    SearchAction(onSearchClicked = onSearchClicked)
    ShareAction()
    MoreAction()
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
){
    val context = LocalContext.current
    IconButton(
        onClick = {
            onSearchClicked()
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

@Composable
fun SearchTopAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: () -> Unit
){

    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.DELETE)
    }

    Box(modifier = Modifier
        .fillMaxWidth()
    ){

        Surface(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxSize(),
            color = Color.Transparent,
            elevation = 0.dp
        ){
            TextField(
                modifier = Modifier
                    .fillMaxSize(),
                value = text,
                onValueChange = {
                onTextChange(it)
            },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        text = stringResource(id = R.string.search),
                        color = Color.White
                    )
                },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        onClick = {}
                    ){
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(
                                id = R.string.search_icon
                            ),
                            tint = Color.White
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        when(trailingIconState){
                            TrailingIconState.DELETE -> {
                                onTextChange("")
                                trailingIconState = TrailingIconState.CLOSE
                            }

                            TrailingIconState.CLOSE -> {
                                if(text.isNotEmpty()){
                                    onTextChange("")
                                } else{
                                    onCloseClicked()
                                    trailingIconState = TrailingIconState.DELETE
                                }
                            }
                        }
                    }
                    ) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = )
                    }
                }
            )
        }

    }
}

@Preview
@Composable
fun CustomAppBarPreview(){
    DefaultTopAppBar(city = "City", onSearchClicked = {})
}