package com.example.copair.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.copair.R
import com.example.copair.ui.theme.CoPairTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton


@Composable
fun ForgotPasswordScreen(onBackClick: () -> Unit = {}) {

    var email by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.auth),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        TextButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 8.dp, top = 19.dp)
        ) {
            Text(
                text = "<",
                color = Color(0xFFA96CEF),
                fontSize = 24.sp
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.85f)
                .height(460.dp)
                .background(
                    color = Color(0xFF392D79).copy(alpha = 0.75f),
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Forgot password?",
                    color = Color(0xFFBF9DE7),
                    fontSize = 45.sp,
                    fontFamily = FontFamily(Font(R.font.southern_beach)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top=15.dp,bottom= 18.dp)
                )

                Text(
                    text = "Enter your email and we'll send you a link to reset your password.",
                    color = Color(0xFFD4CBE8),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font  (R.font.glacial_bold)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom= 57.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = {
                        Text("Email address", color = Color(0xFFABA0BC))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color(0xFFB39DDB),
                        unfocusedBorderColor = Color(0xFFB39DDB),
                        focusedContainerColor = Color(0xFF160E29).copy(alpha = 0.55f),
                        unfocusedContainerColor = Color(0xFF160E29).copy(alpha = 0.55f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom=18.dp)
                )

                Button(
                    onClick = {
                        // Aici conectăm trimiterea emailului de resetare.
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
                        text = "Send reset link",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ForgotPasswordScreenPreview() {
    CoPairTheme {
        ForgotPasswordScreen()
    }
}