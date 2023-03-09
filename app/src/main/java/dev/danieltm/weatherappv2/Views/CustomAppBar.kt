package dev.danieltm.weatherappv2.Views

import android.view.Display.Mode
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.danieltm.weatherappv2.R
import dev.danieltm.weatherappv2.ViewModels.MainViewModel
import dev.danieltm.weatherappv2.utilities.SearchAppBarState
import dev.danieltm.weatherappv2.utilities.SharedViewModel
import dev.danieltm.weatherappv2.utilities.TrailingIconState

@Composable
fun CustomTopAppBar(
    mainViewModel: MainViewModel,
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    city: String
){
    val context = LocalContext.current
    when(searchAppBarState){
        SearchAppBarState.CLOSED -> {
            DefaultTopAppBar(
                city = city,
                onSearchClicked = {
                    sharedViewModel.searchAppBarState.value =
                        SearchAppBarState.OPENED
                }
            )
        }
        else -> {
            SearchTopAppBar(
                text = searchTextState,
                onTextChange = {text ->
                    sharedViewModel.searchTextState.value = text
                },
                onCloseClicked = {
                    sharedViewModel.searchAppBarState.value =
                        SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                },
                onSearchClicked = {
                    mainViewModel.getCurrentWeatherFromSearch(sharedViewModel.searchTextState.value)
                    sharedViewModel.searchAppBarState.value =
                        SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                }
            )
        }
    }
}

@Composable
fun DefaultTopAppBar(city: String, onSearchClicked: () -> Unit)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(70.dp)
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
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = {
            expanded = true
        }
    ) {
        Icon(
            imageVector = Icons.Filled.MoreVert, 
            contentDescription = "more_icon", 
            tint = Color.White
        )
        DropdownMenu(
            expanded = expanded, 
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = { expanded = false }
            ) {
                Text(text = "Profile")
            }
            DropdownMenuItem(
                onClick = { expanded = false }
            ) {
                Text(text = "Weather")
            }
            DropdownMenuItem(
                onClick = { expanded = false }
            ) {
                Text(text = "Settings")
            }
        }
    }
}

@Composable
fun SearchTopAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
){

    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.DELETE)
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
    ){

        Surface(
            modifier = Modifier
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
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(id = R.string.close_icon),
                            tint = Color.White
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClicked(text)
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = colorResource(id = R.color.status_bar_app_bar)
                )
            )
        }

    }
}

@Preview
@Composable
fun CustomAppBarPreview(){
    DefaultTopAppBar(city = "City", onSearchClicked = {})
}

@Preview
@Composable
fun SearchAppBarStatePreview(){
    SearchTopAppBar(
        text = "",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}