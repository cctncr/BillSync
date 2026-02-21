package com.example.billsync.presentation.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.billsync.presentation.navigation.route.Subscription
import com.example.billsync.presentation.navigation.route.SubscriptionDetail
import com.example.billsync.presentation.screen.SubscriptionDetailScreen
import com.example.billsync.presentation.screen.SubscriptionScreen

@Composable
fun SetUpNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Subscription
    ) {

        composable<Subscription> {
            SubscriptionScreen(
                onSubscriptionCardClick = { subscriptionID ->
                    navController.navigate(SubscriptionDetail(subscriptionID))
                }
            )
        }

        composable<SubscriptionDetail> { entry ->
            val subscriptionDetail = entry.toRoute<SubscriptionDetail>()
            SubscriptionDetailScreen(subscriptionID = subscriptionDetail.subscriptionID)
        }
    }
}