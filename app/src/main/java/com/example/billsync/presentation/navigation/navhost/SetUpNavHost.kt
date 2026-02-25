package com.example.billsync.presentation.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.billsync.presentation.navigation.route.CreateSubscription
import com.example.billsync.presentation.navigation.route.Subscription
import com.example.billsync.presentation.navigation.route.SubscriptionDetail
import com.example.billsync.presentation.screen.create_subscription.CreateSubscriptionScreen
import com.example.billsync.presentation.screen.subscription_detail.SubscriptionDetailScreen
import com.example.billsync.presentation.screen.subscriptions.SubscriptionScreen

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
                },
                onAddClick = {
                    navController.navigate(CreateSubscription)
                }
            )
        }

        composable<SubscriptionDetail> { entry ->
            val subscriptionDetail = entry.toRoute<SubscriptionDetail>()
            SubscriptionDetailScreen(subscriptionID = subscriptionDetail.subscriptionID)
        }

        composable<CreateSubscription> {
            CreateSubscriptionScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}