package dev.danieltm.weatherappv2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.ResourceFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import dev.danieltm.weatherappv2.ViewModels.MainViewModel
import dev.danieltm.weatherappv2.Views.CustomAppBar
import dev.danieltm.weatherappv2.ui.theme.WeatherAppV2Theme
import java.time.LocalDate
import java.time.LocalTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppV2Theme {
                val mainViewModel = viewModel<MainViewModel>()
                mainViewModel.getLocationAndWeather(this@MainActivity, this, intent)

                val cityName by mainViewModel.cityName.collectAsState()
                val temp by mainViewModel.temp.collectAsState()
                val feelsLike by mainViewModel.feelsLike.collectAsState()
                val windSpeed by mainViewModel.windSpeed.collectAsState()
                val windDirection by mainViewModel.windDirection.collectAsState()
                val weatherDescription by mainViewModel.weatherDescription.collectAsState()
                val icon by mainViewModel.icon.collectAsState()

                //finish()
                //startActivity(intent)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScaffoldTopBar(city = cityName.uppercase())
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .offset(0.dp, 60.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        val hourOfDay = remember {
                            LocalTime.now()
                                .hour + 1
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
                            .padding(30.dp,45.dp)
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
                    }
                }
            }
        }
    }
}

@Composable
fun ScaffoldTopBar(city: String) {
    Scaffold(topBar = {CustomAppBar(city = city)}) {
            paddingValues -> Modifier.padding(paddingValues)
    }
}