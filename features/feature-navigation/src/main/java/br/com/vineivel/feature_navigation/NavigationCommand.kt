package br.com.vineivel.feature_navigation

sealed class NavigationCommand {
    object toNext : NavigationCommand()
    object toBack : NavigationCommand()
    data class backTo(val destinationId: Int) : NavigationCommand()
    object toRoot : NavigationCommand()
}