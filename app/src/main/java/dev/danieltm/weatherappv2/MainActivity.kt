package dev.danieltm.weatherappv2

import android.content.Context
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import dev.danieltm.weatherappv2.ViewModels.MainViewModel
import dev.danieltm.weatherappv2.Views.CustomTopAppBar
import dev.danieltm.weatherappv2.Views.DefaultTopAppBar

//import dev.danieltm.weatherappv2.Views.TabItem

import dev.danieltm.weatherappv2.ui.theme.WeatherAppV2Theme
import dev.danieltm.weatherappv2.utilities.SearchAppBarState
import dev.danieltm.weatherappv2.utilities.SharedViewModel
import java.time.LocalDate
import java.time.LocalTime


class MainActivity : ComponentActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            WeatherAppV2Theme {
                val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
                val searchTextState: String by sharedViewModel.searchTextState

                val mainViewModel = viewModel<MainViewModel>()
                mainViewModel.getLocationAndWeather(this@MainActivity, this, intent)

                val cityName by mainViewModel.cityName.collectAsState()
                val temp by mainViewModel.temp.collectAsState()
                val feelsLike by mainViewModel.feelsLike.collectAsState()
                val windSpeed by mainViewModel.windSpeed.collectAsState()
                val windDirection by mainViewModel.windDirection.collectAsState()
                val weatherDescription by mainViewModel.weatherDescription.collectAsState()
                val icon by mainViewModel.icon.collectAsState()

                val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val countryCodeValue = tm.networkCountryIso

                //finish()
                //startActivity(intent)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScaffoldTopBar(
                        mainViewModel,
                        city = cityName.uppercase(),
                        sharedViewModel,
                        searchAppBarState = searchAppBarState,
                        searchTextState = searchTextState
                    )


                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .offset(0.dp, 60.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        var hourOfDay = remember {
                            (LocalTime.now()
                                .hour + 1).toString()
                        }

                        if(hourOfDay == "24"){
                            hourOfDay = "00"
                        }

                        val dayOfWeek = remember {
                            LocalDate.now()
                                .dayOfWeek
                        }

                        Text(text = "$dayOfWeek $hourOfDay:00", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$temp\u00B0", fontSize = 40.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Feels like $feelsLike\u00B0", fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            //fontFamily = ResourcesCompat.getFont(this@MainActivity, R.font.)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(modifier = Modifier, text = weatherDescription.replaceFirstChar { c: Char -> c.uppercaseChar() }, fontSize = 20.sp)

                    }
                    Column(
                        modifier = Modifier
                            .padding(30.dp, 45.dp)
                            .offset(0.dp, 65.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(modifier = Modifier
                            .border(1.dp, Color.White, CircleShape)
                            .padding(15.dp, 7.dp),
                            text = "$windSpeed", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(text = "$windDirection deg", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Image(
                            painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/$icon@2x.png"),
                            contentDescription = "Weather Icon",

                            modifier = Modifier
                                .size(200.dp)
                                .offset((-45).dp)
                        )
                        Text(text = searchTextState)
                    }
                }
            }
        }
    }
}

/*
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen()
{
    val tabs = listOf(
        TabItem.Home,
        TabItem.About,
        TabItem.Settings
    )
    
    val pagerState = rememberPagerState(pageCount = tabs.size)
    
    Scaffold(
        bottomBar = { BottomBar() }
    ) { paddingValues -> Modifier.padding(paddingValues)
        Column() {
            Tabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
    }
}

@Composable
fun BottomBar()
{
    BottomAppBar(
        backgroundColor = Color.Green,
        contentColor = Color.Black
    ){}
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState){

    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Green,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        tabs.forEachIndexed{ index, tab ->
            LeadingIconTab(
                icon = { Icon(painter = painterResource(id = tab.icon), contentDescription = "") },
                text = { Text(text = tab.title) },
                selected = pagerState.currentPage == index,
                onClick = {
                          scope.launch {
                              pagerState.animateScrollToPage(index)
                          }
                },
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState)
{
    HorizontalPager(state = pagerState) { page ->
        tabs[page].screen()
    }
}
*/

@Composable
fun ScaffoldTopBar(
    mainViewModel: MainViewModel,
    city: String,
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
) {
    Scaffold(topBar = {
        CustomTopAppBar(
            mainViewModel = mainViewModel,
            sharedViewModel = sharedViewModel,
            searchAppBarState = searchAppBarState,
            searchTextState = searchTextState,
            city = city
        )
    }) {
            paddingValues -> Modifier.padding(paddingValues)
    }
}

/*@Composable
fun HomeScreen()
{
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Home Screen",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 30.sp
        )
    }
}

@Composable
fun AboutScreen()
{
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "About Screen",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 30.sp
        )
    }
}

@Composable
fun SettingsScreen()
{
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Settings Screen",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 30.sp
        )
    }
}
*/