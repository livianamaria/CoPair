package com.example.copair.ui.auth

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.copair.R
import com.example.copair.ui.theme.CoPairTheme
import java.util.Calendar
import java.util.Locale
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AboutYouScreen(onBackClick: () -> Unit = {},
                   onComplete: () -> Unit={}
                   ) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var birthDate by rememberSaveable { mutableStateOf("") }
    var countryCode by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }
    var languages by rememberSaveable { mutableStateOf("") }

    var birthYear by rememberSaveable { mutableStateOf(2000) }
    var birthMonth by rememberSaveable { mutableStateOf(0) }
    var birthDay by rememberSaveable { mutableStateOf(1) }

    var showCountries by remember { mutableStateOf(false) }
    var countrySearch by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val context = LocalContext.current
    val purple = Color(0xFFE297F1)
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    // Lista ISO de țări și teritorii disponibilă pe dispozitiv.
    val countries = remember {
        Locale.getISOCountries()
            .map { code ->
                code to Locale("", code).getDisplayCountry(Locale.ENGLISH)
            }
            .sortedBy { it.second }
    }

    val countryName = countries
        .firstOrNull { it.first == countryCode }
        ?.second.orEmpty()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.createacc),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 60.dp,
                    bottom = 60.dp
                )
                .background(
                    color = Color(0xFF250936).copy(alpha = 0.4f),
                    RoundedCornerShape(24.dp)
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "About You",
                color = Color(0xFFDF71F3),
                fontSize = 42.sp,
                fontFamily = FontFamily(Font(R.font.southern_beach))
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                AboutYouLineField(
                    label = "First name",
                    value = firstName,
                    onValueChange = { firstName = it },
                    placeholder = "Your first name"
                )

                AboutYouSelection(
                    label = "Date of birth",
                    value = birthDate,
                    placeholder = "Choose day, month and year",
                    onClick = {
                        val picker = DatePickerDialog(
                            context,
                            { _, year, month, day ->
                                birthYear = year
                                birthMonth = month
                                birthDay = day

                                birthDate = String.format(
                                    Locale.ENGLISH,
                                    "%02d/%02d/%04d",
                                    day,
                                    month + 1,
                                    year
                                )
                            },
                            birthYear,
                            birthMonth,
                            birthDay
                        )

                        picker.datePicker.maxDate =
                            Calendar.getInstance().timeInMillis

                        picker.show()
                    }
                )

                AboutYouSelection(
                    label = "Country",
                    value = countryName,
                    placeholder = "Choose your country",
                    onClick = {
                        countrySearch = ""
                        showCountries = true
                    }
                )

                Column {
                    Text(
                        text = "Gender",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.glacial_bold))
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        listOf("Female", "Male").forEach { option ->
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { gender = option },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = gender == option,
                                    onClick = { gender = option },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = purple,
                                        unselectedColor = purple
                                    )
                                )

                                Text(
                                    text = option,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.glacial_bold))
                                )
                            }
                        }
                    }
                }

                AboutYouLineField(
                    label = "Languages spoken",
                    value = languages,
                    onValueChange = { languages = it },
                    placeholder = "English, Romanian..."
                )

                if (error.isNotEmpty()) {
                    Text(
                        text = error,
                        color = Color(0xFFFFB4AB),
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (
                        firstName.isBlank() ||
                        birthDate.isBlank() ||
                        countryCode.isBlank() ||
                        gender.isBlank() ||
                        languages.isBlank()
                    ) {
                        error = "Please complete all fields."
                    } else {
                        error = ""

                        val user=auth.currentUser
                        if(user==null){
                            error="You must be signed in."
                        }else{
                            val profile=hashMapOf(
                                "firstName" to firstName,
                                "birthDate" to birthDate,
                                "country" to countryCode,
                                "gender" to gender,
                                "languages" to languages,
                                "email" to (user.email ?: ""),
                                "profileCompleted" to true
                            )

                            db.collection("users")
                                .document(user.uid)
                                .set(profile)
                                .addOnSuccessListener{
                                    onComplete()
                                }
                                .addOnFailureListener{e->
                                    error="Failed to save profile: ${e.localizedMessage}"
                                }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9254DE),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "CREATE ACCOUNT",
                    fontSize = 17.sp,
                    fontFamily = FontFamily(Font(R.font.glacial_bold))
                )
            }
        }

        TextButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 8.dp, top = 8.dp)
        ) {
            Text(
                text = "<",
                color = Color(0xFFA96CEF),
                fontSize = 24.sp
            )
        }
    }

    if (showCountries) {
        val filteredCountries = countries.filter {
            it.second.contains(countrySearch.trim(), ignoreCase = true)
        }

        AlertDialog(
            onDismissRequest = { showCountries = false },
            containerColor = Color(0xFF241735),
            title = {
                Text("Choose your country", color = Color.White)
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = countrySearch,
                        onValueChange = { countrySearch = it },
                        placeholder = {
                            Text("Search", color = purple)
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = purple,
                            focusedBorderColor = purple,
                            unfocusedBorderColor = purple
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 320.dp)
                    ) {
                        items(
                            items = filteredCountries,
                            key = { it.first }
                        ) { country ->
                            Text(
                                text = country.second,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        countryCode = country.first
                                        showCountries = false
                                    }
                                    .padding(vertical = 14.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCountries = false }) {
                    Text("Close", color = purple)
                }
            }
        )
    }

    if (showMessage) {
        AlertDialog(
            onDismissRequest = { showMessage = false },
            containerColor = Color(0xFF241735),
            title = {
                Text("Demo", color = Color.White)
            },
            text = {
                Text(
                    text = "The form is complete. Account creation is not connected yet.",
                    color = Color.White
                )
            },
            confirmButton = {
                TextButton(onClick = { showMessage = false }) {
                    Text("OK", color = purple)
                }
            }
        )
    }
}

@Composable
private fun AboutYouLineField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.glacial_bold))

        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 16.sp
            ),
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color(0xFFB9ADCD),
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFB39DDB))
        )
    }
}

@Composable
private fun AboutYouSelection(
    label: String,
    value: String,
    placeholder: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.glacial_bold))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value.ifEmpty { placeholder },
                color = if (value.isEmpty()) {
                    Color(0xFFB9ADCD)
                } else {
                    Color.White
                },
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "▾",
                color = Color(0xFFB39DDB),
                fontSize = 20.sp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFB39DDB))
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AboutYouScreenPreview() {
    CoPairTheme {
        AboutYouScreen()
    }
}