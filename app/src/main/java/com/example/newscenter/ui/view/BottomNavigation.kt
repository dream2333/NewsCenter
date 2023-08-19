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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
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
import com.example.newscenter.db.App
import com.example.newscenter.ui.model.AppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
    val color: Color
)


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
fun BottomNavigationBar(navController: NavController, model: AppViewModel) {
    val currentUser by model.currentUser.collectAsState()
    val bottomNavItems = listOf(
        BottomNavItem(
            label = "Home",
            icon = Icons.Filled.Home,
            route = "home_page",
            color = Color(0xFF3F51B5)
        ),
        BottomNavItem(
            label = "Recommend",
            icon = Icons.Filled.ThumbUp,
            route = "recommend_page",
            color = Color(0xFFFFB74D)
        ),
        BottomNavItem(
            label = "Favorite",
            icon = Icons.Filled.Favorite,
            route = "favorite_page",
            color = Color(0xFFEC407A)
        ),
        BottomNavItem(
            label = "Profile",
            icon = Icons.Filled.Person,
            route = if (currentUser == null) "login_page" else "user_page",
            color = Color(0xFF009688)
        )
    )
    var selectedItem by remember { mutableStateOf(bottomNavItems[0]) }
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        bottomNavItems.forEach { navItem ->
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
                Icon(
                    imageVector = navItem.icon,
                    contentDescription = navItem.label,
                    tint = navItem.color
                )
                Spacer(Modifier.padding(top = 2.dp))
                AnimatedVisibility(visible = navItem == selectedItem) {
                    Surface(
                        shape = CircleShape,
                        modifier = Modifier.size(5.dp),
                        color = navItem.color
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