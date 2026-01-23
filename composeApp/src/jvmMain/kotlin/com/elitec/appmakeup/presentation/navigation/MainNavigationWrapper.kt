package com.elitec.appmakeup.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavigationWrapper(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

}