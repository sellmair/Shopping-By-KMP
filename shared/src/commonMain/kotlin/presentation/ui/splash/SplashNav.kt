package presentation.ui.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import common.ChangeStatusBarColors
import org.koin.compose.koinInject
import presentation.navigation.SplashNavigation
import presentation.ui.splash.view_model.LoginViewModel

@Composable
internal fun SplashNav(viewModel: LoginViewModel = koinInject(), navigateToMain: () -> Unit) {
    val navigator = rememberNavController()

    ChangeStatusBarColors(Color.Black)
    NavHost(
        startDestination = SplashNavigation.Splash,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<SplashNavigation.Splash> {
            SplashScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                navigateToMain = navigateToMain,
                navigateToLogin = {
                    navigator.popBackStack()
                    navigator.navigate(SplashNavigation.Login)
                })
        }
        composable<SplashNavigation.Login> {
            LoginScreen(
                navigateToMain = navigateToMain, navigateToRegister = {
                    navigator.navigate(SplashNavigation.Register)
                },
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent
            )
        }
        composable<SplashNavigation.Register> {
            RegisterScreen(
                navigateToMain = navigateToMain, popUp = {
                    navigator.popBackStack()
                }, state = viewModel.state.value,
                events = viewModel::onTriggerEvent
            )
        }
    }

}