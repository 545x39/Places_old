package ru.fivefourtyfive.places.framework.presentation.abstraction

interface IEventHandler<EVENT : IEvent> {
    fun handleEvent(event: EVENT)
}