package ru.fivefourtyfive.places.framework.presentation.abstraction

interface EventHandler<EVENT : Event> {
    fun handleEvent(event: EVENT)
}