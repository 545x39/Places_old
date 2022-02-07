package ru.fivefourtyfive.places.framework.presentation.abstraction

interface IEventDispatcher<EVENT : IEvent> {

    fun dispatchEvent(event: EVENT)
}