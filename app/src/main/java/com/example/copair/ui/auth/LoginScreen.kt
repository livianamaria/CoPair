package com.example.copair.ui.auth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.copair.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.copair.ui.theme.CoPairTheme
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedButton
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size

@Composable
fun LoginScreen(onLoginClick: ()->Unit={}) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "CoPair",
            color = Color(0xFFC59BE7), //CULOAREA SCRISULUI
            style = TextStyle(
                shadow = Shadow(
                    color = Color(0xFFE606EF), //REFLEXIA
                    offset = Offset.Zero,
                    blurRadius = 35f
                )
            ),
            fontSize = 65.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
        )

        Text(
            text = "CoPair",
            color = Color(0xFFA405A1), //CONTUR
            style = TextStyle(
                drawStyle = Stroke(width = 5f)
            ),
            fontSize = 65.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
        )

        Text(
            text="Your new friend is one swipe away",
            color = Color.White,
            fontSize=25.sp,
            fontFamily = FontFamily.Cursive,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top=180.dp)
        )

        Button(
            onClick = onLoginClick,

            border = BorderStroke(1.3.dp, Color(0xFFFF00E5)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF703B98).copy(alpha = 0.65f),
                contentColor = Color(0xFFFFFFFF)
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.55f)
                .offset(y = 75.dp)
                .height(46.dp)
        ){
            Text(
                text = "Log-in",
                fontSize = 22.sp,
                //fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(
                    Font(R.font.glacial_bold)
                )
            )
        }

        OutlinedButton(
            onClick = {
                //deschidem pagina de inregistrare
            },
            border = BorderStroke(1.3.dp, Color(0xFFFF00EE)),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFFFFFFF),
                containerColor=Color(0xC4775493).copy(alpha = 0.425f)
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 140.dp)
                .fillMaxWidth(0.55f)
                .height(46.dp)
        ){
            Text(
                text="Create Account",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(
                    Font(R.font.glacial_bold)
                )
            )
        }

        OutlinedButton(
            onClick = {
                // Conectăm autentificarea cu Google mai târziu.
            },
            border = BorderStroke(1.dp, Color(0xFFFF00EE)),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xC4775493).copy(alpha = 0.15f),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 205.dp)
                .fillMaxWidth(0.55f)
                .height(46.dp)
        ) {
            Text(
                text = "Continue with ",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily(
                    Font(R.font.glacial_bold)
                )
            )
            Spacer(modifier = Modifier.width(1.dp))

            Image(
                painter = painterResource(R.drawable.google_icon_removebg),
                contentDescription = "Google",
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text="Your people. Your setup. Your games.",
            color = Color(0xFFFFFFFF),
            fontSize=19.sp,
            fontFamily = FontFamily(
                Font(R.font.noctelle_rouge)
            ),
            //fontStyle = FontStyle.Italic,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top=799.dp)
        )
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    CoPairTheme {
        LoginScreen()
    }
}