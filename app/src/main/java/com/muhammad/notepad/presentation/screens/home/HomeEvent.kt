package com.muhammad.notepad.presentation.screens.home

sealed interface HomeEvent {
    data object ScrollToTop : HomeEvent
}