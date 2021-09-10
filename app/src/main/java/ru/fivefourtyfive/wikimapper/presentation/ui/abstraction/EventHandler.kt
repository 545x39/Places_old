package ru.fivefourtyfive.wikimapper.presentation.ui.abstraction

interface EventHandler<EVENT : Event> {
    fun handleEvent(event: EVENT)
}