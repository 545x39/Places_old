package ru.fivefourtyfive.places.framework.presentation.abstraction

interface EventDispatcher<EVENT : Event> {

    fun dispatchEvent(event: EVENT)
}