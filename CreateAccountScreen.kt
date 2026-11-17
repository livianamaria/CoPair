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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.VisualTransformation
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CreateAccountScreen(
    onBackClick: () -> Unit = {},
    onContinueClick: (
        String, String, String
    ) -> Unit = { _, _, _ -> }
){
    var nickname by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val emailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val passwordValid =
        password.any { it.isUpperCase() } &&
                password.any { it.isDigit() }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        cursorColor = Color.White,
        focusedBorderColor = Color(0xFF8E1DBE),
        unfocusedBorderColor = Color(0xFF851CB0),
        focusedContainerColor = Color(0xFF1E1149).copy(alpha = 0.55f),
        unfocusedContainerColor = Color(0xFF160E29).copy(alpha = 0.55f)
    )


    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.createacc),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
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

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.85f)
                .height(640.dp)
                .background(
                    color = Color(0xFF250936).copy(alpha = 0.4f),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(start = 24.dp, end = 24.dp, top = 1.dp, bottom = 28.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.gamingiconrmv),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(160.dp)
            )
            Box(
                modifier = Modifier.offset(y = (-59).dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Create Account",
                    fontSize = 42.sp,
                    fontFamily = FontFamily(Font(R.font.southern_beach)),
                    color = Color(0xFF00026C),
                    style = TextStyle(
                        drawStyle = Stroke(width = 10f)
                    )
                )

                Text(
                    text = "Create Account",
                    fontSize = 40.sp,
                    fontFamily = FontFamily(Font(R.font.southern_beach)),
                    color = Color(0xFFDF71F3)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-40).dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Nickname:",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(R.font.glacial_bold))
                )

                BasicTextField(
                    value = nickname,
                    onValueChange = { nickname = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    ),
                    cursorBrush = SolidColor(Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .align(Alignment.Start)
                        .background(
                            Color(0xFF160E29).copy(alpha = 0.55f),
                            RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = Color(0xFF721C8D),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (nickname.isEmpty()) {
                                Text(
                                    text = "Your nickname",
                                    color = Color(0xFF6F5F8C),
                                    fontSize = 16.sp
                                )
                            }

                            innerTextField()
                        }
                    }
                )

                Text(
                    text = "Email:",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(R.font.glacial_bold)),
                    modifier = Modifier.padding(top = 6.dp)
                )

                BasicTextField(
                    value = email,
                    onValueChange = { email = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    ),
                    cursorBrush = SolidColor(Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .align(Alignment.Start)
                        .background(
                            Color(0xFF160E29).copy(alpha = 0.55f),
                            RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = Color(0xFF721C8D),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (email.isEmpty()) {
                                Text(
                                    text = "hello@gmail.com",
                                    color = Color(0xFF6F5F8C),
                                    fontSize = 16.sp
                                )
                            }

                            innerTextField()
                        }
                    }
                )
                if (email.isNotEmpty() && !emailValid) {
                    Text(
                        text = "Please enter a valid email address.",
                        color = Color(0xFFFF8A8A),
                        fontSize = 11.sp
                    )
                }

                Text(
                    text = "Password:",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(R.font.glacial_bold)),
                    modifier = Modifier.padding(top = 6.dp)
                )

                BasicTextField(
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    ),
                    cursorBrush = SolidColor(Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .align(Alignment.Start)
                        .background(
                            Color(0xFF160E29).copy(alpha = 0.55f),
                            RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = Color(0xFF721C8D),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                if (password.isEmpty()) {
                                    Text(
                                        text = "Enter your password",
                                        color = Color(0xFF6F5F8C),
                                        fontSize = 16.sp
                                    )
                                }
                                innerTextField()
                            }

                            IconButton(
                                onClick = { passwordVisible = !passwordVisible }
                            ) {
                                Icon(
                                    imageVector = if (passwordVisible) {
                                        Icons.Default.VisibilityOff
                                    } else {
                                        Icons.Default.Visibility
                                    },
                                    contentDescription = if (passwordVisible) {
                                        "Hide password"
                                    } else {
                                        "Show password"
                                    },
                                    tint = Color(0xFFB39DDB),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                )
                Text(
                    text = "At least one uppercase letter and one number.",
                    color = if (passwordValid) {
                        Color(0xFF9BE7B0)
                    } else {
                        Color(0xFFD4CBE8)
                    },
                    fontSize = 11.sp,
                    modifier = Modifier.fillMaxWidth(0.98f)
                )
                Text(
                    text = "Confirm password:",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(R.font.glacial_bold)),
                    modifier = Modifier.padding(top = 6.dp)
                )

                BasicTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    singleLine = true,
                    visualTransformation = if (confirmPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    ),
                    cursorBrush = SolidColor(Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .align(Alignment.Start)
                        .background(
                            Color(0xFF160E29).copy(alpha = 0.55f),
                            RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = Color(0xFF721C8D),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                if (confirmPassword.isEmpty()) {
                                    Text(
                                        text = "Repeat your password",
                                        color = Color(0xFF6F5F8C),
                                        fontSize = 16.sp
                                    )
                                }
                                innerTextField()
                            }

                            IconButton(
                                onClick = {
                                    confirmPasswordVisible = !confirmPasswordVisible
                                }
                            ) {
                                Icon(
                                    imageVector = if (confirmPasswordVisible) {
                                        Icons.Default.VisibilityOff
                                    } else {
                                        Icons.Default.Visibility
                                    },
                                    contentDescription = if (confirmPasswordVisible) {
                                        "Hide password"
                                    } else {
                                        "Show password"
                                    },
                                    tint = Color(0xFFB39DDB),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                )

                Button(
                    onClick = {
                        onContinueClick(
                            nickname,
                            email.trim(),
                            password
                        )
                    },
                    enabled = nickname.isNotBlank() &&
                            emailValid &&
                            passwordValid &&
                            password == confirmPassword,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9254DE),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "CONTINUE",
                        fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.glacial_bold))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateAccountScreenPreview() {
    CoPairTheme {
        CreateAccountScreen()
    }
}