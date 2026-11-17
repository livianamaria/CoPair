
package com.example.copair.ui.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.copair.R
import kotlin.math.abs
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

data class DemoPlayer(
    val nickname: String,
    val age: Int,
    val games: String
)

@Composable
fun DiscoverScreen() {

    val players = listOf(
        DemoPlayer("Alex", 22, "Valorant • CS2"),
        DemoPlayer("Maria", 21, "League of Legends • Minecraft"),
        DemoPlayer("David", 24, "CS2 • PUBG • GTA V")
    )

    var currentPlayerIndex by remember {
        mutableIntStateOf(0)
    }

    val currentPlayer = players[currentPlayerIndex]

    val nextPlayerIndex =
        (currentPlayerIndex + 1) % players.size

    val nextPlayer = players[nextPlayerIndex]

    // POZITIA CARDULUI
    var swipeOffset by remember {
        mutableStateOf(0.dp)
    }

    // daca trage utilizatorul cardul cu degetul sau nu
    var isDragging by remember {
        mutableStateOf(false)
    }

    var isReturning by remember {
        mutableStateOf(false)
    }

    // ANIMATIA SWIPE
    val animatedOffset by animateDpAsState(
        targetValue = swipeOffset,


        animationSpec = when {
            isDragging -> snap()

            swipeOffset == 0.dp && isReturning -> tween(
                durationMillis = 350,
                easing = FastOutSlowInEasing
            )

            swipeOffset == 0.dp -> snap()

            else -> tween(
                durationMillis = 850,
                easing = FastOutSlowInEasing
            )
        },

        finishedListener = { finalOffset ->
            if (!isDragging && abs(finalOffset.value) >= 499f) {

                currentPlayerIndex =
                    (currentPlayerIndex + 1) % players.size

                swipeOffset = 0.dp
            }
            else if (finalOffset == 0.dp) {
                isReturning = false
            }
        },

        label = "CardSwipe"
    )

    // PROGRESUL ANIMATIEI: 0 -> 1
    val progress =
        (abs(animatedOffset.value) / 500f)
            .coerceIn(0f, 1f)
    val density = LocalDensity.current //localdensity ne ajuta sa convertim de la dp la px

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF160E29))
            .padding(20.dp)
    ) {

        // TITLU + FILTERS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Discover",
                color = Color.White,
                fontSize = 28.sp
            )

            TextButton(
                onClick = { }
            ) {
                Text(
                    text = "⚙ Filters",
                    color = Color(0xFFE606EF),
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // AMBELE CARDURI SUNT IN ACELASI BOX
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            // CARDUL URMATOR - IN SPATE
            PlayerCard(
                player = nextPlayer,
                modifier = Modifier.fillMaxSize()
            )

            // STRATUL MOV-NEGRU CARE DISPARE
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Color(0xFF160E29).copy(
                            alpha = 0.55f * (1f - progress)
                        )
                    )
            )

            // CARDUL ACTUAL - IN FATA
            PlayerCard(
                player = currentPlayer,

                modifier = Modifier
                    .fillMaxSize()
                    .offset(
                        x = animatedOffset,
                        y = (-90f * progress * progress).dp
                    )
                    .rotate(
                        (animatedOffset.value / 16f)
                            .coerceIn(-17f, 17f)
                    )

                    .graphicsLayer {
                        // CARDUL VECHI DEVINE TRANSPARENT
                        alpha = 1f - 0.75f * progress
                    }

                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragStart = {
                                isDragging = true
                            },

                            onHorizontalDrag = { change, dragAmount ->
                                change.consume()

                                swipeOffset += with(density) {
                                    dragAmount.toDp()
                                }
                            },

                            onDragEnd = {
                                isDragging = false

                                isReturning = swipeOffset.value in -120f..120f

                                swipeOffset = when {
                                    swipeOffset.value > 120f -> 500.dp
                                    swipeOffset.value < -120f -> (-500).dp
                                    else -> 0.dp
                                }
                            },


                            onDragCancel = {
                                isDragging = false
                                swipeOffset = 0.dp
                            }
                        )
                    },
                swipeX = animatedOffset.value
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // BUTOANELE SWIPE
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // DISLIKE
            Button(
                onClick = {
                    if (swipeOffset == 0.dp) {
                        swipeOffset = (-500).dp
                    }
                },
                modifier = Modifier.size(60.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3C214F)
                )
            ) {
                Text(
                    text = "❌",
                    color = Color(0xFFFF5577),
                    fontSize = 25.sp
                )
            }

            // SUPER LIKE
            Button(
                onClick = { },
                modifier = Modifier.size(60.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3C214F)
                )
            ) {
                Text(
                    text = "\uD83C\uDF1F",
                    color = Color(0xFFD4AAFF),
                    fontSize = 40.sp
                )
            }

            // LIKE
            Button(
                onClick = {
                    if (swipeOffset == 0.dp) {
                        swipeOffset = 500.dp
                    }
                },
                modifier = Modifier.size(60.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3C214F)
                )
            ) {
                Text(
                    text = "🎮",
                    color = Color(0xFFFF58D7),
                    fontSize = 32.sp
                )
            }
        }
    }
}

// COMPONENTA REUTILIZABILA PENTRU CARDURI
@Composable
private fun PlayerCard(
    player: DemoPlayer,
    modifier: Modifier = Modifier,
    swipeX: Float = 0f
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2C1945)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            // FOTOGRAFIA
            Image(
                painter = painterResource(
                    id = R.drawable.player_pfp
                ),
                contentDescription = "Player photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // GRADIENTUL DE JOS
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
            )

            // INFORMATIILE JUCATORULUI
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {

                Text(
                    text = "${player.nickname}, ${player.age}",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = player.games,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            if (abs(swipeX) > 15f) {
                val overlayAlpha = (abs(swipeX) / 180f)
                    .coerceIn(0f, 0.5f)

                val overlayColor =
                    if (swipeX < 0f) {
                        Color(0xFFFF3B5C)
                    } else {
                        Color(0xFF39FF88)
                    }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            overlayColor.copy(alpha = overlayAlpha)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (swipeX < 0f)
                            "SKIP ❌"
                        else
                            "LET'S PLAY 🎮",

                        color = Color.White,
                        fontSize = 50.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.noctelle_rouge)),
                        style = TextStyle(
                            shadow = Shadow(
                                color = if (swipeX < 0f)
                                    Color(0xFFFF1744)
                                else
                                    Color(0xFF00FF88),

                                offset = Offset.Zero,
                                blurRadius = 18f
                            )
                        ),
                        modifier = Modifier.graphicsLayer {
                            alpha = (abs(swipeX) / 120f)
                                .coerceIn(0f, 1f)
                        }



                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 393,
    heightDp = 852
)
@Composable
fun CoPairDiscoverScreen() {
    HomeScreen()
}
