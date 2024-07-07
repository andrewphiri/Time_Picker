package com.example.timepicker

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.timepicker.ui.theme.TimePickerTheme
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimePickerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //SimpleTimePicker()
                        //TimePickerWithDialog()
                        TimePickerInput()

                    }

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTimePicker(modifier: Modifier = Modifier) {
    val timeState = rememberTimePickerState(
        is24Hour = true
    )
    TimePicker(
        modifier = modifier,
        state = timeState
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerWithDialog(
    modifier: Modifier = Modifier
) {
    val timeState = rememberTimePickerState(
        is24Hour = true
    )
    var timeSelected by remember { mutableStateOf("") }


    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    showDialog = true
                }),
            text =  if (timeSelected.isEmpty()) "Select time" else timeSelected,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
                properties = DialogProperties(usePlatformDefaultWidth = true)
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.extraLarge),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                    ) {

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            text = "Select time"
                        )

                        TimePicker(
                            state = timeState,
                            layoutType = TimePickerLayoutType.Vertical,
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {

                            Button(
                                modifier = Modifier.padding(end = 8.dp),
                                onClick = { showDialog = false }
                            ) {
                                Text(
                                    text = "Cancel"
                                )
                            }

                            Button(
                                modifier = Modifier.padding(start = 8.dp),
                                onClick = {
                                    timeSelected = formattedTime(timeState.hour, timeState.minute)
                                    showDialog = false
                                }
                            ) {
                                Text(text = "OK")
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerInput(
    modifier: Modifier = Modifier
) {
    val timeState = rememberTimePickerState(
        is24Hour = true
    )
    var timeSelected by remember { mutableStateOf("") }


    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    showDialog = true
                }),
            text =  if (timeSelected.isEmpty()) "Select time" else timeSelected,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
                properties = DialogProperties(usePlatformDefaultWidth = true)
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.extraLarge),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                    ) {

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            text = "Select time"
                        )

                        TimeInput(
                            state = timeState
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {

                            Button(
                                modifier = Modifier.padding(end = 8.dp),
                                onClick = { showDialog = false }
                            ) {
                                Text(
                                    text = "Cancel"
                                )
                            }

                            Button(
                                modifier = Modifier.padding(start = 8.dp),
                                onClick = {
                                    timeSelected = formattedTime(timeState.hour, timeState.minute)
                                    showDialog = false
                                }
                            ) {
                                Text(text = "OK")
                            }
                        }
                    }
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TimePreview() {
    TimePickerTheme {
//        Greeting("Android")
        TimePickerInput()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formattedTime(hour: Int, minute: Int): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val time = LocalTime.of(hour, minute).format(formatter)
    return time
}