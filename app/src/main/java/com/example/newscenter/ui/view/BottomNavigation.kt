import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
    val color: Color
)

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Home",
            icon = Icons.Filled.Home,
            route = "home_page",
            color = Color(0xFF303F9F)
        ),
        BottomNavItem(
            label = "Favorite",
            icon = Icons.Filled.Favorite,
            route = "favorite_page",
            color = Color(0xFFD32F2F)
        ),
        BottomNavItem(
            label = "Profile",
            icon = Icons.Filled.Person,
            route = "login_page",
            color = Color(0xFFC2185B)
        )
    )
}

//@Composable
//fun BottomNavigationBar(navController: NavController) {
//    BottomNavigation(
//        modifier = Modifier.height(72.dp),
//        backgroundColor = Color.White,
//    ) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//        Constants.BottomNavItems.forEach { navItem ->
//            BottomNavigationItem(
//                selected = currentRoute == navItem.route,
//                onClick = {
//                    navController.navigate(navItem.route) {
//                        launchSingleTop = true
//                        popUpTo("home_page")
//                    }
//                },
//                icon = {
//                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
//                },
//                label = {
//                    Text(text = navItem.label)
//                },
//            )
//        }
//    }
//}


@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedItem by remember { mutableStateOf(Constants.BottomNavItems[0]) }
    BottomNavigation(
        backgroundColor = Color.White
    ) {
        Constants.BottomNavItems.forEach { navItem ->
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable(
                        onClick = {
                            selectedItem = navItem
                            navController.navigate(navItem.route) {
                                launchSingleTop = true
                                popUpTo("home_page")
                            }
                        },
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = navItem.icon, contentDescription = navItem.label, tint = navItem.color)
                Spacer(Modifier.padding(top = 2.dp))
                AnimatedVisibility(visible = navItem == selectedItem) {
                    Surface(
                        shape = CircleShape,
                        modifier = Modifier.size(5.dp),
                        color =  navItem.color
                    ) { }
                }
            }
        }
    }
}

@Composable
fun NavigationIcon(
    index: Int,
    selectedItem: Int
) {
    val alpha = if (selectedItem != index) 0.5f else 1f

    CompositionLocalProvider(LocalContentAlpha provides alpha) {
        when (index) {
            0 -> Icon(Icons.Filled.Home, contentDescription = null)

            else -> Icon(Icons.Filled.Settings, contentDescription = null)
        }
    }
}