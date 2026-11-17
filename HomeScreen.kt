
package com.example.copair.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview



@Composable
fun HomeScreen() {

    var selectedTab by rememberSaveable {
        mutableIntStateOf(0)
    }

    val tabs = listOf(
        "Discover",
        "Voice Rooms",
        "Forum",
        "Profile"
    )

    val icons = listOf(
        Icons.Default.Search,
        Icons.Default.Mic,
        Icons.Default.Forum,
        Icons.Default.Person
    )

    Scaffold(
        containerColor = Color(0xFF160E29),

        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF241333)
            ) {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Icon(
                                imageVector = icons[index],
                                contentDescription = title
                            )
                        },
                        label = {
                            Text(
                                text = title,
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            indicatorColor = Color(0xFF703B98),
                            unselectedIconColor = Color(0xFFB39DDB),
                            unselectedTextColor = Color(0xFFB39DDB)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF160E29)),
            contentAlignment = Alignment.Center
        ) {
             if(selectedTab == 0){
                 DiscoverScreen()
            }else{
                Text(
                    text = "${tabs[selectedTab]} - Coming soon",
                    color = Color(0xFFFFFFFF),
                    fontSize = 24.sp
                )
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
fun CoPairHomePreview() {
    HomeScreen()
}
